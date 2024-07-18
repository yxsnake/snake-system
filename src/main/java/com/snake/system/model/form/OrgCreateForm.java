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
 * @description: 创建组织机构接收参数对象
 * @version: 1.0
 */
@Schema(name = "创建组织机构接收参数对象")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrgCreateForm implements Convert, Serializable {

    @Schema(name = "上级机构ID")
    private String parentId;

    @Schema(name = "组织名称")
    private String orgName;

    @Schema(name = "组织类型(1:公司,2:片区,3:分公司,4:部门,5:中心,6:组)")
    private Integer orgType;

    @Schema(name = "负责人ID")
    private String personInCharge;

    @Schema(name = "负责人联系电话")
    private String contactPhone;

}
