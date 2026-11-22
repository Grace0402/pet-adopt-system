package com.kmbeast.controller;

import com.kmbeast.aop.Pager;
import com.kmbeast.pojo.api.Result;
import com.kmbeast.pojo.dto.PetQueryDto;
import com.kmbeast.pojo.entity.Pet;
import com.kmbeast.pojo.vo.PetListItemVO;
import com.kmbeast.pojo.vo.PetVO;
import com.kmbeast.service.PetService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 宠物控制器
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Resource
    private PetService petService;

    /**
     * 宠物新增
     *
     * @param pet 实体数据
     * @return Result<String> 通用返回封装类
     */
    @ResponseBody
    @PostMapping(value = "/save")
    public Result<String> save(@RequestBody Pet pet) {
        return petService.save(pet);
    }

    /**
     * 宠物修改
     *
     * @param pet 实体数据
     * @return Result<String> 通用返回封装类
     */
    @ResponseBody
    @PutMapping(value = "/update")
    public Result<String> update(@RequestBody Pet pet) {
        return petService.update(pet);
    }

    /**
     * 宠物删除
     *
     * @param id 主键ID
     * @return Result<String> 通用返回封装类
     */
    @ResponseBody
    @DeleteMapping(value = "/{id}")
    public Result<String> deleteById(@PathVariable Integer id) {
        return petService.deleteById(id);
    }

    /**
     * 通过宠物ID查询宠物详情信息
     *
     * @param id 宠物ID
     * @return Result<List < PetVO>> 通用返回封装类
     */
    @ResponseBody
    @GetMapping(value = "/{id}")
    public Result<PetVO> getById(@PathVariable Integer id) {
        return petService.getById(id);
    }

    /**
     * 通过宠物ID查询宠物详情信息
     *
     * @param id 宠物ID
     * @return Result<List < PetVO>> 通用返回封装类
     */
    @ResponseBody
    @GetMapping(value = "/viewById/{id}")
    public Result<PetVO> viewById(@PathVariable Integer id) {
        return petService.viewById(id);
    }

    /**
     * 宠物查询信息列表
     *
     * @param petQueryDto 实体数据
     * @return Result<List < PetListItemVO>> 通用返回封装类
     */
    @Pager
    @ResponseBody
    @PostMapping(value = "/list")
    public Result<List<PetListItemVO>> list(@RequestBody PetQueryDto petQueryDto) {
        return petService.list(petQueryDto);
    }

    /**
     * 查询手动推荐宠物信息
     *
     * @return Result<List < PetListItemVO>> 通用返回封装类
     */
    @ResponseBody
    @GetMapping(value = "/recommend")
    public Result<List<PetListItemVO>> recommend() {
        return petService.recommend();
    }

    /**
     * 查询用户收藏的宠物信息
     *
     * @return Result<List < PetListItemVO>> 通用返回封装类
     */
    @ResponseBody
    @GetMapping(value = "/saveList")
    public Result<List<PetListItemVO>> saveList() {
        return petService.saveList();
    }


    /**
     * 智能推荐宠物信息
     * @param count 期望拿到的条数
     * @return Result<List < PetListItemVO>> 通用返回封装类
     */
    @ResponseBody
    @GetMapping(value = "/autoRecommend/{count}")
    public Result<List<PetListItemVO>> autoRecommend(@PathVariable Integer count) {
        return petService.autoRecommend(count);
    }

}

