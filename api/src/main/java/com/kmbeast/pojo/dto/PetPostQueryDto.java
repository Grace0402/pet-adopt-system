package com.kmbeast.pojo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *宠物经验帖子查询条件类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PetPostQueryDto extends QueryDto{
    /**
     * 用户ID，外键，关联的是用户表
     */
    private Integer userId;
    /**
     * 宠物类别ID，外键，关联的是宠物类别表
     */
    private Integer petTypeId;
    /**
     * 标题
     */
    private String title;
    /**
     * 是否已经审核（0：未审核；1：已审核）
     */
    private Boolean isAudit;
}
