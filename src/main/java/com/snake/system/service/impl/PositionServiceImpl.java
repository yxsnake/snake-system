package com.snake.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snake.system.mapper.PositionMapper;
import com.snake.system.model.entity.Position;
import com.snake.system.service.PositionService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 职位表 服务实现类
 * </p>
 *
 * @author snake
 * @since 2024-06-25
 */
@Service
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position> implements PositionService {

}
