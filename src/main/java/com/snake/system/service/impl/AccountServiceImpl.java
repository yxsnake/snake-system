package com.snake.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snake.system.mapper.AccountMapper;
import com.snake.system.model.dto.AccountDTO;
import com.snake.system.model.entity.Account;
import com.snake.system.model.entity.Emp;
import com.snake.system.model.entity.Tenant;
import com.snake.system.model.enums.*;
import com.snake.system.model.queries.DefaultTenantAccountEqualsQueries;
import com.snake.system.service.AccountService;
import com.snake.system.util.PasswordUtil;
import io.github.yxsnake.pisces.mybatis.plus.context.TenantIgnoreContext;
import lombok.extern.slf4j.Slf4j;
import io.github.yxsnake.pisces.web.core.context.UserContext;
import io.github.yxsnake.pisces.web.core.utils.BizAssert;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 账号表 服务实现类
 * </p>
 *
 * @author snake
 * @since 2024-06-25
 */
@Slf4j
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    @Value("${aes.key:RW0S6YkTQ4WFzXpbbhkBDg==}")
    private String aesKey;

    @Override
    public AccountDTO findDefaultTenantAccount(DefaultTenantAccountEqualsQueries queries) {
        TenantIgnoreContext.set();
        List<Account> list = this.lambdaQuery()
                .eq(Account::getAccount, queries.getAccount())
                .eq(Account::getChannel, queries.getChannel())
                .eq(Account::getDefaultTenantFlag, AccountDefaultTenantEnum.YES.getValue())
                .eq(Account::getLoginWay,AccountLoginWayEnum.PWD.getValue())
                .list();
        BizAssert.isNull("账号或密码不存在", CollUtil.isEmpty(list));
        Account account = list.stream().findFirst().orElse(null);
        String dbPwd = account.getPassword();
        Boolean checkPwd = PasswordUtil.checkPwd(aesKey, dbPwd, queries.getPassword());
        BizAssert.isFalse("账号密码错误",checkPwd);
        return account.convert(AccountDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createEmpAccount(Emp emp) {
        // 查询当前账号是否已存在默认账号
        TenantIgnoreContext.set();
        List<Account> list = this.lambdaQuery()
                .eq(Account::getAccount, emp.getPhone())
                .eq(Account::getChannel, AccountChannelEnum.EMP.getValue())
                .eq(Account::getLoginWay, AccountLoginWayEnum.PWD.getValue())
                .eq(Account::getDefaultTenantFlag, AccountDefaultTenantEnum.YES.getValue())
                .list();
        // 如果存在默认账号 则本次创建账号为非默认租户账号，反则为默认租户账号
        AccountDefaultTenantEnum  defaultTenantEnum = CollUtil.isEmpty(list)?AccountDefaultTenantEnum.YES:AccountDefaultTenantEnum.NO;
        Account account = new Account();
        String accountId = IdWorker.getIdStr();
        account.setUserId(emp.getEmpId());
        account.setAccountId(accountId);
        account.setAccount(emp.getPhone());
        account.setChannel(AccountChannelEnum.EMP.getValue());
        account.setDisabled(AccountStatusEnum.NORMAL.getValue());
        account.setDefaultTenantFlag(defaultTenantEnum.getValue());
        account.setLoginWay(AccountLoginWayEnum.PWD.getValue());
        account.setPassword(PasswordUtil.getEncryptPwd(aesKey,account.getAccount()));
        account.setSupperAdmin(AccountSupperAdminEnum.ORDINARY.getValue());
        String tenantId = UserContext.getTenantId();
        account.setTenantId(tenantId);
        this.getBaseMapper().insert(account);
    }

    @Override
    public AccountDTO findByAccountId(String accountId) {
        TenantIgnoreContext.set();
        Account account = this.getBaseMapper().selectById(accountId);
        if(Objects.isNull(account)){
            return null;
        }
        return account.convert(AccountDTO.class);
    }

    @Override
    public void initTenantCreateAccount(Tenant tenant) {
        Account account = new Account();
        String accountId = IdWorker.getIdStr();
        account.setUserId(tenant.getTenantId());
        account.setAccountId(accountId);
        account.setAccount(tenant.getSupperAccount());
        account.setChannel(AccountChannelEnum.EMP.getValue());
        account.setDisabled(AccountStatusEnum.NORMAL.getValue());
        account.setDefaultTenantFlag(AccountDefaultTenantEnum.YES.getValue());
        account.setLoginWay(AccountLoginWayEnum.PWD.getValue());
        account.setPassword(PasswordUtil.getEncryptPwd(aesKey,tenant.getSupperPassword()));
        account.setSupperAdmin(AccountSupperAdminEnum.SUPPER.getValue());
        String tenantId = UserContext.getTenantId();
        account.setTenantId(tenantId);
        TenantIgnoreContext.set();
        this.getBaseMapper().insert(account);
    }
}
