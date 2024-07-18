package com.snake.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snake.system.model.entity.SysAuditLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 审计日志 Mapper 接口
 * </p>
 *
 * @author snake
 * @since 2024-06-25
 */
@Mapper
public interface SysAuditLogMapper extends BaseMapper<SysAuditLog> {

}
