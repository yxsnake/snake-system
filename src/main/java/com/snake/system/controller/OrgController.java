package com.snake.system.controller;

import com.snake.system.model.dto.OrgDTO;
import com.snake.system.model.dto.OrgTreeNode;
import com.snake.system.model.form.OrgCreateForm;
import com.snake.system.model.form.OrgModifyForm;
import com.snake.system.service.IOrgService;
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
 * @description: 组织机构 API
 * @version: 1.0
 */
@Tag(name = "组织机构API")
@Slf4j
@RestController
@RequestMapping(value = "/org")
@RequiredArgsConstructor
public class OrgController extends BaseController {

    private final IOrgService orgService;

    @Operation(summary = "新增组织机构")
    @PostMapping(value = "/create")
    public ResponseEntity<Result<OrgDTO>> create(@RequestBody @Validated OrgCreateForm form){
        return success(orgService.create(form));
    }

    @Operation(summary = "修改组织机构")
    @PostMapping(value = "/modify")
    public ResponseEntity<Result<OrgDTO>> modify(@RequestBody @Validated OrgModifyForm form){
        return success(orgService.modify(form));
    }

    @Operation(summary = "查询组织机构")
    @GetMapping(value = "/detail")
    public ResponseEntity<Result<OrgDTO>> detail(@RequestParam("orgId") String orgId){
        return success(orgService.detail(orgId));
    }

    @Operation(summary = "删除组织机构")
    @GetMapping(value = "/deleteByOrgId")
    public ResponseEntity<Result<Boolean>> deleteByOrgId(@RequestParam("orgId") String orgId){
        orgService.deleteByOrgId(orgId);
        return success(Boolean.TRUE);
    }

    @Operation(summary = "查询组织机构树")
    @GetMapping(value = "/queryOrgTree")
    public ResponseEntity<Result<OrgTreeNode>> getOrgTree(@RequestParam(value = "orgId",required = false) String orgId){
        return success(orgService.queryOrgTree(orgId));
    }



}
