package com.kmbeast.controller;

import com.kmbeast.aop.Pager;
import com.kmbeast.context.LocalThreadHolder;
import com.kmbeast.pojo.api.ApiResult;
import com.kmbeast.pojo.api.Result;
import com.kmbeast.pojo.dto.PetAdoptOrderQueryDto;
import com.kmbeast.pojo.entity.PetAdoptOrder;
import com.kmbeast.pojo.vo.PetAdoptOrderVO;
import com.kmbeast.service.PetAdoptOrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 宠物领养订单控制器
 */
@RestController
@RequestMapping("/pet-adopt-order")
public class PetAdoptOrderController {

    @Resource
    private PetAdoptOrderService petAdoptOrderService;

    /**
     * 宠物领养订单新增
     *
     * @param petAdoptOrder 实体数据
     * @return Result<String> 通用返回封装类
     */
    @ResponseBody
    @PostMapping(value = "/save")
    public Result<String> save(@RequestBody PetAdoptOrder petAdoptOrder) {
        return petAdoptOrderService.saveEntity(petAdoptOrder);
    }

    /**
     * 宠物领养订单修改
     *
     * @param petAdoptOrder 实体数据
     * @return Result<String> 通用返回封装类
     */
    @ResponseBody
    @PutMapping(value = "/update")
    public Result<String> update(@RequestBody PetAdoptOrder petAdoptOrder) {
        return petAdoptOrderService.update(petAdoptOrder);
    }

    /**
     * 管理员审核
     *
     * @param petAdoptOrder 实体数据
     * @return Result<String> 通用返回封装类
     */
    @ResponseBody
    @PutMapping(value = "/audit")
    public Result<String> audit(@RequestBody PetAdoptOrder petAdoptOrder) {
        return petAdoptOrderService.audit(petAdoptOrder);
    }

    /**
     * 用户再次提交订单数据
     *
     * @param petAdoptOrder 实体数据
     * @return Result<String> 通用返回封装类
     */
    @ResponseBody
    @PutMapping(value = "/againReply")
    public Result<String> againReply(@RequestBody PetAdoptOrder petAdoptOrder) {
        return petAdoptOrderService.againReply(petAdoptOrder);
    }

    /**
     * 宠物领养订单删除
     *
     * @param id 主键ID
     * @return Result<String> 通用返回封装类
     */
    @ResponseBody
    @DeleteMapping(value = "/{id}")
    public Result<String> deleteById(@PathVariable Integer id) {
        petAdoptOrderService.removeById(id);
        return ApiResult.success("删除成功");
    }

    /**
     * 宠物领养订单查询
     *
     * @param petAdoptOrderQueryDto 实体数据
     * @return Result<List < PetAdoptOrderVO>> 通用返回封装类
     */
    @Pager
    @ResponseBody
    @PostMapping(value = "/queryUser")
    public Result<List<PetAdoptOrderVO>> queryUser(@RequestBody PetAdoptOrderQueryDto petAdoptOrderQueryDto) {
        petAdoptOrderQueryDto.setUserId(LocalThreadHolder.getUserId()); // 设置用户ID，数据隔离
        return petAdoptOrderService.query(petAdoptOrderQueryDto);
    }

    /**
     * 宠物领养订单查询
     *
     * @param petAdoptOrderQueryDto 实体数据
     * @return Result<List < PetAdoptOrderVO>> 通用返回封装类
     */
    @Pager
    @ResponseBody
    @PostMapping(value = "/query")
    public Result<List<PetAdoptOrderVO>> query(@RequestBody PetAdoptOrderQueryDto petAdoptOrderQueryDto) {
        return petAdoptOrderService.query(petAdoptOrderQueryDto);
    }

}

