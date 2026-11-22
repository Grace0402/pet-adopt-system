package com.kmbeast.controller;

import com.kmbeast.aop.Pager;
import com.kmbeast.context.LocalThreadHolder;
import com.kmbeast.pojo.api.ApiResult;
import com.kmbeast.pojo.api.Result;
import com.kmbeast.pojo.dto.AddressQueryDto;
import com.kmbeast.pojo.entity.Address;
import com.kmbeast.service.AddressService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 收货地址控制器
 */
@RestController
@RequestMapping("/address")
public class AddressController {

    @Resource
    private AddressService addressService;

    /**
     * 收货地址新增
     *
     * @param address 实体数据
     * @return Result<String> 通用返回封装类
     */
    @ResponseBody
    @PostMapping(value = "/save")
    public Result<String> save(@RequestBody Address address) {
        return addressService.saveEntity(address);
    }

    /**
     * 收货地址修改
     *
     * @param address 实体数据
     * @return Result<String> 通用返回封装类
     */
    @ResponseBody
    @PutMapping(value = "/update")
    public Result<String> update(@RequestBody Address address) {
        return addressService.update(address);
    }

    /**
     * 收货地址删除
     *
     * @param id 主键ID
     * @return Result<String> 通用返回封装类
     */
    @ResponseBody
    @DeleteMapping(value = "/{id}")
    public Result<String> deleteById(@PathVariable Integer id) {
        addressService.removeById(id);
        return ApiResult.success("删除成功");
    }

    /**
     * 收货地址查询
     *
     * @param addressQueryDto 实体数据
     * @return Result<List < Address>> 通用返回封装类
     */
    @Pager
    @ResponseBody
    @PostMapping(value = "/queryUser")
    public Result<List<Address>> queryUser(@RequestBody AddressQueryDto addressQueryDto) {
        addressQueryDto.setUserId(LocalThreadHolder.getUserId()); // 设置用户ID，数据隔离
        return addressService.query(addressQueryDto);
    }

    /**
     * 收货地址查询
     *
     * @param addressQueryDto 实体数据
     * @return Result<List < Address>> 通用返回封装类
     */
    @Pager
    @ResponseBody
    @PostMapping(value = "/query")
    public Result<List<Address>> query(@RequestBody AddressQueryDto addressQueryDto) {
        return addressService.query(addressQueryDto);
    }

}

