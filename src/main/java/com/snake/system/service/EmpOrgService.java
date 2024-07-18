package com.snake.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.snake.system.model.entity.EmpOrg;
import com.snake.system.model.enums.OrgOperatorTypeEnum;
import com.snake.system.model.form.ext.EmpOrgExtForm;

import java.util.List;

/**
 * <p>
 * 员工组织关联表 服务类
 * </p>
 *
 * @author snake
 * @since 2024-06-25
 */
public interface EmpOrgService extends IService<EmpOrg> {

    /**
     * 员工绑定组织机构
     * @param empId
     * @param forms
     * @param orgOperatorTypeEnum
     */
    void bingEmpOrg(String empId,List<EmpOrgExtForm> forms, OrgOperatorTypeEnum orgOperatorTypeEnum);
}
