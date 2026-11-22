package com.kmbeast.pojo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 意见与反馈查询条件类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProposalFeedbackQueryDto extends QueryDto {
    /**
     * 用户ID，外键，关联的是用户表
     */
    private Integer userId;
    /**
     * 是否已经回复（0：未回复；1：已回复）
     */
    private Boolean isReply;
    /**
     * 反馈详情
     */
    private String detail;
}
