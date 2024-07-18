package com.snake.system.model.queries;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author: snake
 * @create-time: 2024-06-26
 * @description: 基于组织ID分页查询员工信息
 * @version: 1.0
 */
@Data
@Schema(name = "员工分页查询等值查询条件对象")
public class EmpPageEqualsQueries {

    @Schema(name = "组织ID")
    private String orgId;

    @Schema(name = "性别")
    private String gender;

}
