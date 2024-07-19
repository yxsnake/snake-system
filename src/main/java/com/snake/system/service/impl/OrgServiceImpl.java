package com.snake.system.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snake.system.mapper.EmpMapper;
import com.snake.system.mapper.EmpOrgMapper;
import com.snake.system.mapper.OrgMapper;
import com.snake.system.model.dto.OrgDTO;
import com.snake.system.model.dto.OrgTreeNode;
import com.snake.system.model.entity.Emp;
import com.snake.system.model.entity.EmpOrg;
import com.snake.system.model.entity.Org;
import com.snake.system.model.entity.Tenant;
import com.snake.system.model.enums.OrgTypeEnum;
import com.snake.system.model.form.OrgCreateForm;
import com.snake.system.model.form.OrgModifyForm;
import com.snake.system.service.OrgService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.github.yxsnake.pisces.web.core.constant.StringPool;
import io.github.yxsnake.pisces.web.core.enums.DeletedEnum;
import io.github.yxsnake.pisces.web.core.utils.BizAssert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 组织表 服务实现类
 * </p>
 *
 * @author snake
 * @since 2024-06-25
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrgServiceImpl extends ServiceImpl<OrgMapper, Org> implements OrgService {

    private final EmpMapper empMapper;

    private final EmpOrgMapper empOrgMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrgDTO create(OrgCreateForm form) {
        Org org = this.lambdaQuery().eq(Org::getOrgName, form.getOrgName())
                .list()
                .stream()
                .findFirst()
                .orElse(null);
        BizAssert.isTrue("组织名称已存在", Objects.nonNull(org));
        org = form.convert(Org.class);
        // 查询上级组织信息
        Org parentOrg = this.lambdaQuery().eq(Org::getOrgId, form.getParentId())
                .list()
                .stream()
                .findFirst()
                .orElse(null);
        BizAssert.isTrue("上级组织不存在",Objects.isNull(parentOrg));
        String orgId = IdWorker.getIdStr();
        org.setOrgId(orgId);
        org.setOrgPathIds(parentOrg.getOrgPathIds()+ StringPool.PIPE+orgId);
        org.setOrgPathNames(parentOrg.getOrgPathNames()+StringPool.PIPE+org.getOrgName());
        if(StrUtil.isNotBlank(form.getPersonInCharge())){
            // 查询负债人信息
            Emp emp = empMapper.selectById(form.getPersonInCharge());
            BizAssert.isTrue("负责人信息不存在",Objects.isNull(emp));
            org.setPersonInCharge(emp.getEmpId());
            org.setPersonInChargeName(emp.getRealName());
            org.setContactPhone(emp.getPhone());
        }
        this.getBaseMapper().insert(org);
        return org.convert(OrgDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrgDTO modify(OrgModifyForm form) {
        Org org = this.lambdaQuery().eq(Org::getOrgId, form.getOrgId())
                .eq(Org::getDeleted, DeletedEnum.NORMAL.getValue())
                .list()
                .stream()
                .findFirst()
                .orElse(null);
        BizAssert.isTrue("组织不存在",Objects.isNull(org));
        if(StrUtil.isNotBlank(form.getOrgName())){
            org.setOrgName(form.getOrgName());
        }
        if(StrUtil.isNotBlank(form.getPersonInChargeName())){
            org.setPersonInChargeName(form.getPersonInChargeName());
        }
        if(StrUtil.isNotBlank(form.getPersonInCharge())){
            org.setPersonInCharge(form.getPersonInCharge());
        }
        if(StrUtil.isNotBlank(form.getContactPhone())){
            org.setContactPhone(form.getContactPhone());
        }
        this.getBaseMapper().updateById(org);
        return org.convert(OrgDTO.class);
    }

    @Override
    public OrgDTO detail(String orgId) {
        Org org = this.getBaseMapper().selectById(orgId);
        BizAssert.isTrue("组织机构信息不存在",Objects.isNull(org));
        return org.convert(OrgDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByOrgId(String orgId) {
        Org org = this.getBaseMapper().selectById(orgId);
        BizAssert.isTrue("组织机构信息不存在",Objects.isNull(org));
        // 校验是否存在子级机构
        long childOrgCount = this.lambdaQuery().eq(Org::getParentId, orgId).list().stream().count();
        BizAssert.isTrue("当前机构下存在子级机构不允许被删除",childOrgCount>0);
        // 校验机构下是否存在员工
        Long orgRefEmpCount = empOrgMapper.selectCount(Wrappers.lambdaQuery(EmpOrg.class).eq(EmpOrg::getOrgId, orgId));
        BizAssert.isTrue("当前机构下存在员工，请先解除当前机构下的员工，再进行删除",orgRefEmpCount>0);
        org.setDeleted(DeletedEnum.DEL.getValue());
        this.getBaseMapper().updateById(org);
    }

    @Override
    public OrgTreeNode queryOrgTree(String orgId) {
        Org root = null;
        if(StrUtil.isBlank(orgId)){
            root = this.lambdaQuery()
                    .eq(Org::getOrgType, OrgTypeEnum.COMPANY.getValue())
                    .eq(Org::getDeleted,DeletedEnum.NORMAL.getValue())
                    .eq(StrUtil.isNotBlank(orgId),Org::getOrgId,orgId)
                    .list()
                    .stream()
                    .findFirst()
                    .orElse(null);
        }else{
            root = this.lambdaQuery()
                    .eq(Org::getDeleted,DeletedEnum.NORMAL.getValue())
                    .eq(StrUtil.isNotBlank(orgId),Org::getOrgId,orgId)
                    .list()
                    .stream()
                    .findFirst()
                    .orElse(null);
        }
        if(Objects.isNull(root)){
            return null;
        }
        String queryId = null;
        if(StrUtil.isBlank(orgId)){
            queryId = root.getOrgId();
        }else{
            queryId = root.getOrgPathIds();
        }
        List<Org> list = this.lambdaQuery()
                .eq(Org::getDeleted,DeletedEnum.NORMAL.getValue())
                .likeRight(Org::getOrgPathIds, queryId).list();
        List<OrgTreeNode> nodes = list.stream()
                .map(item->item.convert(OrgTreeNode.class))
                .collect(Collectors.toList());
        List<OrgTreeNode> treeNodes = streamToTree(nodes, root.getParentId());
        OrgTreeNode tree = treeNodes.stream().findFirst().orElse(null);
        return tree;
    }

    @Override
    public Org initTenantBuildOrg(Tenant tenant) {
        Org org = new Org();
        org.setOrgId(IdWorker.getIdStr());
        org.setOrgName(tenant.getTenantName());
        org.setTenantId(tenant.getTenantId());
        org.setContactPhone(tenant.getPhone());
        org.setParentId(Org.ORG_ROOT);
        org.setOrgPathIds(com.baomidou.mybatisplus.core.toolkit.StringPool.PIPE+org.getOrgId());
        org.setOrgPathNames(org.getOrgName());
        org.setCreateTime(DateUtil.date());
        org.setDeleted(DeletedEnum.NORMAL.getValue());
        org.setOrgType(OrgTypeEnum.COMPANY.getValue());
        org.setOrgOrder(1);
        return org;
    }

    private List<OrgTreeNode> streamToTree(List<OrgTreeNode> treeList,String parentId){

        List<OrgTreeNode> list = treeList.stream()
                .filter(parent -> parent.getParentId().equals(parentId))
                .map(child -> {
                    child.setChildren(streamToTree(treeList,child.getOrgId()));
                    return child;
                }).collect(Collectors.toList());
        return list;
    }

}
