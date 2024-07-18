package com.snake.system.controller;

import com.snake.system.model.dto.MenuTreeDTO;
import com.snake.system.model.dto.ResourceDTO;
import com.snake.system.model.form.MenuCreateForm;
import com.snake.system.model.form.MenuModifyForm;
import com.snake.system.service.MenuService;
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
 * @create-time: 2024-07-15
 * @description: 菜单管理
 * @version: 1.0
 */
@Tag(name = "菜单管理")
@Slf4j
@RestController
@RequestMapping(value = "/menu")
@RequiredArgsConstructor
public class MenuController extends BaseController {

    private final MenuService menuService;

    @Operation(summary = "创建菜单或按钮")
    @PostMapping(value = "/create")
    public ResponseEntity<Result<ResourceDTO>> createMenuOrButton(@RequestBody @Validated MenuCreateForm form){
        return success(menuService.createMenuOrButton(form));
    }

    @Operation(summary = "修改菜单或按钮")
    @PostMapping(value = "/modify")
    public ResponseEntity<Result<ResourceDTO>> modifyMenuOrButton(@RequestBody @Validated MenuModifyForm form){
        return success(menuService.modifyMenuOrButton(form));
    }

    @Operation(summary = "删除菜单")
    @GetMapping(value = "/delete")
    public ResponseEntity<Result<Boolean>> deleteResourceById(@RequestParam("menuId") String menuId){
        menuService.deleteMenuById(menuId);
        return success(Boolean.TRUE);
    }

    @Operation(summary = "获取菜单树")
    @GetMapping(value = "/tree")
    public ResponseEntity<Result<MenuTreeDTO>> tree(){
        return success(menuService.tree());
    }
}
