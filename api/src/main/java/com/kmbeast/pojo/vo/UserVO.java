package com.kmbeast.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户VO类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {

    private Integer id; // 主键ID
    private String account; // 用户账号
    private String username; // 用户昵称
    private String avatar; // 用户头像
    private String email; // 用户邮箱
    private Integer role; // 用户角色
    private Integer gender; // 性别：0-女；1-男
    private LocalDate birthday; // 生日
    private String phone; // 手机号
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime; // 注册时间

}
