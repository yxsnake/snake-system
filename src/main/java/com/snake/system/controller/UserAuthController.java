package com.snake.system.controller;

import com.snake.system.model.dto.ResourceDTO;
import com.snake.system.model.queries.SsoPermissionQueries;
import com.snake.system.model.queries.SsoRoleQueries;
import com.snake.system.model.queries.UserResourceEqualsQueries;
import com.snake.system.service.UserAuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import io.github.yxsnake.pisces.web.core.base.Result;
import io.github.yxsnake.pisces.web.core.framework.controller.BaseController;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 网关鉴权 用户权限查询
 */
@Tag(name = "网关鉴权-用户权限、角色查询")
@RestController
@RequestMapping(value = "/user-auth")
@RequiredArgsConstructor
public class UserAuthController extends BaseController {

    private final UserAuthService userAuthService;

    @PostMapping(value = "/permissions")
    public ResponseEntity<Result<List<String>>> getPermissions(@RequestBody @Validated SsoPermissionQueries queries){
       return success(userAuthService.getPermissions(queries));
    }

    @PostMapping(value = "/roles")
    public ResponseEntity<Result<List<String>>> getRoles(@RequestBody @Validated SsoRoleQueries queries){
        return success(userAuthService.getRoles(queries));
    }

    @PostMapping(value = "/getResources")
    public ResponseEntity<Result<List<ResourceDTO>>> getResources(@RequestBody UserResourceEqualsQueries queries){
        return success(userAuthService.getResources(queries));
    }
}
