package com.kmbeast.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 意见与反馈信息表，与数据库proposal_feedback表对应
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "proposal_feedback") // 标识对应的数据库表
public class ProposalFeedback {

    /**
     * 意见与反馈信息表主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 用户ID，外键，关联的是用户表
     */
    private Integer userId;
    /**
     * 描述
     */
    private String detail;
    /**
     * 是否已经回复（0：未回复；1：已回复）
     */
    private Boolean isReply;
    /**
     * 回复内容
     */
    private String replyContent;
    /**
     * 是否是精华帖（0：非精华贴；1：是精华帖）
     */
    private Boolean isTop;
    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /**
     * 回复时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime replyTime;

}
