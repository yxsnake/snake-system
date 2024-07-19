package com.snake.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.snake.system.mapper.RoleMapper;
import com.snake.system.model.dto.RoleDTO;
import com.snake.system.model.entity.Role;
import com.snake.system.model.entity.RoleResource;
import com.snake.system.model.entity.UserRole;
import com.snake.system.model.form.RoleCreateForm;
import com.snake.system.model.form.RoleModifyForm;
import com.snake.system.model.form.ext.InitTenantRoleInfoForm;
import com.snake.system.model.queries.RolePageEqualsQueries;
import com.snake.system.service.RoleResourceService;
import com.snake.system.service.RoleService;
import com.snake.system.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.github.yxsnake.pisces.web.core.base.QueryFilter;
import io.github.yxsnake.pisces.web.core.enums.DeletedEnum;
import io.github.yxsnake.pisces.web.core.enums.DisabledEnum;
import io.github.yxsnake.pisces.web.core.framework.model.BaseFuzzyQueries;
import io.github.yxsnake.pisces.web.core.utils.BizAssert;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author snake
 * @since 2024-06-25
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    private final UserRoleService userRoleService;

    private final RoleResourceService roleResourceService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoleDTO create(RoleCreateForm form) {
        // 校验角色是否已存在
        Role existRole = this.lambdaQuery().eq(Role::getRoleCode, form.getRoleCode()).list().stream().findFirst().orElse(null);
        BizAssert.isTrue("角色编码已存在",Objects.nonNull(existRole));
        Role role = form.convert(Role.class);
        String roleId = IdWorker.getIdStr();
        role.setRoleId(roleId);
        role.setDeleted(DeletedEnum.NORMAL.getValue());
        role.setDisabled(DisabledEnum.NORMAL.getValue());
        this.getBaseMapper().insert(role);
        return role.convert(RoleDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoleDTO modify(RoleModifyForm form) {
        Role dbRole = this.getBaseMapper().selectById(form.getRoleId());
        BizAssert.isTrue("角色信息不存在", Objects.isNull(dbRole));
        BizAssert.isTrue("平台初始化角色禁止修改",StrUtil.isNotBlank(dbRole.getPRoleId()));
        // 查询角色编码是否已被占用
        Role role = form.convert(Role.class);
        BeanUtils.copyProperties(role,dbRole);
        this.getBaseMapper().updateById(dbRole);
        return dbRole.convert(RoleDTO.class);
    }

    @Override
    public RoleDTO detail(String roleId) {
        Role role = this.getBaseMapper().selectById(roleId);
        if(Objects.nonNull(role)){
            return role.convert(RoleDTO.class);
        }
        return null;
    }

    @Override
    public IPage<RoleDTO> pageList(QueryFilter<RolePageEqualsQueries, BaseFuzzyQueries> queryFilter) {
        BizAssert.isTrue("queryFilter不能为空",Objects.isNull(queryFilter));
        RolePageEqualsQueries equalsQueries = queryFilter.getEqualsQueries();
        BizAssert.isTrue("equalsQueries不能为空",Objects.isNull(equalsQueries));
        BaseFuzzyQueries fuzzyQueries = queryFilter.getFuzzyQueries();
        BizAssert.isTrue("fuzzyQueries不能为空",Objects.isNull(fuzzyQueries));
        Page<Role> page = new Page<>(queryFilter.getPageNum(),queryFilter.getPageSize());
        return this.page(page,
                Wrappers.lambdaQuery(Role.class)
                        .eq(StrUtil.isNotBlank(equalsQueries.getRoleCode()), Role::getRoleCode, equalsQueries.getRoleCode())
                        .eq(StrUtil.isNotBlank(equalsQueries.getRoleName()), Role::getRoleName, equalsQueries.getRoleName())
                        .eq(Objects.nonNull(equalsQueries.getDisabled()), Role::getDisabled, equalsQueries.getDisabled())
        ).convert(role -> role.convert(RoleDTO.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByRoleId(String roleId) {
        // 查询角色信息
        Role role = this.getBaseMapper().selectById(roleId);
        BizAssert.isTrue("角色信息不存在", Objects.isNull(role));
        // 查询角色是否已授权给员工，如果授权给员工则不允许删除
        Long roleRefUserCount = userRoleService.lambdaQuery().eq(UserRole::getRoleId, roleId).count();
        BizAssert.isTrue("当前删除角色已关联用户，无法直接删除，请先解除与用户关系",roleRefUserCount>0);
        // 查询角色是否已关联资源
        Long roleRefResourceCount = roleResourceService.lambdaQuery().eq(RoleResource::getRoleId, roleId).count();
        BizAssert.isTrue("当前删除角色已关联资源，无法直接删除，请先解除与资源关系",roleRefResourceCount>0);
        role.setDeleted(DeletedEnum.DEL.getValue());
        this.getBaseMapper().updateById(role);
    }

    @Override
    public List<Role> initTenantBuildRoles(String tenantId, List<InitTenantRoleInfoForm> roleFormList) {
        List<Role> roles = Lists.newArrayList();
        for (InitTenantRoleInfoForm initTenantRoleInfoForm : roleFormList) {
            Role role = new Role();
            String roleId = IdWorker.getIdStr();
            role.setTenantId(tenantId);
            role.setRoleId(roleId);
            role.setPRoleId(initTenantRoleInfoForm.getPlatformRoleId());
            role.setRoleCode(initTenantRoleInfoForm.getRoleCode());
            role.setRoleName(initTenantRoleInfoForm.getRoleName());
            role.setRemark(initTenantRoleInfoForm.getRemark());
            role.setDisabled(DisabledEnum.NORMAL.getValue());
            roles.add(role);
        }
        return roles;
    }
}
