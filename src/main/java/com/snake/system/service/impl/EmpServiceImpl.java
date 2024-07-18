package com.snake.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snake.system.mapper.EmpMapper;
import com.snake.system.model.dto.EmpDTO;
import com.snake.system.model.dto.EmpDetailDTO;
import com.snake.system.model.dto.OrgDTO;
import com.snake.system.model.entity.Account;
import com.snake.system.model.entity.Emp;
import com.snake.system.model.entity.EmpOrg;
import com.snake.system.model.entity.Org;
import com.snake.system.model.enums.AccountChannelEnum;
import com.snake.system.model.enums.AccountSupperAdminEnum;
import com.snake.system.model.enums.OrgOperatorTypeEnum;
import com.snake.system.model.form.EmpCreateForm;
import com.snake.system.model.form.EmpEnableOrDisableForm;
import com.snake.system.model.form.EmpModifyForm;
import com.snake.system.model.form.ext.EmpOrgExtForm;
import com.snake.system.model.queries.EmpPageEqualsQueries;
import com.snake.system.model.queries.EmpPageFuzzyQueries;
import com.snake.system.service.*;
import io.github.yxsnake.pisces.mybatis.plus.context.TenantIgnoreContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.github.yxsnake.pisces.web.core.base.QueryFilter;
import io.github.yxsnake.pisces.web.core.enums.DisabledEnum;
import io.github.yxsnake.pisces.web.core.utils.BizAssert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 员工表 服务实现类
 * </p>
 *
 * @author snake
 * @since 2024-06-25
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmpServiceImpl extends ServiceImpl<EmpMapper, Emp> implements EmpService {

    private final AccountService accountService;

    private final EmpOrgService empOrgService;

    private final UserRoleService userRoleService;

    private final OrgService orgService;

    @Override
    public EmpDTO get(String accountId) {
        TenantIgnoreContext.set();
        Account account = accountService.lambdaQuery()
                .eq(Account::getAccountId, accountId)
                .list().stream().findFirst().orElse(null);
        BizAssert.isNull("账号不存在",account);
        String empId = account.getUserId();

        TenantIgnoreContext.set();
        Emp emp = this.lambdaQuery()
                .eq(Emp::getTenantId,account.getTenantId())
                .eq(Emp::getEmpId,empId)
                .list()
                .stream()
                .findFirst()
                .orElse(null);
        BizAssert.isNull("员工信息不存在",emp);
        return emp.convert(EmpDTO.class);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public EmpDTO create(EmpCreateForm form) {
        Emp emp = form.convert(Emp.class);
        String empId = IdWorker.getIdStr();
        emp.setEmpId(empId);
        emp.setSupperAdmin(AccountSupperAdminEnum.ORDINARY.getValue());
        // 保存员工信息
        this.getBaseMapper().insert(emp);
        // 创建账号
        accountService.createEmpAccount(emp);
        // 绑定组织机构
        empOrgService.bingEmpOrg(empId,form.getOrgies(), OrgOperatorTypeEnum.SAVE);
        // 赋予基础权限
        userRoleService.bingEmpRole(empId,form.getRoleIds());
        return emp.convert(EmpDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EmpDTO modify(EmpModifyForm form) {
        Emp emp = this.getBaseMapper().selectById(form.getEmpId());
        BizAssert.isTrue("员工信息不存在", Objects.isNull(emp));
        if(StrUtil.isNotBlank(form.getAvatar())){
            emp.setAvatar(form.getAvatar());
        }
        if(StrUtil.isNotBlank(form.getRealName())){
            emp.setRealName(form.getRealName());
        }
        if(Objects.nonNull(form.getGender())){
            emp.setGender(form.getGender());
        }
        if(StrUtil.isNotBlank(form.getEmail())){
            emp.setEmail(form.getEmail());
        }
        this.getBaseMapper().updateById(emp);
        if(CollUtil.isNotEmpty(form.getOrgies())){
            // 校验传入的组织 ID 是否合法
            List<String> orgIds = form.getOrgies().stream().map(EmpOrgExtForm::getOrgId).collect(Collectors.toList());
            long count = this.orgService.lambdaQuery().eq(Org::getDeleted, DisabledEnum.NORMAL.getValue())
                    .in(Org::getOrgId, orgIds).list().stream().count();
            BizAssert.isTrue("部分组织已停用或不存在",count-orgIds.size()!=0);
            empOrgService.bingEmpOrg(emp.getEmpId(),form.getOrgies(), OrgOperatorTypeEnum.MODIFY);
        }

        if(CollUtil.isNotEmpty(form.getRoleIds())){
            userRoleService.bingEmpRole(emp.getEmpId(),form.getRoleIds());
        }
        return emp.convert(EmpDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EmpDTO enableOrDisable(EmpEnableOrDisableForm form) {
        Emp emp = this.getBaseMapper().selectById(form.getEmpId());
        BizAssert.isTrue("员工信息不存在", Objects.isNull(emp));
        // 查询 账号信息 并对启用禁用状态进行修改
        Account account = this.accountService.lambdaQuery()
                .eq(Account::getChannel, AccountChannelEnum.EMP.getValue())
                .eq(Account::getUserId, emp.getEmpId())
                .eq(Account::getTenantId, emp.getTenantId()).list().stream().findFirst().orElse(null);
        BizAssert.isTrue("账号不存在",Objects.isNull(account));
        DisabledEnum disabledEnum = DisabledEnum.getInstance(form.getStatus());
        BizAssert.isTrue("状态值错误",Objects.isNull(disabledEnum));
        account.setDisabled(disabledEnum.getValue());
        this.accountService.updateById(account);
        return emp.convert(EmpDTO.class);
    }

    @Override
    public IPage<EmpDTO> pageList(QueryFilter<EmpPageEqualsQueries, EmpPageFuzzyQueries> queryFilter) {
        BizAssert.isTrue("请求参数不能为空",Objects.isNull(queryFilter));
        BizAssert.isTrue("equalsQueries不能为空",Objects.isNull(queryFilter.getEqualsQueries()));
        BizAssert.isTrue("fuzzyQueries不能为空",Objects.isNull(queryFilter.getFuzzyQueries()));
        Map<String,Object> params = new HashMap<>();
        Long offset = (queryFilter.getPageNum() - 1) * queryFilter.getPageSize();
        params.put("offset",offset);
        params.put("pageSize",queryFilter.getPageSize());
        Map<String, Object> equalsQueriesMap = BeanUtil.beanToMap(queryFilter.getEqualsQueries());
        if(CollUtil.isNotEmpty(equalsQueriesMap)){
            params.putAll(equalsQueriesMap);
        }
        Map<String, Object> fuzzyQueriesMap = BeanUtil.beanToMap(queryFilter.getFuzzyQueries());
        if(CollUtil.isNotEmpty(fuzzyQueriesMap)){
            params.putAll(fuzzyQueriesMap);
        }
        Long total = this.getBaseMapper().countByCondition(params);
        List<Emp> list = this.getBaseMapper().findByConditionWithPage(params);
        IPage<EmpDTO> result = new Page<>();
        result.setTotal(total);
        result.setCurrent(queryFilter.getPageNum());
        result.setSize(queryFilter.getPageSize());
        result.setRecords(list.stream().map(item->item.convert(EmpDTO.class)).collect(Collectors.toList()));
        return result;
    }

    @Override
    public EmpDetailDTO detail(String empId) {
        Emp emp = this.lambdaQuery().eq(Emp::getEmpId, empId).list().stream().findFirst().orElse(null);
        BizAssert.isTrue("员工信息不存在",Objects.isNull(emp));
        EmpDetailDTO empDetailDTO = emp.convert(EmpDetailDTO.class);
        //查询员工的组织信息
        Set<String> orgIds = this.empOrgService.lambdaQuery()
                .eq(EmpOrg::getEmpId, empId)
                .list()
                .stream()
                .map(EmpOrg::getOrgId)
                .collect(Collectors.toSet());
        if(CollUtil.isNotEmpty(orgIds)){
            List<OrgDTO> orgList = this.orgService
                    .lambdaQuery()
                    .eq(Org::getOrgId, orgIds)
                    .list()
                    .stream()
                    .map(item -> item.convert(OrgDTO.class))
                    .collect(Collectors.toList());
            empDetailDTO.setOrgies(orgList);
        }
        return empDetailDTO;
    }
}
