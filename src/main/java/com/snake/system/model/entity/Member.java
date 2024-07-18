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
 * 会员表
 * </p>
 *
 * @author snake
 * @since 2024-06-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("member")
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 会员 ID
     */
    @TableId(value = "member_id", type = IdType.AUTO)
    private String memberId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别(0-女，1-男，2-保密)
     */
    private Integer gender;

    /**
     * 注册时间
     */
    private LocalDateTime registerTime;

    /**
     * 修改时间
     */
    private Date updateTime;


}
