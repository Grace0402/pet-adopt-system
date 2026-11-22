package com.kmbeast.pojo.em;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 行为互动状态枚举
 */
@Getter
@AllArgsConstructor
public enum ActiveNetType {
    VIEW(1, "浏览"),
    UPVOTE(2, "点赞"),
    SAVE(3, "收藏");

    private final Integer status; // 状态
    private final String name; // 描述
}
