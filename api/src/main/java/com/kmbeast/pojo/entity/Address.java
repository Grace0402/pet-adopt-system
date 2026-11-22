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
 * 收货地址信息表，与数据库address表对应
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "address")
public class Address {

    /**
     * 收货地址信息表主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 用户ID，外键，关联的是用户表
     */
    private Integer userId;
    /**
     * 详细地址
     */
    private String detail;
    /**
     * 收件人
     */
    private String addressee;
    /**
     * 联系电话
     */
    private String concatPhone;
    /**
     * 是否是默认地址（0：非默认；1：默认）
     */
    private Boolean isDefault;
    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
