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
@Schema(name = "停用或启用租户")
public class TenantStopAndRepeatForm {

    private String tenantId;

    private Integer status;


}
