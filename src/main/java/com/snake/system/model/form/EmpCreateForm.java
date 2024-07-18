package com.snake.system.model.form;

import com.snake.system.model.form.ext.EmpOrgExtForm;
import io.github.yxsnake.pisces.web.core.converter.Convert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author: snake
 * @create-time: 2024-06-26
 * @description: 创建员工表单对象
 * @version: 1.0
 */
@Schema(name = "创建员工表单对象")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmpCreateForm implements Convert, Serializable {

    @Schema(name = "员工姓名")
    private String realName;

    @Schema(name = "员工头像")
    private String avatar;

    @Schema(name = "邮箱")
    private String email;

    @Schema(name = "联系电话")
    private String phone;

    @Schema(name = "性别(0-女，1-男)")
    private Integer gender;

    @Schema(name = "所属组织ID集合")
    private List<EmpOrgExtForm> orgies;

    @Schema(name = "授权角色ID")
    private List<String> roleIds;
}
