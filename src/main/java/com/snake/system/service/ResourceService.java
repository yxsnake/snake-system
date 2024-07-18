package com.snake.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.snake.system.model.dto.ResourceDTO;
import com.snake.system.model.entity.Resource;

import java.util.List;

/**
 * <p>
 * 资源表 服务类
 * </p>
 *
 * @author snake
 * @since 2024-06-25
 */
public interface ResourceService extends IService<Resource> {
    ResourceDTO detail(String resourceId);

    List<Resource> queryCurrentUserMenuList(String userId);
}
