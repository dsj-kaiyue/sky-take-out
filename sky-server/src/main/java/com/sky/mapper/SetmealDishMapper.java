package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    /**
     * 根据菜品id查询套餐id集合
     * @param dishIds
     * @return
     */
    List<Long> getSetmealIdsByDishId(List<Long> dishIds);

    /**
     * 批量插入套餐菜品关联数据
     * @param setmealDishes
     */
    void insertBatch(List<SetmealDish> setmealDishes);

    /**
     * 根据套餐id查询套餐菜品关联数据
     * @param id
     * @return
     */
    @Select("select * from setmeal_dish where setmeal_id = #{id}")
    List<SetmealDish> getBySetmealId(Long id);

    /**
     * 根据套餐id删除套餐菜品关联数据
     * @param id
     */
    @Delete("delete from setmeal_dish where setmeal_id = #{id}")
    void deleteBySetmealId(Long id);

    /**
     * 根据套餐ids批量删除套餐菜品关联数据
     * @param ids
     */
    void deleteBySetmealIds(List<Long> ids);
}
