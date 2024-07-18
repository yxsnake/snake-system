package com.snake.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.snake.system.model.dto.EmpDTO;
import com.snake.system.model.dto.EmpDetailDTO;
import com.snake.system.model.entity.Emp;
import com.snake.system.model.form.EmpCreateForm;
import com.snake.system.model.form.EmpEnableOrDisableForm;
import com.snake.system.model.form.EmpModifyForm;
import com.snake.system.model.queries.EmpPageEqualsQueries;
import com.snake.system.model.queries.EmpPageFuzzyQueries;
import io.github.yxsnake.pisces.web.core.base.QueryFilter;

/**
 * <p>
 * 员工表 服务类
 * </p>
 *
 * @author snake
 * @since 2024-06-25
 */
public interface EmpService extends IService<Emp> {
    /**
     * 根据账号 ID 查询员工信息
     * @param accountId
     * @return
     */
    EmpDTO get(String accountId);

    /**
     * 创建员工
     * @param form
     * @return
     */
    EmpDTO create(EmpCreateForm form);

    /**
     * 编辑员工
     * @param form
     * @return
     */
    EmpDTO modify(EmpModifyForm form);

    /**
     * 员工账号启用或禁用
     * @param form
     * @return
     */
    EmpDTO enableOrDisable(EmpEnableOrDisableForm form);

    /**
     * 分页查询员工列表
     * @param queryFilter
     * @return
     */
    IPage<EmpDTO> pageList(QueryFilter<EmpPageEqualsQueries, EmpPageFuzzyQueries> queryFilter);

    /**
     * 查询员工详情
     * @param empId
     * @return
     */
    EmpDetailDTO detail(String empId);
}
