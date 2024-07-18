package com.snake.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snake.system.model.entity.Tenant;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TenantMapper extends BaseMapper<Tenant> {
}
