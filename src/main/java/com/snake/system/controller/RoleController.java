package com.snake.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.snake.system.model.dto.RoleDTO;
import com.snake.system.model.form.RoleCreateForm;
import com.snake.system.model.form.RoleModifyForm;
import com.snake.system.model.queries.RolePageEqualsQueries;
import com.snake.system.service.IRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.github.yxsnake.pisces.web.core.base.QueryFilter;
import io.github.yxsnake.pisces.web.core.base.Result;
import io.github.yxsnake.pisces.web.core.framework.controller.BaseController;
import io.github.yxsnake.pisces.web.core.framework.model.BaseFuzzyQueries;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "角色API")
@Slf4j
@RestController
@RequestMapping(value = "/role")
@RequiredArgsConstructor
public class RoleController extends BaseController {

    private final IRoleService roleService;

    @Operation(summary = "创建角色")
    @PostMapping(value = "/create")
    public ResponseEntity<Result<RoleDTO>> create(@RequestBody @Validated RoleCreateForm form){
        return success(roleService.create(form));
    }
    @Operation(summary = "修改角色")
    @PostMapping(value = "/modify")
    public ResponseEntity<Result<RoleDTO>> modify(@RequestBody @Validated RoleModifyForm form){
        return success(roleService.modify(form));
    }

    @Operation(summary = "查询角色详情")
    @GetMapping(value = "/detail")
    public ResponseEntity<Result<RoleDTO>> detail(@RequestParam("roleId") String roleId){
        return success(roleService.detail(roleId));
    }

    @Operation(summary = "分页查询角色")
    @PostMapping(value = "/pageList")
    public ResponseEntity<Result<IPage<RoleDTO>>> pageList(@RequestBody QueryFilter<RolePageEqualsQueries, BaseFuzzyQueries> queryFilter){
        return success(roleService.pageList(queryFilter));
    }

    @Operation(summary = "删除角色")
    @GetMapping(value = "/deleteByRoleId")
    public ResponseEntity<Result<Boolean>> deleteByRoleId(@RequestParam("roleId") String roleId){
        roleService.deleteByRoleId(roleId);
        return success(Boolean.TRUE);
    }
}
