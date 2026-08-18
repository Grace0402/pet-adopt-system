package com.kmbeast.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 数据源VO
 */
@Data
@AllArgsConstructor
public class ChartVO {
    private String name; // 数据名
    private Object count; // 数据总数
}
