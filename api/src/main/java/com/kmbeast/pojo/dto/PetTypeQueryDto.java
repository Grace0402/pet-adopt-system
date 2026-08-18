package com.kmbeast.pojo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 宠物类别查询条件类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PetTypeQueryDto extends QueryDto{
    /**
     * 类别名
     */
    private String name;
}
