package com.snake.system.model.form;

import io.github.yxsnake.pisces.web.core.converter.Convert;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: snake
 * @create-time: 2024-06-26
 * @description: Api创建对象
 * @version: 1.0
 */
@Schema(name = "Api创建对象")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiCreateForm implements Convert, Serializable {

    @Schema(name = "资源名称")
    @NotBlank(message = "资源名称不能为空")
    private String name;

    @Schema(name = "接口地址")
    @NotBlank(message = "接口地址不能为空")
    private String path;

    @Schema(name = "权限标识")
    @NotBlank(message = "权限标识不能为空")
    private String perm;

    @Schema(name = "备注")
    private String remark;

}
