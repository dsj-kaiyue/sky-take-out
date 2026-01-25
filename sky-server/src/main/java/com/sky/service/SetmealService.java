package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {
    /**
     * 新增套餐，同时保存套餐和菜品的关联关系
     * @param setmealDTO
     */
    void saveWithDish(SetmealDTO setmealDTO);

    /**
     * 套餐分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 根据id查询套餐信息和对应的菜品信息
     * @param id
     * @return
     */
    SetmealVO getByIdWithDish(Long id);

    /**
     * 更新套餐信息，同时更新对应的菜品信息
     * @param setmealDTO
     */
    void updateWithDish(SetmealDTO setmealDTO);

    /**
     * 套餐启用或禁用
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * 根据ids批量删除套餐
     * @param ids
     */
    void deleteByIds(List<Long> ids);
}
