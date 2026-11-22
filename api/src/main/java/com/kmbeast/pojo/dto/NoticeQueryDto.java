package com.kmbeast.pojo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 公告查询条件类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class NoticeQueryDto extends QueryDto{
    /**
     * 标题
     */
    private String title;
}
