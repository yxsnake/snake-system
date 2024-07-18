package com.snake.system.controller;

import com.snake.system.model.dto.AccountDTO;
import com.snake.system.model.queries.DefaultTenantAccountEqualsQueries;
import com.snake.system.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.github.yxsnake.pisces.web.core.base.Result;
import io.github.yxsnake.pisces.web.core.framework.controller.BaseController;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author: snake
 * @create-time: 2024-06-26
 * @description: 账号相关 API
 * @version: 1.0
 */
@Tag(name = "账号相关API")
@Slf4j
@RestController
@RequestMapping(value = "/account")
@RequiredArgsConstructor
public class AccountController extends BaseController {

    private final IAccountService accountService;

    @Operation(summary = "查询默认租户账号信息")
    @PostMapping(value = "/findDefaultTenantAccount")
    public ResponseEntity<Result<AccountDTO>> findDefaultTenantAccount(@RequestBody @Validated DefaultTenantAccountEqualsQueries queries){
        return success(accountService.findDefaultTenantAccount(queries));
    }

    @Operation(summary = "查询账号信息")
    @GetMapping(value = "/findByAccountId")
    public ResponseEntity<Result<AccountDTO>> findByAccountId(@RequestParam("accountId") String accountId){
        return success(accountService.findByAccountId(accountId));
    }
}
