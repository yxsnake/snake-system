package com.snake.system.model.form;

import io.github.yxsnake.pisces.web.core.converter.Convert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: snake
 * @create-time: 2024-06-26
 * @description: 修改角色表单数据对象
 * @version: 1.0
 */
@Data
@Schema(name = "修改角色接收参数对象")
public class RoleModifyForm implements Convert, Serializable {

    @Schema(name = "角色ID")
    private String roleId;

    @Schema(name = "角色名称")
    private String roleName;

    @Schema(name = "角色备注")
    private String remark;
}
