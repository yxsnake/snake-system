package com.snake.system.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: snake
 * @create-time: 2024-06-26
 * @description: 员工详情查询对象
 * @version: 1.0
 */
@Schema(name = "员工详情传输对象")
@Data
public class EmpDetailDTO implements Serializable {

    @Schema(name = "员工ID")
    private String empId;

    @Schema(name = "员工姓名")
    private String realName;

    @Schema(name = "员工头像")
    private String avatar;

    @Schema(name = "邮箱")
    private String email;

    @Schema(name = "联系电话")
    private String phone;

    @Schema(name = "性别(0-女，1-男)")
    private Integer gender;

    @Schema(name = "租户ID")
    private String tenantId;

    @Schema(name = "员工账号")
    private String account;

    @Schema(name = "员工拥有的角色信息列表")
    private List<RoleDTO> roles;

    @Schema(name = "员工拥有组织信息列表")
    private List<OrgDTO> orgies;

}
