package com.kmbeast.pojo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户查询Dto参数类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryDto extends QueryDto {
    private String account; // 用户账号
    private String username; // 用户名
    private String email; // 用户邮箱
    private Integer role; // 用户角色(1：管理员；2：用户)
    private Boolean isLogin; // 是否封号 (true：已经封号；false：未封号)
    private Boolean isWord; // 是否禁言 (true：已经禁言；false：未禁言)
}
