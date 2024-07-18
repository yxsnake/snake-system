package com.snake.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 员工组织关联表
 * </p>
 *
 * @author snake
 * @since 2024-06-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("emp_org")
public class EmpOrg implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     *  员工ID
     */
    private String empId;

    /**
     * 组织ID
     */
    private String orgId;

    /**
     * 是否主部门(1:是,0否)
     */
    private Integer flagMainDepartment;

    /**
     * 租户唯一标识
     */
    private String tenantId;


}
