package com.kmbeast.mapper;

import com.kmbeast.pojo.dto.PetQueryDto;
import com.kmbeast.pojo.entity.Pet;
import com.kmbeast.pojo.vo.PetListItemVO;
import com.kmbeast.pojo.vo.PetVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 宠物持久化接口
 */
@Mapper
public interface PetMapper {

    /**
     * 宠物信息新增
     *
     * @param pet 宠物信息
     */
    void save(Pet pet);

    /**
     * 宠物信息修改
     *
     * @param pet 宠物信息
     */
    void update(Pet pet);

    /**
     * 删除宠物信息
     *
     * @param id 宠物的主键ID
     */
    void deleteById(@Param(value = "id") Integer id);

    /**
     * 通过ID查询宠物信息
     *
     * @param id 宠物ID
     * @return PetVO
     */
    PetVO getById(@Param(value = "id") Integer id);

    /**
     * 查询宠物列表
     *
     * @param petQueryDto 查询条件类
     * @return List<PetListItemVO>
     */
    List<PetListItemVO> queryListItem(PetQueryDto petQueryDto);

    /**
     * 查询符合条件的总条数
     *
     * @param petQueryDto 查询条件类
     * @return Integer
     */
    Integer queryCount(PetQueryDto petQueryDto);

    List<Integer> queryAllIds();


    List<PetListItemVO> queryListItemByIds(@Param(value = "ids") List<Integer> recommendItems);

}
