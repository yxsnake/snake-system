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
import java.util.Date;

/**
 * <p>
 * 账号表
 * </p>
 *
 * @author snake
 * @since 2024-06-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("account")
public class Account extends BaseEntity implements Convert, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 账号ID
     */
    @TableId(value = "account_id", type = IdType.AUTO)
    private String accountId;

    /**
     * 用户ID(员工 ID/会员 ID/供应商 ID,由渠道决定)
     */
    private String userId;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 账号归属渠道(11-员工，12-会员，13-供应商）
     */
    private Integer channel;

    /**
     * 登录方式(1-密码登录，2-手机短信验证码登录，3-扫码登录,4-微信授权登录，5-小程序授权登录)
     */
    private Integer loginWay;

    /**
     * 三方授权ID
     */
    private String openId;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 是否默认租户(0-否，1-是）
     */
    private Integer defaultTenantFlag;

    /**
     * 账号是否禁用(0-否，1-是）
     */
    private Integer disabled;

    /**
     * 是否超级管理员(0-否，1-是）
     */
    private Integer supperAdmin;

    /**
     * 创建时间
     */
    private Date createTime;

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

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;


}
