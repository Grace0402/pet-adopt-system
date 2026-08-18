package com.kmbeast.pojo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 评论点赞实体，对应数据库的 evaluations_upvote 表
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class EvaluationsUpvote {
    private Integer id; // ID，主键自增
    private Integer userId; // 用户ID，关联用户表的用户ID，标识是哪个用户点的赞
    private Integer evaluationsId;// 评论ID，关联的是评论表的评论ID，标识用户针对哪条评论点的赞
}
