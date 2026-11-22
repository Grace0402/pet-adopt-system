package com.kmbeast.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 宠物经验帖子列表VO项
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetPostListItemVO {

    private Integer id;
    /**
     * 用户ID，外键，关联的是用户表
     */
    private Integer userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 标题
     */
    private String title;
    /**
     * 封面
     */
    private String cover;
    /**
     * 摘要
     */
    private String summary;
    /**
     * 点赞量
     */
    private Integer upvoteNumber;
    /**
     * 浏览量
     */
    private Integer viewNumber;
    /**
     * 收藏量
     */
    private Integer saveNumber;
    /**
     * 是否已经审核（0：未审核；1：已审核）
     */
    private Boolean isAudit;
    /**
     * 发布时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
