package com.snake.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 审计日志
 * </p>
 *
 * @author snake
 * @since 2024-06-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_audit_log")
public class SysAuditLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 模块编码(参考：me.pisces.audit.log.model.enums.LogModuleEnum)
     */
    private String moduleCode;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 微服务名称
     */
    private String microservicesName;

    /**
     * 记录类型：0-操作记录；1-异常记录
     */
    private Integer recordType;

    /**
     * 操作类型：(1-登录，2-注销登录，3-新增，4-修改，5-删除，6-查询，7-报表导出，8-数据导入)
     */
    private Integer operationType;

    /**
     * 操作描述
     */
    private String description;

    /**
     * 请求方法
     */
    private String requestMethod;

    /**
     * 请求方法参数
     */
    private String requestParams;

    /**
     * 响应数据
     */
    private String responseData;

    /**
     * 耗时（毫秒）
     */
    private Long costTime;

    /**
     * 异常描述
     */
    private String exceptionDesc;

    /**
     * 请求头特征值
     */
    private String userAgent;

    /**
     * 操作时间
     */
    private LocalDateTime operationTime;

    /**
     * 操作人IP
     */
    private String operationIp;

    /**
     * 操作人ID
     */
    private String operationUserId;

    /**
     * 操作人姓名
     */
    private String operationUserName;

    /**
     * 租户唯一标识
     */
    private String tenantId;


}
