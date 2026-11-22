package com.kmbeast.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 宠物类别信息表，与数据库pet_type表对应
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetType {

    /**
     * 宠物种类信息表主键ID
     */
    private Integer id;
    /**
     * 类别名
     */
    private String name;

}
