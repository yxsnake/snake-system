package com.snake.system.service;

import com.snake.system.model.dto.MenuTreeDTO;
import com.snake.system.model.dto.ResourceDTO;
import com.snake.system.model.entity.Resource;
import com.snake.system.model.form.MenuCreateForm;
import com.snake.system.model.form.MenuModifyForm;
import com.snake.system.model.form.ext.InitTenantMenuForm;

import java.util.List;

/**
 * @author: snake
 * @create-time: 2024-07-15
 * @description: 菜单
 * @version: 1.0
 */
public interface MenuService {

    ResourceDTO createMenuOrButton(MenuCreateForm form);

    ResourceDTO modifyMenuOrButton(MenuModifyForm form);

    void deleteMenuById(String menuId);

    MenuTreeDTO tree();

    List<Resource> initTenantBuildMenus(String tenantId, List<InitTenantMenuForm> menuFormList);
}
