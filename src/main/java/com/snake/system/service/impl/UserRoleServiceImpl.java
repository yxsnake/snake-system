package com.snake.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.snake.system.mapper.RoleMapper;
import com.snake.system.mapper.UserRoleMapper;
import com.snake.system.model.entity.Role;
import com.snake.system.model.entity.UserRole;
import com.snake.system.service.IUserRoleService;
import lombok.RequiredArgsConstructor;
import io.github.yxsnake.pisces.web.core.enums.DeletedEnum;
import io.github.yxsnake.pisces.web.core.enums.DisabledEnum;
import io.github.yxsnake.pisces.web.core.utils.BizAssert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户角色关联表 服务实现类
 * </p>
 *
 * @author snake
 * @since 2024-06-25
 */
@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

    private final RoleMapper roleMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bingEmpRole(String empId,List<String> roleIds) {
        // 校验角色的合法性
        Long roleCount = roleMapper.selectCount(
                Wrappers.lambdaQuery(Role.class)
                        .eq(Role::getDeleted, DeletedEnum.NORMAL.getValue())
                .eq(Role::getDisabled, DisabledEnum.NORMAL.getValue())
                .in(Role::getRoleId, roleIds)
        );
        BizAssert.isTrue("部分角色已禁用或不存在",roleCount-roleIds.size()!=0);

        List<UserRole> list = this.lambdaQuery().in(UserRole::getUserId, empId).list();

        if(CollUtil.isNotEmpty(list)){
            this.getBaseMapper().deleteBatchIds(list.stream().map(UserRole::getId).collect(Collectors.toList()));
        }

        List<UserRole> userRoles = Lists.newArrayList();
        for (String roleId : roleIds) {
            UserRole userRole = new UserRole();
            userRole.setId(IdWorker.getIdStr());
            userRole.setUserId(empId);
            userRole.setRoleId(roleId);
            userRoles.add(userRole);
        }

        this.saveBatch(userRoles);

    }
}
