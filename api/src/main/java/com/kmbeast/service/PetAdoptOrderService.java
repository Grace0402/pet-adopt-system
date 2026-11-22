package com.kmbeast.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kmbeast.pojo.api.Result;
import com.kmbeast.pojo.dto.PetAdoptOrderQueryDto;
import com.kmbeast.pojo.entity.PetAdoptOrder;
import com.kmbeast.pojo.vo.PetAdoptOrderVO;

import java.util.List;

/**
 * 宠物领养订单业务逻辑接口
 */
public interface PetAdoptOrderService extends IService<PetAdoptOrder> {

    Result<String> saveEntity(PetAdoptOrder petAdoptOrder);

    Result<String> update(PetAdoptOrder petAdoptOrder);

    Result<List<PetAdoptOrderVO>> query(PetAdoptOrderQueryDto petAdoptOrderQueryDto);

    Result<String> audit(PetAdoptOrder petAdoptOrder);

    Result<String> againReply(PetAdoptOrder petAdoptOrder);

}
