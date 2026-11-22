package com.kmbeast.pojo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 收货地址查询条件类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AddressQueryDto extends QueryDto {
    /**
     * 用户ID，外键，关联的是用户表
     */
    private Integer userId;
    /**
     * 是否是默认地址
     */
    private Boolean isDefault;
    /**
     * 收件人
     */
    private String addressee;
}
