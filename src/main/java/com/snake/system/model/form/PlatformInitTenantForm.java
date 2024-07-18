package com.snake.system.model.form;

import com.snake.system.model.form.ext.InitTenantApiResourceForm;
import com.snake.system.model.form.ext.InitTenantInfoForm;
import com.snake.system.model.form.ext.InitTenantMenuForm;
import com.snake.system.model.form.ext.InitTenantRoleInfoForm;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "初始化租户接受参数")
public class PlatformInitTenantForm {

    @Schema(name = "租户信息")
    private InitTenantInfoForm tenantInfoForm;

    @Schema(name = "角色信息")
    private List<InitTenantRoleInfoForm> roleFormList;

    @Schema(name = "菜单信息")
    private List<InitTenantMenuForm> menuFormList;

    @Schema(name = "接口信息")
    private List<InitTenantApiResourceForm> apiResourceFormList;

    public void checkTenantInfoForm(){

    }
    public void checkRoleFormList(){

    }
    public void checkMenuFormList(){

    }
    public void checkApiResourceFormList(){

    }
}
