package com.snake.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.snake.system.model.dto.ResourceDTO;
import com.snake.system.model.form.ApiCreateForm;
import com.snake.system.model.form.ApiModifyForm;
import com.snake.system.model.queries.ApiResourcePageEqualsQueries;
import io.github.yxsnake.pisces.web.core.base.BaseFuzzyQueries;
import io.github.yxsnake.pisces.web.core.base.QueryFilter;

/**
 * @author: snake
 * @create-time: 2024-07-15
 * @description: api 资源
 * @version: 1.0
 */
public interface ApiResourceService {
    ResourceDTO createApi(ApiCreateForm form);

    ResourceDTO modifyApi(ApiModifyForm form);

    IPage<ResourceDTO> pageList(QueryFilter<ApiResourcePageEqualsQueries, BaseFuzzyQueries> queryFilter);
}
