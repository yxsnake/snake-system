package com.snake.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.snake.system.mapper.ResourceMapper;
import com.snake.system.model.dto.ResourceDTO;
import com.snake.system.model.entity.Resource;
import com.snake.system.service.IResourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.github.yxsnake.pisces.web.core.enums.DeletedEnum;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author snake
 * @since 2024-06-25
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {

    @Override
    public ResourceDTO detail(String resourceId) {
        Resource resource = this.lambdaQuery().eq(Resource::getResourceId, resourceId)
                .eq(Resource::getDeleted, DeletedEnum.NORMAL.getValue())
                .list().stream().findFirst().orElse(null);
        if(Objects.isNull(resource)){
            return null;
        }
        return resource.convert(ResourceDTO.class);
    }

    @Override
    public List<Resource> queryCurrentUserMenuList(String userId) {
        return null;
    }
}
