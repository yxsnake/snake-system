package com.snake.system.model.enums;

import io.github.yxsnake.pisces.web.core.base.IBaseEnum;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author: snake
 * @create-time: 2024-06-25
 * @description: 账号是否默认租户 枚举
 * @version: 1.0
 */
@Getter
public enum AccountDefaultTenantEnum implements IBaseEnum<Integer> {

    YES(1,"是"),

    NO(0,"否"),

    ;

    private final Integer value;

    private final String label;

    AccountDefaultTenantEnum(final Integer value,final String label){
        this.value = value;
        this.label = label;
    }

    public static AccountDefaultTenantEnum getInstance(final Integer value){
        return Arrays.asList(values()).stream().filter(item->item.getValue().equals(value)).findFirst().orElse(null);
    }
}
