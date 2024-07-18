package com.snake.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.snake.system.model.dto.ResourceDTO;
import com.snake.system.model.entity.*;
import com.snake.system.model.enums.*;
import com.snake.system.model.queries.SsoPermissionQueries;
import com.snake.system.model.queries.SsoRoleQueries;
import com.snake.system.model.queries.UserResourceEqualsQueries;
import com.snake.system.service.*;
import io.github.yxsnake.pisces.mybatis.plus.context.TenantIgnoreContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.github.yxsnake.pisces.web.core.enums.DeletedEnum;
import io.github.yxsnake.pisces.web.core.enums.DisabledEnum;
import io.github.yxsnake.pisces.web.core.utils.BizAssert;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {

    private final IAccountService accountService;

    private final IRoleResourceService roleResourceService;

    private final IUserRoleService userRoleService;

    private final IResourceService resourceService;

    private final IRoleService roleService;

    @Override
    public List<String> getPermissions(SsoPermissionQueries queries) {
        List<String> permissions = Lists.newArrayList();
        // 账号信息
        Account account = this.getAccountInfo(queries.getAccountId());
        AccountSupperAdminEnum accountSupperAdminEnum = AccountSupperAdminEnum.getInstance(account.getSupperAdmin());
        if(AccountSupperAdminEnum.SUPPER.equals(accountSupperAdminEnum)){
            // 超管 查询所有权限
            TenantIgnoreContext.set();
            Set<String> resourceIds = roleResourceService.lambdaQuery()
                    .eq(RoleResource::getTenantId,account.getTenantId())
                    .list()
                    .stream()
                    .map(RoleResource::getResourceId)
                    .collect(Collectors.toSet());
            TenantIgnoreContext.set();
            if(CollUtil.isEmpty(resourceIds)){
                return permissions;
            }
            Set<String> permList = resourceService.lambdaQuery()
                    .eq(Resource::getTenantId,account.getTenantId())
                    .eq(Resource::getResourceType, ResourceTypeEnum.INTERFACE.getValue())
                    .in(Resource::getResourceId, resourceIds)
                    .eq(Resource::getDeleted, DeletedEnum.NORMAL.getValue())
                    .list().stream().map(Resource::getPerm).collect(Collectors.toSet());
            if(CollUtil.isNotEmpty(permList)){
                permissions.addAll(permList);
            }
        }else{
            // 查询用户权限


        }
        return permissions;
    }

    @Override
    public List<String> getRoles(SsoRoleQueries queries) {
        List<String> roles = Lists.newArrayList();
        // 账号信息
        Account account = this.getAccountInfo(queries.getAccountId());
        AccountSupperAdminEnum accountSupperAdminEnum = AccountSupperAdminEnum.getInstance(account.getSupperAdmin());
        if(AccountSupperAdminEnum.SUPPER.equals(accountSupperAdminEnum)){
           // 查询当前租户下所有角色
            TenantIgnoreContext.set();
            Set<String> roleIds = userRoleService
                    .lambdaQuery()
                    .eq(UserRole::getTenantId,account.getTenantId())
                    .list().stream().map(UserRole::getRoleId).collect(Collectors.toSet());
            TenantIgnoreContext.set();
            Set<String> roleCodeList = roleService.lambdaQuery()
                    .eq(Role::getDeleted,DeletedEnum.NORMAL.getValue())
                    .in(Role::getRoleId, roleIds)
                    .list()
                    .stream()
                    .map(Role::getRoleCode)
                    .collect(Collectors.toSet());
            if(CollUtil.isNotEmpty(roleCodeList)){
                roles.addAll(roleCodeList);
            }

        }else{
            // 查询用户权限

        }
        return roles;
    }

    @Override
    public List<ResourceDTO> getResources(UserResourceEqualsQueries queries) {
        BizAssert.isTrue("账号ID不能为空", StrUtil.isBlank(queries.getAccountId()));
        // 查询账号信息
        Account account = getAccountInfo(queries.getAccountId());
        BizAssert.isTrue("账号信息不存在",Objects.isNull(account));
        AccountChannelEnum accountChannelEnum = AccountChannelEnum.getInstance(account.getChannel());
        AccountSupperAdminEnum accountSupperAdminEnum = AccountSupperAdminEnum.getInstance(account.getSupperAdmin());
        if(AccountChannelEnum.EMP.equals(accountChannelEnum)){
            if(AccountSupperAdminEnum.SUPPER.equals(accountSupperAdminEnum)){
                // 查询 当前租户 所有接口类型的资源权限
                TenantIgnoreContext.set();
                return resourceService.lambdaQuery()
                        .eq(Resource::getTenantId,account.getTenantId())
                        .eq(Objects.nonNull(queries.getResourceType()),Resource::getResourceType,queries.getResourceType())
                        .list()
                        .stream()
                        .map(item->item.convert(ResourceDTO.class))
                        .collect(Collectors.toList());
            }else{
                String empId = account.getUserId();
                // 查询员工关联的角色
                TenantIgnoreContext.set();
                List<String> roleIds = userRoleService.lambdaQuery()
                        .eq(UserRole::getTenantId,account.getTenantId())
                        .eq(UserRole::getUserId, empId)
                        .list()
                        .stream()
                        .map(UserRole::getRoleId)
                        .collect(Collectors.toList());
                if(CollUtil.isEmpty(roleIds)){
                    return null;
                }
                // 查询角色关联的资源
                TenantIgnoreContext.set();
                List<String> resourceIds = this.roleResourceService.lambdaQuery()
                        .eq(RoleResource::getTenantId,account.getTenantId())
                        .in(RoleResource::getRoleId, roleIds)
                        .list()
                        .stream()
                        .map(RoleResource::getResourceId)
                        .collect(Collectors.toList());
                if(CollUtil.isEmpty(resourceIds)){
                    return null;
                }
                // 查询资源信息
                TenantIgnoreContext.set();
                return this.resourceService.lambdaQuery()
                        .eq(Resource::getTenantId,account.getTenantId())
                        .eq(Resource::getDeleted,DeletedEnum.NORMAL.getValue())
                        .eq(Resource::getDisabled, DisabledEnum.NORMAL.getValue())
                        .in(Resource::getResourceId,resourceIds)
                        .list()
                        .stream()
                        .map(item->item.convert(ResourceDTO.class))
                        .collect(Collectors.toList());

            }
        }
        return null;
    }


    private Account getAccountInfo(String accountId){
        // 账号信息
        TenantIgnoreContext.set();
        Account account = accountService.lambdaQuery()
                .eq(Account::getAccountId, accountId)
                .list()
                .stream()
                .findFirst()
                .orElse(null);
        BizAssert.isNull("账号信息不存在",account);
        return account;
    }


}
