package com.snake.system.model.form;

import io.github.yxsnake.pisces.web.core.converter.Convert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: snake
 * @create-time: 2024-06-26
 * @description: 员工账号启动或禁用接收参数对象
 * @version: 1.0
 */
@Schema(name = "员工账号启动或禁用接收参数对象")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmpEnableOrDisableForm implements Convert, Serializable {

    @Schema(name = "员工 ID")
    private String empId;

    @Schema(name = "是否禁用(0-启用，1-禁用)")
    private Integer status;

}
