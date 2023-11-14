package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SetmealMapper {
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);

    List<Long> getSetmealIdsByDishIds(List<Long> dishIds);

    @AutoFill(value = OperationType.INSERT)
    void insert(Setmeal setmeal);

    Page pagequery(SetmealPageQueryDTO setmealPageQueryDTO);

    void deleteIds(List<Long> ids);

    @Select("SELECT * FROM setmeal WHERE id = #{id}")
    Setmeal getById(Long setmealId);

    void update(Setmeal setmeal);

    @Update("UPDATE setmeal SET status = #{status} WHERE id = #{id}")
    void startOrStop(Integer status, Long id);
}
