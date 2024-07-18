package com.snake.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.snake.system.model.dto.RoleDTO;
import com.snake.system.model.entity.Role;
import com.snake.system.model.form.RoleCreateForm;
import com.snake.system.model.form.RoleModifyForm;
import com.snake.system.model.queries.RolePageEqualsQueries;
import io.github.yxsnake.pisces.web.core.base.QueryFilter;
import io.github.yxsnake.pisces.web.core.framework.model.BaseFuzzyQueries;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author snake
 * @since 2024-06-25
 */
public interface RoleService extends IService<Role> {
    /**
     * 创建角色
     * @param form
     * @return
     */
    RoleDTO create(RoleCreateForm form);

    /**
     * 修改角色
     * @param form
     * @return
     */
    RoleDTO modify(RoleModifyForm form);

    /**
     * 查询角色详情
     * @param roleId
     * @return
     */
    RoleDTO detail(String roleId);

    /**
     * 分页查询角色列表
     * @param queryFilter
     * @return
     */
    IPage<RoleDTO> pageList(QueryFilter<RolePageEqualsQueries, BaseFuzzyQueries> queryFilter);

    /**
     * 删除角色
     * @param roleId
     */
    void deleteByRoleId(String roleId);
}
