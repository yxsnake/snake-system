package com.snake.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.snake.system.model.dto.TenantDTO;
import com.snake.system.model.entity.Tenant;
import com.snake.system.model.form.PlatformInitTenantForm;
import com.snake.system.model.form.RestSupperAdminPwdForm;
import com.snake.system.model.form.TenantModifyForm;
import com.snake.system.model.form.TenantStopAndRepeatForm;

public interface TenantService extends IService<Tenant> {


    void initTenant(PlatformInitTenantForm initTenantForm);

    void tenantAddFunction(PlatformInitTenantForm addFunctionForm);

    void modify(TenantModifyForm form);

    void restSupperAdminPwd(RestSupperAdminPwdForm form);

    void stopAndRepeat(TenantStopAndRepeatForm form);

    TenantDTO detail(String tenantId);
}
