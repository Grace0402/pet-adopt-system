package com.kmbeast.controller;

import com.kmbeast.aop.Pager;
import com.kmbeast.pojo.api.Result;
import com.kmbeast.pojo.dto.PetTypeQueryDto;
import com.kmbeast.pojo.entity.PetType;
import com.kmbeast.service.PetTypeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 宠物类别控制器
 */
@RestController
@RequestMapping("/pet-type")
public class PetTypeController {

    @Resource
    private PetTypeService petTypeService;

    /**
     * 宠物类别新增
     *
     * @param petType 实体数据
     * @return Result<String> 通用返回封装类
     */
    @ResponseBody
    @PostMapping(value = "/save")
    public Result<String> save(@RequestBody PetType petType) {
        return petTypeService.save(petType);
    }

    /**
     * 宠物类别修改
     *
     * @param petType 实体数据
     * @return Result<String> 通用返回封装类
     */
    @ResponseBody
    @PostMapping(value = "/update")
    public Result<String> update(@RequestBody PetType petType) {
        return petTypeService.update(petType);
    }

    /**
     * 宠物类别删除
     *
     * @param id 主键ID
     * @return Result<String> 通用返回封装类
     */
    @ResponseBody
    @DeleteMapping(value = "/{id}")
    public Result<String> deleteById(@PathVariable Integer id) {
        return petTypeService.deleteById(id);
    }

    /**
     * 宠物类别查询
     *
     * @param petTypeQueryDto 实体数据
     * @return Result<List < PetType>> 通用返回封装类
     */
    @Pager
    @ResponseBody
    @PostMapping(value = "/query")
    public Result<List<PetType>> query(@RequestBody PetTypeQueryDto petTypeQueryDto) {
        return petTypeService.query(petTypeQueryDto);
    }

}

