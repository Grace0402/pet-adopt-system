package com.kmbeast.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户实体，关联数据库 user 表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id; // 用户ID，主键自增
    private String account; // 账号
    private String username; // 用户名
    private String password; // 用户密码
    private String avatar; // 头像
    private String email; // 邮箱
    private Integer role; // 角色
    private Integer gender; // 性别：1-女；2-男
    private LocalDate birthday; // 生日
    private String phone; // 手机号
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime; // 注册时间
}
