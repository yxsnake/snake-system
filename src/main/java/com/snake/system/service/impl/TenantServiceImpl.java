package com.snake.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.snake.system.mapper.TenantMapper;
import com.snake.system.model.dto.TenantDTO;
import com.snake.system.model.entity.Org;
import com.snake.system.model.entity.Resource;
import com.snake.system.model.entity.Role;
import com.snake.system.model.entity.Tenant;
import com.snake.system.model.form.PlatformInitTenantForm;
import com.snake.system.model.form.RestSupperAdminPwdForm;
import com.snake.system.model.form.TenantModifyForm;
import com.snake.system.model.form.TenantStopAndRepeatForm;
import com.snake.system.model.form.ext.InitTenantInfoForm;
import com.snake.system.service.*;
import io.github.yxsnake.pisces.mybatis.plus.context.TenantIgnoreContext;
import io.github.yxsnake.pisces.web.core.utils.BizAssert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements TenantService {

    private final OrgService orgService;

    private final RoleService roleService;

    private final MenuService menuService;

    private final ApiResourceService apiResourceService;

    private final ResourceService resourceService;

    private final AccountService accountService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void initTenant(PlatformInitTenantForm initTenantForm) {
        InitTenantInfoForm tenantInfoForm = initTenantForm.getTenantInfoForm();
        String tenantId = tenantInfoForm.getTenantId();
        String tenantName = tenantInfoForm.getTenantName();
        // 校验租户ID是否已存在
        this.checkTenantIdExist(tenantId);
        // 校验租户名称是否已存在
        this.checkTenantNameExist(tenantName);
        // 创建租户
        tenantInfoForm.checkNull();
        Tenant tenant = tenantInfoForm.convert(Tenant.class);
        this.getBaseMapper().insert(tenant);
        // 构建组织机构
        Org org = orgService.initTenantBuildOrg(tenant);
        TenantIgnoreContext.set();
        orgService.save(org);
        // 构建角色基础信息
        List<Role> roles = roleService.initTenantBuildRoles(tenantId,initTenantForm.getRoleFormList());
        if(CollUtil.isNotEmpty(roles)){
            TenantIgnoreContext.set();
            roleService.saveBatch(roles);
        }
        List<Resource> resources = Lists.newArrayList();
        // 构建菜单基础信息
        List<Resource> menus = menuService.initTenantBuildMenus(tenantId,initTenantForm.getMenuFormList());
        if(CollUtil.isNotEmpty(menus)){
            resources.addAll(menus);
        }
        // 构建api接口基础信息
        List<Resource> apiResources = apiResourceService.initTenantBuildApiResource(tenantId,initTenantForm.getApiResourceFormList());
        if(CollUtil.isNotEmpty(apiResources)){
            resources.addAll(apiResources);
        }
        if(CollUtil.isNotEmpty(resources)){
            TenantIgnoreContext.set();
            resourceService.saveBatch(resources);
        }
        // 创建账号
        accountService.initTenantCreateAccount(tenant);
    }

    @Override
    public void tenantAddFunction(PlatformInitTenantForm addFunctionForm) {

    }

    @Override
    public void modify(TenantModifyForm form) {

    }

    @Override
    public void restSupperAdminPwd(RestSupperAdminPwdForm form) {

    }

    @Override
    public void stopAndRepeat(TenantStopAndRepeatForm form) {

    }

    @Override
    public TenantDTO detail(String tenantId) {
        Tenant tenant = this.getBaseMapper().selectById(tenantId);
        if(Objects.isNull(tenant)){
            return null;
        }
        return tenant.convert(TenantDTO.class);
    }

    private void checkTenantIdExist(String tenantId){
        TenantIgnoreContext.set();
        Tenant tenant = this.getBaseMapper().selectById(tenantId);
        BizAssert.isTrue("租户已存在", Objects.nonNull(tenant));
    }

    private void checkTenantNameExist(String tenantName){
        TenantIgnoreContext.set();
        int size = this.lambdaQuery().eq(Tenant::getTenantName, tenantName).list().size();
        BizAssert.isTrue("租户名称已存在",size>0);
    }

}
