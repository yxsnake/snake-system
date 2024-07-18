package com.snake.system.model.enums;

import io.github.yxsnake.pisces.web.core.base.IBaseEnum;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author: snake
 * @create-time: 2024-06-25
 * @description: 用户性别 枚举
 * @version: 1.0
 */
@Getter
public enum GenderEnum implements IBaseEnum<Integer> {

    FEMALE(0,"女"),

    MALE(1,"男"),

    HIDE(2,"保密"),

    ;

    private final Integer value;

    private final String label;

    GenderEnum(final Integer value,final String label){
        this.value = value;
        this.label = label;
    }

    public static GenderEnum getInstance(final Integer value){
        return Arrays.asList(values()).stream().filter(item->item.getValue().equals(value)).findFirst().orElse(null);
    }


}
