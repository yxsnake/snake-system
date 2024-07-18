package com.snake.system.model.enums;

import io.github.yxsnake.pisces.web.core.base.IBaseEnum;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author: snake
 * @create-time: 2024-07-05
 * @description: 是否主部门组织
 * @version: 1.0
 */

@Getter
public enum OrgFlagMainDepartmentEnum implements IBaseEnum<Integer> {

    YES(1,"是"),

    NO(0,"否"),

    ;

    private final Integer value;

    private final String label;

    OrgFlagMainDepartmentEnum(final Integer value,final String label){
        this.value = value;
        this.label = label;
    }

    public static OrgFlagMainDepartmentEnum getInstance(final Integer value){
        return Arrays.asList(values()).stream().filter(item->item.getValue().equals(value)).findFirst().orElse(null);
    }
}
