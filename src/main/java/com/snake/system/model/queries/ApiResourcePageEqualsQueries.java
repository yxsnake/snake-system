package com.snake.system.model.queries;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author: snake
 * @create-time: 2024-07-16
 * @description: api 资源分页查询条件
 * @version: 1.0
 */
@Data
@Schema(name = "资源分页查询条件对象")
public class ApiResourcePageEqualsQueries {

    private String path;

    private String perm;
}
