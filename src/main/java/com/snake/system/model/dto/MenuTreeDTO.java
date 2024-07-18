package com.snake.system.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author: snake
 * @create-time: 2024-07-16
 * @description: 菜单树
 * @version: 1.0
 */
@Data
@Schema(name = "菜单树")
public class MenuTreeDTO {

    @Schema(name = "菜单ID")
    private String resourceId;

    @Schema(name = "菜单名称")
    private String name;

    @Schema(name = "上级资源ID")
    private String parentId;

    @Schema(name = "资源类型(0:目录,1:菜单, 2:按钮 ,3:外链,4:接口)")
    private Integer resourceType;

    @Schema(name = "路由地址")
    private String path;

    @Schema(name = "vue组件名称")
    private String component;

    @Schema(name = "权限标识")
    private String perm;

    @Schema(name = "跳转地址")
    private String redirect;

    @Schema(name = "图标")
    private String icon;

    @Schema(name = "排序")
    private Long sort;

    @Schema(name = "子级菜单")
    private List<MenuTreeDTO> children;

}
