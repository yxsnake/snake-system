package com.snake.system.model.form;

import io.github.yxsnake.pisces.web.core.converter.Convert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name = "创建角色接收参数对象")
public class RoleCreateForm implements Convert, Serializable {

    @Schema(name = "角色名称")
    private String roleName;

    @Schema(name = "角色编码")
    private String roleCode;

    @Schema(name = "角色备注")
    private String remark;

}
