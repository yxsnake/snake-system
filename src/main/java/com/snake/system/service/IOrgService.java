package com.snake.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.snake.system.model.dto.OrgDTO;
import com.snake.system.model.dto.OrgTreeNode;
import com.snake.system.model.entity.Org;
import com.snake.system.model.form.OrgCreateForm;
import com.snake.system.model.form.OrgModifyForm;

/**
 * <p>
 * 组织表 服务类
 * </p>
 *
 * @author snake
 * @since 2024-06-25
 */
public interface IOrgService extends IService<Org> {

    /**
     * 创建组织机构
     * @param form
     * @return
     */
    OrgDTO create(OrgCreateForm form);

    /**
     * 修改组织机构
     * @param form
     * @return
     */
    OrgDTO modify(OrgModifyForm form);

    /**
     * 查询组织详情
     * @param orgId
     * @return
     */
    OrgDTO detail(String orgId);

    /**
     * 删除组织机构
     * @param orgId
     */
    void deleteByOrgId(String orgId);

    /**
     * 查询组织机构树
     * @param orgId
     * @return
     */
    OrgTreeNode queryOrgTree(String orgId);
}
