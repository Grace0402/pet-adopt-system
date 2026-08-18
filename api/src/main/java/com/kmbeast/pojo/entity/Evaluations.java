package com.kmbeast.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 评论实体，对应数据库的 evaluations 表
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Evaluations {
    private Integer id; // ID，主键自增
    private Integer parentId; // 父级评论ID，为了构建评论的树形嵌套结构
    private Integer commenterId; // 评论者用户ID，与user表关联，为用户ID
    private Integer replierId; // 回复者ID，与user表关联，为用户ID
    private String contentType; // 内容类型，标识评论挂载的模块
    private String content; // 评论的内容
    private Integer contentId; // 内容的ID，与内容类型配合模块下评论查询
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") // 这行注解的作用是将查询出来的时间按照这种格式化，返回前端
    private LocalDateTime createTime; // 评论时间
}
