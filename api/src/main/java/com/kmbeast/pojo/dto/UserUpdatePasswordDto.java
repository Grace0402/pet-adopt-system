package com.kmbeast.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
public class UserUpdatePasswordDto {
    private String oldPassword;
    private String newPassword;
    private String againPassword;
}
