package com.snake.system.model.form.ext;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import io.github.yxsnake.pisces.web.core.utils.BizAssert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Objects;

/**
 * @author: snake
 * @create-time: 2024-07-05
 * @description: 员工组织扩展表单对象
 * @version: 1.0
 */
@Data
@Schema(name = "员工组织扩展表单对象")
public class EmpOrgExtForm {

    @Schema(name = "组织 ID")
    private String orgId;

    @Schema(name = "是否主部门(1:是,0否)")
    private Integer flagMainDepartment = 0;

    public static void checkField(List<EmpOrgExtForm> forms){
        BizAssert.isTrue("组织信息不能为空", CollUtil.isEmpty(forms));
        for (EmpOrgExtForm form : forms) {
            BizAssert.isTrue("组织唯一标识不能为空", StrUtil.isBlank(form.getOrgId()));
            BizAssert.isTrue("是否主部门不能为空", Objects.isNull(form.getFlagMainDepartment()));
        }
    }
}
