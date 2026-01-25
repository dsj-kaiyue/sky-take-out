package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Override
    public void saveWithDish(SetmealDTO setmealDTO) {

        Setmeal setmeal = new Setmeal();
        //属性拷贝
        BeanUtils.copyProperties(setmealDTO,setmeal);

        //向套餐表插入一条数据
        setmealMapper.insert(setmeal);

        //获取套餐id
        Long setmealId = setmeal.getId();

        //向套餐菜品关联表插入多条数据
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if (setmealDishes !=null && setmealDishes.size()!=0){
            setmealDishes.forEach(setmealDish -> {
                setmealDish.setSetmealId(setmealId);
            });
            setmealDishMapper.insertBatch(setmealDishes);
        }
    }

    /**
     * 套餐分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(),setmealPageQueryDTO.getPageSize());
        Page<Setmeal> page=setmealMapper.pageQuery(setmealPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());

    }

    /**
     * 根据id查询套餐信息和对应的菜品信息
     * @param id
     * @return
     */
    @Override
    public SetmealVO  getByIdWithDish(Long id) {
        //查询套餐基本信息
        Setmeal setmeal = setmealMapper.getById(id);
        //获取分类名字
        String categoryName = setmealMapper.getCategoryNameById(setmeal.getCategoryId());
        //查询套餐对应的菜品信息
        List<SetmealDish> setmealDishes = setmealDishMapper.getBySetmealId(id);

        SetmealVO setmealVO=new SetmealVO();
        BeanUtils.copyProperties(setmeal,setmealVO);
        setmealVO.setSetmealDishes(setmealDishes);
        setmealVO.setCategoryName(categoryName);

        return setmealVO;
    }

    /**
     * 更新套餐信息，同时更新对应的菜品信息
     * @param setmealDTO
     */
    @Override
    public void updateWithDish(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        //更新套餐表基本信息
        setmealMapper.updateById(setmeal);
        //删除套餐对应的菜品信息
        setmealDishMapper.deleteBySetmealId(setmealDTO.getId());
        //添加套餐对应的菜品信息
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if (setmealDishes !=null && setmealDishes.size()!=0){
            setmealDishes.forEach(setmealDish -> {
                setmealDish.setSetmealId(setmealDTO.getId());
            });
            setmealDishMapper.insertBatch(setmealDishes);
        }

    }

    /**
     * 套餐启用或禁用
     * @param status
     * @param id
     */
    @Override
    public void startOrStop(Integer status, Long id) {
        Setmeal setmeal = Setmeal.builder().status(status).id(id).build();
        setmealMapper.updateById(setmeal);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        //判断当前菜品是否可以删除（停售状态）
        for (Long id : ids) {
            Setmeal setmeal = setmealMapper.getById(id);
            if (setmeal.getStatus()==1){
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        }
        setmealMapper.deleteByIds(ids);
        setmealDishMapper.deleteBySetmealIds(ids);
    }
}
