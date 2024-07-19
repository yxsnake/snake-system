package com.snake.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.github.yxsnake.pisces.web.core.base.BaseEntity;
import io.github.yxsnake.pisces.web.core.converter.Convert;

import java.io.Serializable;

/**
 * <p>
 * 组织表
 * </p>
 *
 * @author snake
 * @since 2024-06-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("org")
public class Org extends BaseEntity implements Convert,Serializable {

    private static final long serialVersionUID = 1L;

    public static final String ORG_ROOT = "0";
    /**
     * 组织ID
     */
    @TableId(value = "org_id", type = IdType.AUTO)
    private String orgId;

    /**
     * 上级组织ID
     */
    private String parentId;

    /**
     * 组织名称
     */
    private String orgName;

    /**
     * 组织类型(1:公司,2:片区,3:分公司,4:部门,5:中心,6:组)
     */
    private Integer orgType;

    /**
     * 组织全路径ID,层级之间用 | 连接
     */
    private String orgPathIds;

    /**
     * 组织全路径名称,层级之间用 | 连接
     */
    private String orgPathNames;

    /**
     * 负责人ID
     */
    private String personInCharge;

    /**
     * 负责人名称
     */
    private String personInChargeName;

    /**
     * 负责人联系电话
     */
    private String contactPhone;
    /**
     * 排序
     */
    private Integer orgOrder;
    /**
     * 是否删除（0-否，1-是）
     */
    private Integer deleted;

    /**
     * 租户唯一标识
     */
    private String tenantId;



}
