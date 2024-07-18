package com.snake.system.model.enums;

import io.github.yxsnake.pisces.web.core.base.IBaseEnum;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author: snake
 * @create-time: 2024-06-25
 * @description: 登录类型 枚举
 * @version: 1.0
 */
@Getter
public enum AccountLoginWayEnum implements IBaseEnum<Integer> {

    PWD(1,"账号密码登录"),

    MOBILE_SMS(2,"手机短信验证码登录"),

    SWEEP_CODE(3,"扫码登录"),

    WECHAT_AUTH(4,"微信授权登录"),

    WECHAT_SMALL_PROGRAM(5,"微信小程序登录"),






    ;

    private final Integer value;

    private final String label;

    AccountLoginWayEnum(final Integer value,final String label){
        this.value = value;
        this.label = label;
    }

    public static AccountLoginWayEnum getInstance(final Integer value){
        return Arrays.asList(values()).stream().filter(item->item.getValue().equals(value)).findFirst().orElse(null);
    }
}
