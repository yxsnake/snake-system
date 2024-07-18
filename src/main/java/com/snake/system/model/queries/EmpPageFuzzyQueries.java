package com.snake.system.model.queries;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author: snake
 * @create-time: 2024-06-26
 * @description: 分页查询员工模糊查询条件对象
 * @version: 1.0
 */
@Data
@Schema(name = "员工分页查询模糊查询条件对象")
public class EmpPageFuzzyQueries {

    @Schema(name = "员工姓名")
    private String realName;

    @Schema(name = "员工电话")
    private String phone;
}
