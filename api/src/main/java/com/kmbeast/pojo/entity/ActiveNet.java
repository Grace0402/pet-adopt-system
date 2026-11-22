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
 * 行为互动信息表，与数据库active_net对应
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "active_net") // 标识对应的数据库表
public class ActiveNet {

    /**
     * 行为互动信息表主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 用户ID，外键，关联的是用户表
     */
    private Integer userId;
    /**
     * 内容ID，与内容模块配合使用
     */
    private Integer contentId;
    /**
     * 内容模块
     */
    private String contentType;
    /**
     * 行为类型（1：浏览；2：点赞；3：收藏）
     */
    private Integer type;
    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
