package com.kmbeast.pojo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *行为互动查询条件类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ActiveNetQueryDto extends QueryDto{
    /**
     * 用户ID，外键，关联的是用户表
     */
    private Integer userId;
    /**
     * 内容ID，与内容模块配合使用
     */
    private Integer contentId;
    /**
     * 内容模块
     */
    private String contentType;
    /**
     * 行为类型（1：浏览；2：点赞；3：收藏）
     */
    private Integer type;
    /**
     * 查询天数
     */
    private Integer days;
}
