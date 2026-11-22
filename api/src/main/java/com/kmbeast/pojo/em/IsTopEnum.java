package com.kmbeast.pojo.em;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 精华帖状态枚举
 */
@Getter
@AllArgsConstructor
public enum IsTopEnum {

    NO_TOP(false, "未精华帖"),
    TOP(true, "精华帖");

    private final Boolean status; // 状态
    private final String name; // 状态描述

}
