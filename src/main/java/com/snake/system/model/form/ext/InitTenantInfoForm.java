package com.snake.system.model.form.ext;

import io.github.yxsnake.pisces.web.core.converter.Convert;
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

}
