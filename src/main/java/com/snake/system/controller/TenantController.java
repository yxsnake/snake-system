package com.snake.system.controller;

import com.snake.system.model.form.PlatformInitTenantForm;
import com.snake.system.service.TenantService;
import io.github.yxsnake.pisces.web.core.base.Result;
import io.github.yxsnake.pisces.web.core.framework.controller.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "租户API")
@Slf4j
@RestController
@RequestMapping(value = "/tenant")
@RequiredArgsConstructor
public class TenantController extends BaseController {

    private final TenantService tenantService;

    @Operation(summary = "初始化租户")
    @PostMapping(value = "/initTenant")
    public ResponseEntity<Result<Boolean>> initTenant(@RequestBody PlatformInitTenantForm initTenantForm){
        tenantService.initTenant(initTenantForm);
        return success(Boolean.TRUE);
    }

}
