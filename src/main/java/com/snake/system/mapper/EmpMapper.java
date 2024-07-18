package com.snake.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snake.system.model.entity.Emp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 员工表 Mapper 接口
 * </p>
 *
 * @author snake
 * @since 2024-06-25
 */
@Mapper
public interface EmpMapper extends BaseMapper<Emp> {

    /**
     * 查询总记录数
     * @param params
     * @return
     */
    Long countByCondition(@Param("params") Map<String, Object> params);

    /**
     * 查询数据列表
     * @param params
     * @return
     */
    List<Emp> findByConditionWithPage(@Param("params") Map<String, Object> params);
}
