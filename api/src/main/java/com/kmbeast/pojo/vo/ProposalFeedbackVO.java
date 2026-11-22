package com.kmbeast.pojo.vo;

import com.kmbeast.pojo.entity.ProposalFeedback;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 意见与反馈VO类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProposalFeedbackVO extends ProposalFeedback {
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户头像
     */
    private String avatar;
}
