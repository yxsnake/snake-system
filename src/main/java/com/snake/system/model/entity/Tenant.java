package com.snake.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.yxsnake.pisces.web.core.converter.Convert;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tenant")
public class Tenant implements Convert, Serializable {
    /**
     * 租户唯一标识
     */
    @TableId(type = IdType.NONE)
    private String tenantId;
    /**
     * 租户名称
     */
    private String tenantName;
    /**
     * 租户简称
     */
    private String introduction;
    /**
     * 租户联系人
     */
    private String supperPerson;
    /**
     * 联系邮箱
     */
    private String email;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 租户超级管理员账号
     */
    private String supperAccount;
    /**
     * 租户超级管理员密码
     */
    private String supperPassword;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;

}
