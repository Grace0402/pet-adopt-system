package com.kmbeast.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 用户注册Dto参数类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto {
    private String username; // 用户名
    private String account; // 账号
    private String password; // 密码
    private String email; // 邮箱
    private String avatar; // 头像
    private String phone; // 手机号
    private Integer gender; // 性别
    private LocalDate birthday; // 出生日期
}
