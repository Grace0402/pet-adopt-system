package com.kmbeast.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {
    private String account; // 账号
    private String username; //用户名
    private String password;// 密码
    private String avatar; // 头像
    private String email; // 邮箱
    private Integer gender; // 性别
    private LocalDate birthday; // 出生日期
    private String phone; // 手机号
}
