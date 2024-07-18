package com.snake.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.snake.system.model.dto.EmpDTO;
import com.snake.system.model.dto.EmpDetailDTO;
import com.snake.system.model.form.EmpCreateForm;
import com.snake.system.model.form.EmpEnableOrDisableForm;
import com.snake.system.model.form.EmpModifyForm;
import com.snake.system.model.queries.EmpPageEqualsQueries;
import com.snake.system.model.queries.EmpPageFuzzyQueries;
import com.snake.system.service.EmpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.github.yxsnake.pisces.web.core.base.QueryFilter;
import io.github.yxsnake.pisces.web.core.base.Result;
import io.github.yxsnake.pisces.web.core.framework.controller.BaseController;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author: snake
 * @create-time: 2024-06-26
 * @description: 员工相关 API
 * @version: 1.0
 */
@Tag(name = "员工API")
@Slf4j
@RestController
@RequestMapping(value = "/emp")
@RequiredArgsConstructor
public class EmpController extends BaseController {

    private final EmpService empService;

    @Operation(summary = "登录逻辑使用-根据账号ID查询员工信息")
    @GetMapping(value = "/account")
    public ResponseEntity<Result<EmpDTO>> get(@RequestParam("accountId") String accountId){
        return success(empService.get(accountId));
    }

    @Operation(summary = "创建员工")
    @PostMapping(value = "/create")
    public ResponseEntity<Result<EmpDTO>> create(@RequestBody @Validated EmpCreateForm form){
        return success(empService.create(form));
    }

    @Operation(summary = "修改员工")
    @PostMapping(value = "/modify")
    public ResponseEntity<Result<EmpDTO>> modify(@RequestBody @Validated EmpModifyForm form){
        return success(empService.modify(form));
    }

    @Operation(summary = "查询员工详情")
    @GetMapping(value = "/detail")
    public ResponseEntity<Result<EmpDetailDTO>> detail(@RequestParam("empId") String empId){
        return success(empService.detail(empId));
    }

    @Operation(summary = "员工账号启用或禁用")
    @PostMapping(value = "/enableOrDisable")
    public ResponseEntity<Result<EmpDTO>> enableOrDisable(@RequestBody @Validated EmpEnableOrDisableForm form){
        return success(empService.enableOrDisable(form));
    }

    @Operation(summary = "分页查询员工列表")
    @PostMapping(value = "/pageList")
    public ResponseEntity<Result<IPage<EmpDTO>>> pageList(@RequestBody QueryFilter<EmpPageEqualsQueries, EmpPageFuzzyQueries> queryFilter){
        return success(empService.pageList(queryFilter));
    }


}
