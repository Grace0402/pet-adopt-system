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
 * 宠物经验帖子信息表，与数据库pet_post表对应
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "pet_post") // 标识与数据库表pet_post对应
public class PetPost {

    /**
     * 宠物经验帖子信息表主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 用户ID，外键，关联的是用户表
     */
    private Integer userId;
    /**
     * 宠物类别ID，外键，关联的是宠物类别表
     */
    private Integer petTypeId;
    /**
     * 标题
     */
    private String title;
    /**
     * 封面
     */
    private String cover;
    /**
     * 内容
     */
    private String content;
    /**
     * 摘要
     */
    private String summary;
    /**
     * 是否已经审核（0：未审核；1：已审核）
     */
    private Boolean isAudit;
    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
