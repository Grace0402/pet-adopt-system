package com.kmbeast.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kmbeast.pojo.dto.PetAdoptOrderQueryDto;
import com.kmbeast.pojo.entity.PetAdoptOrder;
import com.kmbeast.pojo.vo.PetAdoptOrderVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 宠物领养订单持久化接口
 */
@Mapper
public interface PetAdoptOrderMapper extends BaseMapper<PetAdoptOrder> {

    /**
     * 查询宠物领养订单信息
     *
     * @param petAdoptOrderQueryDto 查询条件类
     * @return List<PetAdoptOrderVO>
     */
    List<PetAdoptOrderVO> list(PetAdoptOrderQueryDto petAdoptOrderQueryDto);

    /**
     * 查询符合条件的记录数 - 配合前端做分页
     *
     * @param petAdoptOrderQueryDto 查询条件类
     * @return Integer 记录数
     */
    Integer listCount(PetAdoptOrderQueryDto petAdoptOrderQueryDto);

}
