package com.snake.system.model.form;

import io.github.yxsnake.pisces.web.core.converter.Convert;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: snake
 * @create-time: 2024-06-26
 * @description: 菜单按钮创建对象
 * @version: 1.0
 */
@Schema(name = "菜单按钮创建对象")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuCreateForm implements Convert, Serializable {

    @Schema(name = "上级菜单ID")
    private String parentId;

    @Schema(name = "菜单名称")
    @NotBlank(message = "菜单名称不能为空")
    private String name;

    @Schema(name = "资源类型(0:目录,1:菜单, 2:按钮 ,3:外链,4:接口)")
    @NotNull(message = "不能为空")
    private Integer resourceType;

    @Schema(name = "路由路径(浏览器地址栏路径)")
    @NotBlank(message = "路由路径不能为空")
    private String path;

    @Schema(name = "组件路径(vue页面完整路径，省略.vue后缀)")
    private String component;

    @Schema(name = "按钮权限标识")
    private String perm;

    @Schema(name = "菜单图标")
    @NotBlank(message = "菜单图标不能为空")
    private String icon;

    @Schema(name = "")
    private String remark;

    @Schema(name = "排序")
    private Long sort;

}
