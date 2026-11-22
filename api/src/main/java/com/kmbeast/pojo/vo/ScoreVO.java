package com.kmbeast.pojo.vo;

import lombok.Data;

/**
 * 评论的VO类
 */
@Data
public class ScoreVO {
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 内容
     */
    private Integer contentId;
    /**
     * 评分
     */
    private Integer score;
}
