package com.snake.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.snake.system.model.entity.UserRole;

import java.util.List;

/**
 * <p>
 * 用户角色关联表 服务类
 * </p>
 *
 * @author snake
 * @since 2024-06-25
 */
public interface IUserRoleService extends IService<UserRole> {
    /**
     * 员工绑定角色
     * @param roleIds
     */
    void bingEmpRole(String empId,List<String> roleIds);
}
