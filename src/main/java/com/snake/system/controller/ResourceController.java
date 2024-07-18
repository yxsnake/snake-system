package com.snake.system.controller;

import com.snake.system.model.dto.ResourceDTO;
import com.snake.system.service.IResourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.github.yxsnake.pisces.web.core.base.Result;
import io.github.yxsnake.pisces.web.core.framework.controller.BaseController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: snake
 * @create-time: 2024-07-05
 * @description: 资源相关 API
 * @version: 1.0
 */
@Tag(name = "资源API")
@Slf4j
@RestController
@RequestMapping(value = "/resource")
@RequiredArgsConstructor
public class ResourceController extends BaseController {

    private final IResourceService resourceService;

    @Operation(summary = "查询资源详情")
    @GetMapping(value = "/detail")
    public ResponseEntity<Result<ResourceDTO>> detail(@RequestParam("resourceId") String resourceId){
        return success(resourceService.detail(resourceId));
    }
}
