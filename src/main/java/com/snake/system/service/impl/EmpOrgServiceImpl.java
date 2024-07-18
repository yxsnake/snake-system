package com.snake.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.snake.system.mapper.EmpOrgMapper;
import com.snake.system.model.entity.EmpOrg;
import com.snake.system.model.entity.Org;
import com.snake.system.model.enums.OrgFlagMainDepartmentEnum;
import com.snake.system.model.enums.OrgOperatorTypeEnum;
import com.snake.system.model.form.ext.EmpOrgExtForm;
import com.snake.system.service.IEmpOrgService;
import com.snake.system.service.IOrgService;
import lombok.RequiredArgsConstructor;
import io.github.yxsnake.pisces.web.core.enums.DeletedEnum;
import io.github.yxsnake.pisces.web.core.utils.BizAssert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 员工组织关联表 服务实现类
 * </p>
 *
 * @author snake
 * @since 2024-06-25
 */
@Service
@RequiredArgsConstructor
public class EmpOrgServiceImpl extends ServiceImpl<EmpOrgMapper, EmpOrg> implements IEmpOrgService {

    private final IOrgService orgService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bingEmpOrg(String empId,List<EmpOrgExtForm> forms, OrgOperatorTypeEnum orgOperatorTypeEnum) {
        if(OrgOperatorTypeEnum.MODIFY.equals(orgOperatorTypeEnum)){
            List<EmpOrg> list = this.lambdaQuery().eq(EmpOrg::getEmpId, empId).list();
            List<String> ids = list.stream().map(EmpOrg::getId).collect(Collectors.toList());
            this.getBaseMapper().deleteBatchIds(ids);
        }
        long count = forms.stream().filter(item -> item.getFlagMainDepartment().equals(OrgFlagMainDepartmentEnum.YES.getValue())).count();
        BizAssert.isTrue("主部门必须设置",count == 0);
        BizAssert.isTrue("主部门只能设置一个",count == 1);
        List<String> orgIds = forms.stream().map(EmpOrgExtForm::getOrgId).collect(Collectors.toList());
        Long orgCount = orgService.lambdaQuery().eq(Org::getDeleted, DeletedEnum.NORMAL.getValue()).in(Org::getOrgId, orgIds).count();
        BizAssert.isTrue("部分组织已不存在",forms.size()-orgCount!=0);
        List<EmpOrg> empOrgies = Lists.newArrayList();
        for (EmpOrgExtForm form : forms) {
            EmpOrg empOrg = new EmpOrg();
            empOrg.setId(IdWorker.getIdStr());
            empOrg.setEmpId(empId);
            empOrg.setFlagMainDepartment(form.getFlagMainDepartment());
            empOrgies.add(empOrg);
        }
        this.saveBatch(empOrgies);

    }
}
