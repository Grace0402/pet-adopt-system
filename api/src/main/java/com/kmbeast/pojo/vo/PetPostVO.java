package com.kmbeast.pojo.vo;

import com.kmbeast.pojo.entity.PetPost;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 宠物经验帖子VO类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PetPostVO extends PetPost {
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 宠物类别名
     */
    private String petTypeName;
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

}
