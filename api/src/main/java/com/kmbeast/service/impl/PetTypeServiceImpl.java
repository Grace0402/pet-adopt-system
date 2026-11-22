package com.kmbeast.service.impl;

import com.kmbeast.mapper.PetTypeMapper;
import com.kmbeast.pojo.api.ApiResult;
import com.kmbeast.pojo.api.Result;
import com.kmbeast.pojo.dto.PetTypeQueryDto;
import com.kmbeast.pojo.entity.PetType;
import com.kmbeast.service.PetTypeService;
import com.kmbeast.utils.AssertUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 宠物类别业务逻辑实现类
 */
@Service
public class PetTypeServiceImpl implements PetTypeService {

    @Resource
    private PetTypeMapper petTypeMapper;

    /**
     * 宠物类别新增
     *
     * @param petType 实体数据
     * @return Result<String> 后台通用返回封装类
     */
    @Override
    public Result<String> save(PetType petType) {
        // 确保传进来的宠物类别名不能为空
        AssertUtils.hasText(petType.getName(), "宠物类别名不能为空");
        // 如果传进来的宠物类别名如果系统已经存在，则不能再进行新增
        PetType petTypeEntity = petTypeMapper.queryByName(petType.getName());
        AssertUtils.isTrue(petTypeEntity == null, "宠物类别名已经存在");
        petTypeMapper.save(petType);
        return ApiResult.success("宠物类别新增成功");
    }

    /**
     * 宠物类别修改
     *
     * @param petType 实体数据
     * @return Result<String> 后台通用返回封装类
     */
    @Override
    public Result<String> update(PetType petType) {
        // 确保传进来的宠物类别名不能为空
        AssertUtils.hasText(petType.getName(), "宠物类别名不能为空");
        // 如果传进来的宠物类别名如果系统已经存在，则不能再进行新增
        PetType petTypeEntity = petTypeMapper.queryByName(petType.getName());
        AssertUtils.isTrue(petTypeEntity == null, "宠物类别名已经存在");
        petTypeMapper.update(petType);
        return ApiResult.success("宠物类别修改成功");
    }

    /**
     * 宠物类别删除
     *
     * @param id 主键ID
     * @return Result<String> 后台通用返回封装类
     */
    @Override
    public Result<String> deleteById(Integer id) {
        petTypeMapper.deleteById(id);
        return ApiResult.success("宠物类别删除成功");
    }

    /**
     * 宠物类别查询
     *
     * @param petTypeQueryDto 查询条件类
     * @return Result<List < PetType>> 后台通用返回封装类
     */
    @Override
    public Result<List<PetType>> query(PetTypeQueryDto petTypeQueryDto) {
        // 查符合条件的总条数 - 前端分页用的
        Integer count = petTypeMapper.queryCount(petTypeQueryDto);
        // 查符合条件的数据项
        List<PetType> petTypeList = petTypeMapper.query(petTypeQueryDto);
        return ApiResult.success(petTypeList, count);
    }
}
