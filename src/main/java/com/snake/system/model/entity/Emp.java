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
 * 员工表
 * </p>
 *
 * @author snake
 * @since 2024-06-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("emp")
public class Emp extends BaseEntity implements Convert, Serializable {

    public static final String DEFAULT_AVATAR = "https://i1.hdslb.com/bfs/face/98a570a6c6d6a263bcb0cba9e15e492125e9d310.jpg@120w_120h_1c";

    private static final long serialVersionUID = 1L;

    /**
     * 员工ID
     */
    @TableId(value = "emp_id", type = IdType.AUTO)
    private String empId;

    /**
     * 员工姓名
     */
    private String realName;

    /**
     * 头像
     */
    private String avatar = DEFAULT_AVATAR;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 性别(0-女，1-男)
     */
    private Integer gender;

    /**
     * 租户ID
     */
    private String tenantId;
    /**
     * 是否租户超级管理员(0-否，1-是）
     */
    private Integer supperAdmin;
}
