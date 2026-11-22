package com.kmbeast.pojo.em;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 宠物领养订单状态枚举
 * 1：申请中；2：已审核；3：审核未通过；4：已完成
 */
@Getter
@AllArgsConstructor
public enum PetAdoptOrderStatus {

    REPLYING(1, "申请中"),
    AUDIT_OK(2, "已审核"),
    AUDIT_ERROR(3, "审核不通过"),
    COMPLETE(4, "已完成");

    private final Integer status; // 状态码
    private final String name; // 状态描述
}
