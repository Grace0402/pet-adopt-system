package com.kmbeast.pojo.em;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 宠物领养状态枚举
 */
@Getter
@AllArgsConstructor
public enum IsAdoptEnum {

    NO_ADOPT(false, "未领养"),
    ADOPT(true, "已领养");

    private final Boolean status; // 领养状态
    private final String name; // 领养状态描述

}
