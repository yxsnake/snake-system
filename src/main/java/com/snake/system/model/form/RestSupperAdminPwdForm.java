package com.snake.system.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author: snake
 * @create-time: 2024-07-19
 * @description:
 * @version: 1.0
 */
@Data
public class RestSupperAdminPwdForm {

    @Schema(name = "租户ID")
    private String tenantId;

    @Schema(name = "新密码")
    private String supperAdminPwd;

}
