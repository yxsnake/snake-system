package com.snake.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.snake.system.model.dto.MenuTreeDTO;
import com.snake.system.model.dto.ResourceDTO;
import com.snake.system.model.entity.Resource;
import com.snake.system.model.enums.ResourceTypeEnum;
import com.snake.system.model.form.MenuCreateForm;
import com.snake.system.model.form.MenuModifyForm;
import com.snake.system.service.MenuService;
import com.snake.system.service.ResourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.github.yxsnake.pisces.web.core.context.UserContext;
import io.github.yxsnake.pisces.web.core.enums.DeletedEnum;
import io.github.yxsnake.pisces.web.core.enums.DisabledEnum;
import io.github.yxsnake.pisces.web.core.utils.BizAssert;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: snake
 * @create-time: 2024-07-15
 * @description: 菜单
 * @version: 1.0
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final ResourceService resourceService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResourceDTO createMenuOrButton(MenuCreateForm form) {
        List<Integer> allowResourceTypes = Arrays.asList(
                ResourceTypeEnum.DIRECTORY.getValue(),
                ResourceTypeEnum.MENU.getValue(),
                ResourceTypeEnum.BUTTON.getValue());
        Resource resource = form.convert(Resource.class);
        String resourceId = IdWorker.getIdStr();
        resource.setResourceId(resourceId);
        String parentId = resource.getParentId();
        if(StrUtil.isNotBlank(parentId)){
            Resource parentResource = resourceService.lambdaQuery()
                    .eq(Resource::getDeleted,DeletedEnum.NORMAL.getValue())
                    .eq(Resource::getResourceId, parentId)
                    .in(Resource::getResourceType, allowResourceTypes)
                    .list()
                    .stream()
                    .findFirst()
                    .orElse(null);
            BizAssert.isTrue("上级菜单不存在", Objects.isNull(parentResource));
            resource.setLevel(parentResource.getLevel()+1);
        }else {
            resource.setParentId(Resource.ROOT);
            resource.setLevel(1);
        }
        ResourceTypeEnum resourceTypeEnum = ResourceTypeEnum.getInstance(resource.getResourceType());
        BizAssert.isTrue("菜单类型只能是目录、菜单、按钮",Objects.isNull(resourceTypeEnum));
        BizAssert.isTrue("菜单类型只能是目录、菜单、按钮",allowResourceTypes.stream().filter(item->item.equals(resourceTypeEnum.getValue())).count()==0);
        if(ResourceTypeEnum.MENU.equals(resourceTypeEnum)){
            BizAssert.isTrue("组件路径不能为空",StrUtil.isBlank(form.getComponent()));
        }
        resource.setDisabled(DisabledEnum.NORMAL.getValue());
        resource.setDeleted(DeletedEnum.NORMAL.getValue());
        resource.setSort(DateUtil.date().getTime());
        resourceService.save(resource);

        return resource.convert(ResourceDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResourceDTO modifyMenuOrButton(MenuModifyForm form) {
        List<ResourceTypeEnum> allowResourceTypes = Arrays.asList(
                ResourceTypeEnum.DIRECTORY,
                ResourceTypeEnum.MENU,
                ResourceTypeEnum.BUTTON);
        // 根据资源ID查询资源
        Resource resource = resourceService.lambdaQuery()
                .eq(Resource::getDeleted,DeletedEnum.NORMAL.getValue())
                .in(Resource::getResourceType,allowResourceTypes.stream().map(ResourceTypeEnum::getValue).collect(Collectors.toList()))
                .eq(Resource::getResourceId, form.getResourceId())
                .list().stream().findFirst().orElse(null);
        BizAssert.isTrue("菜单或按钮不存在",Objects.isNull(resource));
        BizAssert.isTrue("平台菜单不允许修改",StrUtil.isNotBlank(resource.getPResourceId()));
        BeanUtils.copyProperties(form,resource);
        resourceService.updateById(resource);
        return resource.convert(ResourceDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMenuById(String menuId) {
        // 根据资源ID查询资源
        Resource resource = resourceService.lambdaQuery()
                .eq(Resource::getResourceId, menuId)
                .list().stream().findFirst().orElse(null);
        BizAssert.isTrue("菜单或按钮不存在",Objects.isNull(resource));
        // 校验当前菜单是否有子级菜单或按钮
        List<Resource> list = resourceService.lambdaQuery().eq(Resource::getParentId, menuId).list();
        BizAssert.isTrue("当前菜单存在子菜单或按钮不允许直接删除",list.size()>0);
        resource.setDeleted(DeletedEnum.DEL.getValue());
        resourceService.getBaseMapper().updateById(resource);
    }

    @Override
    public MenuTreeDTO tree() {
        // 查询当前用户的权限菜单
        List<Resource> list = null;
        Boolean supperAdmin = UserContext.isSupperAdmin();
        List<Integer> allowResourceTypes = Arrays.asList(
                ResourceTypeEnum.DIRECTORY.getValue(),
                ResourceTypeEnum.MENU.getValue(),
                ResourceTypeEnum.BUTTON.getValue()
        );
        if(supperAdmin){
            // 查询所有菜单
            list = this.resourceService.lambdaQuery()
                    .eq(Resource::getDeleted, DeletedEnum.NORMAL.getValue())
                    .in(Resource::getResourceType, allowResourceTypes)
                    .list();
        }else{
            // 查询权限范围内的菜单
            list = this.resourceService.queryCurrentUserMenuList(UserContext.getUserId());
        }
        if(CollUtil.isEmpty(list)){
            return null;
        }

        List<MenuTreeDTO> nodes = list.stream()
                .map(item->item.convert(MenuTreeDTO.class))
                .collect(Collectors.toList());
        List<MenuTreeDTO> treeNodes = streamToTree(nodes, Resource.ROOT);
        MenuTreeDTO tree = treeNodes.stream().findFirst().orElse(null);
        return tree;
    }

    private List<MenuTreeDTO> streamToTree(List<MenuTreeDTO> treeList,String parentId){

        List<MenuTreeDTO> list = treeList.stream()
                .filter(parent -> parent.getParentId().equals(parentId))
                .map(child -> {
                    child.setChildren(streamToTree(treeList,child.getResourceId()));
                    return child;
                }).collect(Collectors.toList());
        return list;
    }
}
