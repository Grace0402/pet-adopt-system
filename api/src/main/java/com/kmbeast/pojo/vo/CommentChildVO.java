package com.kmbeast.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentChildVO {

    private Integer id; // 评论ID
    private Integer parentId; // 父级评论ID
    private Integer userId; // 评论者ID
    private String username; // 评论者用户名
    private String avatar; // 评论者头像
    private Integer replierId; // 被回复者ID
    private String replierName; // 被回复者用户名
    private String replierAvatar; // 被回复者头像
    private String content; // 评论内容
    private Boolean replyInputStatus; // 评论回复状态
    private Boolean upvoteFlag; //用户是否已经点赞
    private Integer upvoteCount; //点赞量
    private String contentType; //内容类型
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime; //评论时间

}
