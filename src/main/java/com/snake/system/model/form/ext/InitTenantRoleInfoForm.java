package com.snake.system.model.form.ext;

import io.github.yxsnake.pisces.web.core.converter.Convert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "初始化相关角色信息")
public class InitTenantRoleInfoForm implements Convert {

    @Schema(name = "平台角色ID")
    private String platformRoleId;

    @Schema(name = "角色名称")
    private String roleName;

    @Schema(name = "角色编码")
    private String roleCode;

    @Schema(name = "角色备注")
    private String remark;

}
