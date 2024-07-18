package com.snake.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snake.system.mapper.RoleResourceMapper;
import com.snake.system.model.entity.RoleResource;
import com.snake.system.service.IRoleResourceService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色资源关联表 服务实现类
 * </p>
 *
 * @author snake
 * @since 2024-06-25
 */
@Service
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceMapper, RoleResource> implements IRoleResourceService {

}
