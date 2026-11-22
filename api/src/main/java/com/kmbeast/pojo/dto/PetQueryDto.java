package com.kmbeast.pojo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 宠物查询条件类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PetQueryDto extends QueryDto{
    /**
     * 类别名
     */
    private String name;
    /**
     * 是否推荐（0：未推荐；1：推荐）
     */
    private Boolean isRecommend;
    /**
     * 宠物类别ID
     */
    private Integer petTypeId;
}
