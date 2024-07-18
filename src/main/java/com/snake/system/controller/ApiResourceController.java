package com.snake.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.snake.system.model.dto.ResourceDTO;
import com.snake.system.model.form.ApiCreateForm;
import com.snake.system.model.form.ApiModifyForm;
import com.snake.system.model.queries.ApiResourcePageEqualsQueries;
import com.snake.system.service.ApiResourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.github.yxsnake.pisces.web.core.base.BaseFuzzyQueries;
import io.github.yxsnake.pisces.web.core.base.QueryFilter;
import io.github.yxsnake.pisces.web.core.base.Result;
import io.github.yxsnake.pisces.web.core.framework.controller.BaseController;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: snake
 * @create-time: 2024-07-15
 * @description: API资源管理
 * @version: 1.0
 */
@Tag(name = "资源API")
@Slf4j
@RestController
@RequestMapping(value = "/api-resource")
@RequiredArgsConstructor
public class ApiResourceController extends BaseController {

    private final ApiResourceService apiResourceService;

    @Operation(summary = "创建api")
    @PostMapping(value = "/create")
    public ResponseEntity<Result<ResourceDTO>> createApi(@RequestBody @Validated ApiCreateForm form){
        return success(apiResourceService.createApi(form));
    }

    @Operation(summary = "修改api")
    @PostMapping(value = "/modify")
    public ResponseEntity<Result<ResourceDTO>> modifyApi(@RequestBody @Validated ApiModifyForm form){
        return success(apiResourceService.modifyApi(form));
    }

    @Operation(summary = "分页查询api")
    @PostMapping(value = "/pageList")
    public ResponseEntity<Result<IPage<ResourceDTO>>> pageList(@RequestBody QueryFilter<ApiResourcePageEqualsQueries, BaseFuzzyQueries> queryFilter){
        return success(apiResourceService.pageList(queryFilter));
    }
}
