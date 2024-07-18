package com.snake.system.model.queries;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author: snake
 * @create-time: 2024-06-26
 * @description: 角色分页查询条件参数对象
 * @version: 1.0
 */
@Data
@Schema(name = "角色分页查询等值查询条件对象")
public class RolePageEqualsQueries {

    @Schema(name = "角色编码")
    private String roleCode;

    @Schema(name = "角色名称")
    private String roleName;

    @Schema(name = "是否禁用")
    private Integer disabled;
}
