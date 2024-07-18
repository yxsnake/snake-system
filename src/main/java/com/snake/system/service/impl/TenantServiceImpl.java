package com.snake.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snake.system.mapper.TenantMapper;
import com.snake.system.model.entity.Tenant;
import com.snake.system.model.form.PlatformInitTenantForm;
import com.snake.system.service.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements TenantService {

    @Override
    public void initTenant(PlatformInitTenantForm initTenantForm) {

    }
}
