package com.kmbeast.mapper;

import com.kmbeast.pojo.dto.PetTypeQueryDto;
import com.kmbeast.pojo.entity.PetType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 宠物类别持久化接口
 */
public interface PetTypeMapper {

    void save(PetType petType);

    void update(PetType petType);

    void deleteById(@Param(value = "id") Integer id);

    PetType queryByName(@Param(value = "name") String name);

    List<PetType> query(PetTypeQueryDto petTypeQueryDto);

    Integer queryCount(PetTypeQueryDto petTypeQueryDto);

}