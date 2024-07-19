package com.snake.system.model.form;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author: snake
 * @create-time: 2024-07-19
 * @description:
 * @version: 1.0
 */
public class TenantModifyForm {

    @Schema(name = "租户ID")
    private String tenantId;

    @Schema(name = "租户名称")
    private String tenantName;

    @Schema(name = "租户简称")
    private String introduction;

    @Schema(name = "租户联系人")
    private String supperPerson;

    @Schema(name = "联系邮箱")
    private String email;

    @Schema(name = "联系电话")
    private String phone;

}
