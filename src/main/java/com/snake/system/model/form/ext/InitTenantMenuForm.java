package com.snake.system.model.form.ext;

import io.github.yxsnake.pisces.web.core.converter.Convert;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(name = "初始化相关菜单信息")
public class InitTenantMenuForm implements Convert {

    @Schema(name = "平台菜单ID")
    private String platformMenuId;

    @Schema(name = "平台上级菜单ID")
    private String platformParentId;

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

    @Schema(name = "备注")
    private String remark;

    @Schema(name = "排序")
    private Long sort;
}
