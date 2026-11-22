package com.kmbeast.pojo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 宠物领养订单查询条件类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PetAdoptOrderQueryDto extends QueryDto {
    /**
     * 用户ID，外键，关联的是用户表
     */
    private Integer userId;
    /**
     * 宠物ID，外键，关联的是用户表
     */
    private Integer petId;
    /**
     * 单据状态（1：申请中；2：已审核；3：审核未通过；4：已完成）
     */
    private Integer status;
}
