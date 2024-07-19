package com.snake.system.model.form.ext;

import cn.hutool.core.util.StrUtil;
import io.github.yxsnake.pisces.web.core.converter.Convert;
import io.github.yxsnake.pisces.web.core.utils.BizAssert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "初始化相关租户信息")
public class InitTenantInfoForm implements Convert {

    @Schema(name = "租户ID")
    private String tenantId;

    @Schema(name = "租户名称")
    private String tenantName;

    @Schema(name = "租户简称")
    private String introduction;

    @Schema(name = "租户联系人")
    private String supperPerson;

    @Schema(name = "联系邮箱")
    private String email;

    @Schema(name = "联系电话")
    private String phone;

    @Schema(name = "租户超级管理员账号")
    private String supperAccount;

    @Schema(name = "租户超级管理员密码")
    private String supperPassword;

    public void checkNull(){
        BizAssert.isTrue("租户ID不能为空", StrUtil.isBlank(tenantId));
        BizAssert.isTrue("租户名称不能为空", StrUtil.isBlank(tenantName));
        BizAssert.isTrue("租户联系人不能为空", StrUtil.isBlank(introduction));
        BizAssert.isTrue("联系邮箱不能为空", StrUtil.isBlank(supperPerson));
        BizAssert.isTrue("联系电话不能为空", StrUtil.isBlank(email));
        BizAssert.isTrue("联系电话不能为空", StrUtil.isBlank(phone));
        BizAssert.isTrue("租户超级管理员账号不能为空", StrUtil.isBlank(supperAccount));
        BizAssert.isTrue("租户超级管理员密码不能为空", StrUtil.isBlank(supperPassword));
    }
}
