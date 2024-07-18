package com.snake.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 职位表
 * </p>
 *
 * @author snake
 * @since 2024-06-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("position")
public class Position implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 岗位ID
     */
    @TableId(value = "position_id", type = IdType.AUTO)
    private String positionId;

    /**
     * 平台岗位ID
     */
    private String pPositionId;

    /**
     * 岗位编码
     */
    private String positionCode;

    /**
     * 上级组织ID
     */
    private String positionName;

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 租户唯一标识
     */
    private String tenantId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人ID
     */
    private String createUserId;

    /**
     * 创建人名称
     */
    private String createUserName;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 修改人ID
     */
    private String updateUserId;

    /**
     * 修改人名称
     */
    private String updateUserName;


}
