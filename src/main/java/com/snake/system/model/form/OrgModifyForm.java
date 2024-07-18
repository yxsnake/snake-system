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
 * @description: 修改组织机构请求参数对象
 * @version: 1.0
 */
@Schema(name = "修改组织机构请求参数对象")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrgModifyForm implements Convert, Serializable {

    @Schema(name = "组织机构ID")
    @NotBlank(message = "组织机构ID不能为空")
    private String orgId;

    @Schema(name = "组织名称")
    private String orgName;

    @Schema(name = "负责人ID")
    private String personInCharge;

    @Schema(name = "负责人姓名")
    private String personInChargeName;

    @Schema(name = "负责人联系电话")
    private String contactPhone;

}
