package com.kmbeast.pojo.em;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 宠物是否接种疫苗状态枚举
 */
@Getter
@AllArgsConstructor
public enum IsVaccineEnum {

    NO_VACCINE(false, "未接种"),
    VACCINE(true, "已接种");

    private final Boolean status; // 状态
    private final String name; // 状态描述

}
