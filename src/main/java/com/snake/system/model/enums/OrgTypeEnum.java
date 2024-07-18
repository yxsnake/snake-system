package com.snake.system.model.enums;

import io.github.yxsnake.pisces.web.core.base.IBaseEnum;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author: snake
 * @create-time: 2024-07-01
 * @description: 组织机构类型
 * @version: 1.0
 */
@Getter
public enum OrgTypeEnum implements IBaseEnum<Integer> {

    COMPANY(1,"公司"),

    AREA(2,"片区"),

    BRANCH(3,"分公司"),

    DEPARTMENT(4,"部门"),

    CENTER(5,"中心"),

    GROUP(6,"组"),

    ;



    private final Integer value;

    private final String label;

    OrgTypeEnum(final Integer value,final String label){
        this.value = value;
        this.label = label;
    }

    public static OrgTypeEnum getInstance(final Integer value){
        return Arrays.asList(values()).stream().filter(item->item.getValue().equals(value)).findFirst().orElse(null);
    }
}
