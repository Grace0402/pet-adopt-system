package com.kmbeast.pojo.em;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 回复状态枚举
 */
@Getter
@AllArgsConstructor
public enum IsReplyEnum {

    NO_REPLY(false, "未回复"),
    REPLY(true, "已回复");

    private final Boolean status; // 状态
    private final String name; // 状态描述

}
