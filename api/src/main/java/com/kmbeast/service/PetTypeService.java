package com.kmbeast.service;

import com.kmbeast.pojo.api.Result;
import com.kmbeast.pojo.dto.PetTypeQueryDto;
import com.kmbeast.pojo.entity.PetType;

import java.util.List;

/**
 * 宠物类别业务逻辑接口
 */
public interface PetTypeService {

    Result<String> save(PetType petType);

    Result<String> update(PetType petType);

    Result<String> deleteById(Integer id);

    Result<List<PetType>> query(PetTypeQueryDto petTypeQueryDto);

}
