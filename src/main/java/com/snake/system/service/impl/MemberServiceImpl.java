package com.snake.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snake.system.mapper.MemberMapper;
import com.snake.system.model.entity.Member;
import com.snake.system.service.MemberService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author snake
 * @since 2024-06-25
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

}
