package com.kmbeast.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kmbeast.pojo.api.Result;
import com.kmbeast.pojo.dto.AddressQueryDto;
import com.kmbeast.pojo.entity.Address;

import java.util.List;

/**
 * 收货地址业务逻辑接口
 */
public interface AddressService extends IService<Address> {

    Result<String> saveEntity(Address address);

    Result<String> update(Address address);

    Result<List<Address>> query(AddressQueryDto addressQueryDto);

}
