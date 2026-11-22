package com.kmbeast.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kmbeast.pojo.dto.AddressQueryDto;
import com.kmbeast.pojo.dto.NoticeQueryDto;
import com.kmbeast.pojo.entity.Address;
import com.kmbeast.pojo.entity.Notice;
import com.kmbeast.pojo.vo.NoticeListItemVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 收货地址持久化接口
 */
@Mapper
public interface AddressMapper extends BaseMapper<Address> {

    /**
     * 查询收货地址信息
     *
     * @param addressQueryDto 查询条件类
     * @return List<NoticeListItemVO>
     */
    List<Address> list(AddressQueryDto addressQueryDto);

    /**
     * 查询符合条件的记录数 - 配合前端做分页
     *
     * @param addressQueryDto 查询条件类
     * @return Integer 记录数
     */
    Integer listCount(AddressQueryDto addressQueryDto);

}
