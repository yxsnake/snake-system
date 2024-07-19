package com.snake.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.snake.system.model.dto.ResourceDTO;
import com.snake.system.model.entity.Resource;
import com.snake.system.model.enums.ResourceTypeEnum;
import com.snake.system.model.form.ApiCreateForm;
import com.snake.system.model.form.ApiModifyForm;
import com.snake.system.model.form.ext.InitTenantApiResourceForm;
import com.snake.system.model.form.ext.InitTenantMenuForm;
import com.snake.system.model.queries.ApiResourcePageEqualsQueries;
import com.snake.system.service.ApiResourceService;
import com.snake.system.service.ResourceService;
import io.github.yxsnake.pisces.web.core.enums.DisabledEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.github.yxsnake.pisces.web.core.base.BaseFuzzyQueries;
import io.github.yxsnake.pisces.web.core.base.QueryFilter;
import io.github.yxsnake.pisces.web.core.enums.DeletedEnum;
import io.github.yxsnake.pisces.web.core.utils.BizAssert;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: snake
 * @create-time: 2024-07-15
 * @description: api 资源
 * @version: 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApiResourceServiceImpl implements ApiResourceService {

    private final ResourceService resourceService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResourceDTO createApi(ApiCreateForm form) {
        Resource resource = form.convert(Resource.class);
        long count = this.resourceService.lambdaQuery().eq(Resource::getPath, form.getPath())
                .eq(Resource::getDeleted, DeletedEnum.NORMAL.getValue()).list().stream().count();
        BizAssert.isTrue("接口路径已存在",count>0);
        resource.setResourceId(IdWorker.getIdStr());
        resource.setResourceType(ResourceTypeEnum.INTERFACE.getValue());
        resource.setDeleted(DeletedEnum.NORMAL.getValue());
        this.resourceService.getBaseMapper().insert(resource);
        return resource.convert(ResourceDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResourceDTO modifyApi(ApiModifyForm form) {
        Resource resource = this.resourceService.getBaseMapper().selectById(form.getResourceId());
        BizAssert.isTrue("接口已被删除不允许修改",DeletedEnum.DEL.getValue().equals(resource.getDeleted()));
        //查询接口路径是否已存在
        long count = this.resourceService.lambdaQuery().eq(Resource::getPath, form.getPath()).ne(Resource::getResourceId, form.getResourceId()).list().stream().count();
        BizAssert.isTrue("接口路径已存在",count>0);
        BeanUtils.copyProperties(form,resource);
        this.resourceService.updateById(resource);
        return resource.convert(ResourceDTO.class);
    }

    @Override
    public IPage<ResourceDTO> pageList(QueryFilter<ApiResourcePageEqualsQueries, BaseFuzzyQueries> queryFilter) {
        BizAssert.isTrue("请求参数不能为空", Objects.isNull(queryFilter));
        ApiResourcePageEqualsQueries equalsQueries = queryFilter.getEqualsQueries();
        BizAssert.isTrue("equalsQueries不能为空",Objects.isNull(equalsQueries));
        IPage<ResourceDTO> page = this.resourceService.page(
                new Page<>(queryFilter.getPageNum(), queryFilter.getPageSize()),
                Wrappers.lambdaQuery(Resource.class)
                        .eq(StrUtil.isNotBlank(equalsQueries.getPath()),Resource::getPath,equalsQueries.getPath())
                        .eq(StrUtil.isNotBlank(equalsQueries.getPerm()),Resource::getPerm,equalsQueries.getPerm())
        ).convert(item -> item.convert(ResourceDTO.class));
        return page;
    }

    @Override
    public List<Resource> initTenantBuildApiResource(String tenantId, List<InitTenantApiResourceForm> apiResourceFormList) {
        List<Resource> resources = Lists.newArrayList();
        for (InitTenantApiResourceForm initTenantApiResourceForm : apiResourceFormList) {
            Resource resource = new Resource();
            String resourceId = IdWorker.getIdStr();
            String platformResourceId = initTenantApiResourceForm.getPlatformResourceId();
            resource.setPResourceId(platformResourceId);
            resource.setResourceId(resourceId);
            resource.setTenantId(tenantId);
            resource.setName(initTenantApiResourceForm.getName());
            resource.setPath(initTenantApiResourceForm.getPath());

            Resource parentResource = resources.stream()
                    .filter(item -> item.getPResourceId().equals(platformResourceId))
                    .collect(Collectors.toList()).stream()
                    .findFirst().orElse(null);
            resource.setParentId(parentResource.getParentId());
            resource.setPerm(initTenantApiResourceForm.getPerm());
            resource.setResourceType(ResourceTypeEnum.INTERFACE.getValue());
            resource.setDeleted(DeletedEnum.NORMAL.getValue());
            resource.setDisabled(DisabledEnum.NORMAL.getValue());
            resources.add(resource);

        }
        return resources;
    }
    }
}
