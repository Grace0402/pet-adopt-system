package com.kmbeast.pojo.em;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 是否再次提交状态枚举
 */
@Getter
@AllArgsConstructor
public enum IsAgainPostEnum {

    ORIGIN_REPLY(false, "初次提交"),
    AGAIN_REPLY(true, "再次提交");

    private final Boolean status; // 状态
    private final String name; // 状态描述

}
