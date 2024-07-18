package com.snake.system.model.dto;

import io.github.yxsnake.pisces.web.core.converter.Convert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: snake
 * @create-time: 2024-07-02
 * @description: 组织机构树结构定义
 * @version: 1.0
 */
@Data
@Schema(name = "组织机构树对象")
public class OrgTreeNode implements Convert, Serializable {

    @Schema(name = "上级机构ID")
    private String parentId;

    @Schema(name = "组织ID")
    private String orgId;

    @Schema(name = "组织名称")
    private String orgName;

    @Schema(name = "组织类型(1:公司,2:片区,3:分公司,4:部门,5:中心,6:组)")
    private Integer orgType;

    @Schema(name = "组织机构路径ID")
    private String orgPathIds;

    @Schema(name = "组织机构路径名称")
    private String orgPathNames;

    @Schema(name = "负责人ID")
    private String personInCharge;

    @Schema(name = "负责人姓名")
    private String personInChargeName;

    @Schema(name = "负责人联系电话")
    private String contactPhone;

    @Schema(name = "排序")
    private Integer orgOrder;


    @Schema(name = "子组织机构")
    private List<OrgTreeNode> children = new ArrayList<>();

}
