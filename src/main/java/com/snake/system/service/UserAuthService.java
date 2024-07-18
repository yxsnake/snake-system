package com.snake.system.service;

import com.snake.system.model.dto.ResourceDTO;
import com.snake.system.model.queries.SsoPermissionQueries;
import com.snake.system.model.queries.SsoRoleQueries;
import com.snake.system.model.queries.UserResourceEqualsQueries;

import java.util.List;

public interface UserAuthService {

    /**
     * 查询账号 权限标识集合
     * @param queries
     * @return
     */
    List<String> getPermissions(SsoPermissionQueries queries);

    /**
     * 查询账号 角色编码集合
     * @param queries
     * @return
     */
    List<String> getRoles(SsoRoleQueries queries);

    /**
     * 查询用户资源
     * @param queries
     * @return
     */
    List<ResourceDTO> getResources(UserResourceEqualsQueries queries);
}
