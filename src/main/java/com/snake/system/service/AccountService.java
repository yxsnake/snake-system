package com.snake.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.snake.system.model.dto.AccountDTO;
import com.snake.system.model.entity.Account;
import com.snake.system.model.entity.Emp;
import com.snake.system.model.entity.Tenant;
import com.snake.system.model.queries.DefaultTenantAccountEqualsQueries;

/**
 * <p>
 * 账号表 服务类
 * </p>
 *
 * @author snake
 * @since 2024-06-25
 */
public interface AccountService extends IService<Account> {

    /**
     * 基于账号、密码、渠道查询默认租户账号
     * @param queries
     * @return
     */
    AccountDTO findDefaultTenantAccount(DefaultTenantAccountEqualsQueries queries);

    /**
     *
     * @param emp
     */
    void createEmpAccount(Emp emp);

    /**
     * 查询账号信息
     * @param accountId
     * @return
     */
    AccountDTO findByAccountId(String accountId);

    /**
     * 初始化租户创建超管账号
     * @param tenant
     */
    void initTenantCreateAccount(Tenant tenant);
}
