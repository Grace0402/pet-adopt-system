package com.kmbeast.service;

import com.kmbeast.pojo.api.Result;
import com.kmbeast.pojo.dto.*;
import com.kmbeast.pojo.entity.User;
import com.kmbeast.pojo.vo.ChartVO;
import com.kmbeast.pojo.vo.UserVO;

import java.util.List;
import java.util.Map;

/**
 * 用户服务类
 */
public interface UserService {

    Result<String> register(UserRegisterDto userRegisterDTO);

    Result<Object> login(UserLoginDto userLoginDTO);

    Result<UserVO> auth();

    Result<List<User>> query(UserQueryDto userQueryDto);

    Result<UserVO> update(UserUpdateDto userUpdateDTO);

    Result<String> batchDelete(List<Integer> ids);

    Result<String> updatePassword(UserUpdatePasswordDto userUpdatePasswordDto);

    Result<UserVO> getById(Integer id);

    Result<String> save(UserRegisterDto userRegisterDTO);

    Result<String> backUpdate(User user);

    Result<List<ChartVO>> daysQuery(Integer day);

    Result<String> deleteById(Integer id);

}
