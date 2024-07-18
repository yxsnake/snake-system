package com.snake.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.snake.system.model.entity.Tenant;
import com.snake.system.model.form.PlatformInitTenantForm;

public interface TenantService extends IService<Tenant> {


    void initTenant(PlatformInitTenantForm initTenantForm);
}
