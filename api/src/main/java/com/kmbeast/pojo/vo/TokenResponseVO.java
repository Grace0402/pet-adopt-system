package com.kmbeast.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Token响应凭证VO
 */
@Data
@AllArgsConstructor
public class TokenResponseVO {
    private Integer id; // 用户ID
    private String token; // 令牌
    private Integer role; // 角色
}
