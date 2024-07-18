package com.snake.system.model.enums;

import io.github.yxsnake.pisces.web.core.base.IBaseEnum;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum AccountStatusEnum implements IBaseEnum<Integer> {

    NORMAL(0, "正常"),

    DISABLE(1, "禁用"),

    ;
    private final Integer value;

    private final String label;

    AccountStatusEnum(final Integer value, final String label) {
        this.value = value;
        this.label = label;
    }

    public static AccountStatusEnum getInstance(final Integer value) {
        return Arrays.asList(values()).stream().filter(item -> item.getValue().equals(value)).findFirst().orElse(null);
    }
}
