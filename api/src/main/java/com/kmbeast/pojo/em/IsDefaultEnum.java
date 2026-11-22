package com.kmbeast.pojo.em;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 默认地址状态枚举
 */
@Getter
@AllArgsConstructor
public enum IsDefaultEnum {

    NO_DEFAULT_ADDRESS(false, "非默认地址"),
    DEFAULT_ADDRESS(true, "默认地址");

    private final Boolean status; // 状态
    private final String name; // 状态描述

}
