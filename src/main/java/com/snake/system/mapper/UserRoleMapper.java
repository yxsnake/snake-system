package com.snake.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snake.system.model.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户角色关联表 Mapper 接口
 * </p>
 *
 * @author snake
 * @since 2024-06-25
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

}
