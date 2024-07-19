package com.snake.system.controller;

import com.snake.system.model.form.PlatformInitTenantForm;
import com.snake.system.model.form.RestSupperAdminPwdForm;
import com.snake.system.model.form.TenantModifyForm;
import com.snake.system.model.form.TenantStopAndRepeatForm;
import com.snake.system.service.TenantService;
import io.github.yxsnake.pisces.web.core.base.Result;
import io.github.yxsnake.pisces.web.core.framework.controller.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "租户增复购")
    @PostMapping(value = "/tenantAddFunction")
    public ResponseEntity<Result<Boolean>> tenantAddFunction(@RequestBody PlatformInitTenantForm addFunctionForm){
        tenantService.tenantAddFunction(addFunctionForm);
        return success(Boolean.TRUE);
    }

    @Operation(summary = "租户修改")
    @PostMapping(value = "/modify")
    public ResponseEntity<Result<Boolean>> modify(@RequestBody TenantModifyForm form){
        tenantService.modify(form);
        return success(Boolean.TRUE);
    }

    @Operation(summary = "租户超级管理员密码重置")
    @PostMapping(value = "/restSupperAdminPwd")
    public ResponseEntity<Result<Boolean>> restSupperAdminPwd(@RequestBody RestSupperAdminPwdForm form){
        tenantService.restSupperAdminPwd(form);
        return success(Boolean.TRUE);
    }

    @Operation(summary = "租户超级管理员密码重置")
    @PostMapping(value = "/stopAndRepeat")
    public ResponseEntity<Result<Boolean>> stopAndRepeat(@RequestParam TenantStopAndRepeatForm form){
        tenantService.stopAndRepeat(form);
        return success(Boolean.TRUE);
    }


}
