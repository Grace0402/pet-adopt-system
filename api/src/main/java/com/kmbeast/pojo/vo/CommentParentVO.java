package com.kmbeast.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 父级评论
 */
@Data
public class CommentParentVO {

    private Integer id; //评论时间
    private Integer userId; //用户ID
    private String username; //用户名
    private String avatar; //用户头像
    private String content; //评论内容
    private Boolean showReplyInput; //回复框显示状态
    private Integer childTotal; //一共拥有的子级评论数
    private Boolean upvoteFlag; //用户是否已经点赞
    private Integer upvoteCount; //点赞量
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime; //评论时间
    private List<CommentChildVO> commentChildVOS; //子级评论

}
