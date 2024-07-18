package com.snake.system.model.dto;

import io.github.yxsnake.pisces.web.core.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name = "角色传输对象")
public class RoleDTO extends BaseEntity implements Serializable {

    @Schema(name = "角色ID")
    private String roleId;

    @Schema(name = "平台角色ID")
    private String pRoleId;

    @Schema(name = "角色名称")
    private String roleName;

    @Schema(name = "角色编码")
    private String roleCode;

    @Schema(name = "是否禁用（0-否，1-是）")
    private Integer disabled;

    @Schema(name = "是否删除（0-否，1-是）")
    private Integer deleted;

    @Schema(name = "备注")
    private String remark;

    @Schema(name = "租户唯一标识")
    private String tenantId;
}
