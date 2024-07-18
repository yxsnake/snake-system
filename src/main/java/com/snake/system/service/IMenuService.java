package com.snake.system.service;

import com.snake.system.model.dto.MenuTreeDTO;
import com.snake.system.model.dto.ResourceDTO;
import com.snake.system.model.form.MenuCreateForm;
import com.snake.system.model.form.MenuModifyForm;

/**
 * @author: snake
 * @create-time: 2024-07-15
 * @description: 菜单
 * @version: 1.0
 */
public interface IMenuService {

    ResourceDTO createMenuOrButton(MenuCreateForm form);

    ResourceDTO modifyMenuOrButton(MenuModifyForm form);

    void deleteMenuById(String menuId);

    MenuTreeDTO tree();
}
