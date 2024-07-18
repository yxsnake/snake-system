package com.snake.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snake.system.mapper.SysAuditLogMapper;
import com.snake.system.model.entity.SysAuditLog;
import com.snake.system.service.ISysAuditLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 审计日志 服务实现类
 * </p>
 *
 * @author snake
 * @since 2024-06-25
 */
@Service
public class SysAuditLogServiceImpl extends ServiceImpl<SysAuditLogMapper, SysAuditLog> implements ISysAuditLogService {

}
