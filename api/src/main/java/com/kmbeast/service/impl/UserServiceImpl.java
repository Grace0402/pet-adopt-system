package com.kmbeast.service.impl;

import com.kmbeast.context.LocalThreadHolder;
import com.kmbeast.mapper.UserMapper;
import com.kmbeast.pojo.api.ApiResult;
import com.kmbeast.pojo.api.Result;
import com.kmbeast.pojo.dto.*;
import com.kmbeast.pojo.em.RoleEnum;
import com.kmbeast.pojo.entity.User;
import com.kmbeast.pojo.vo.ChartVO;
import com.kmbeast.pojo.vo.TokenResponseVO;
import com.kmbeast.pojo.vo.UserVO;
import com.kmbeast.service.UserService;
import com.kmbeast.utils.AssertUtils;
import com.kmbeast.utils.DateUtil;
import com.kmbeast.utils.JwtUtil;
import com.kmbeast.utils.ValidationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户服务实现类
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 用户注册
     *
     * @param userRegisterDTO 注册入参
     * @return Result<String> 响应结果
     */
    @Override
    public Result<String> register(UserRegisterDto userRegisterDTO) {
        AssertUtils.hasText(userRegisterDTO.getUsername(), "用户名不能为空");
        AssertUtils.hasText(userRegisterDTO.getAccount(), "账号不能为空");
        AssertUtils.isFalse(ValidationUtils.containsChinese(userRegisterDTO.getAccount()), "账号不能包含中文");
        // 通过用户名查询用户信息
        User userEntity = userMapper.getUserByUsername(userRegisterDTO.getUsername());
        AssertUtils.isTrue(userEntity == null, "用户名已经被使用，请换一个");
        // 通过账号查询用户信息
        User entity = userMapper.getUserByAccount(userRegisterDTO.getAccount());
        AssertUtils.isTrue(entity == null, "账号不可用");
        User user = new User();
        BeanUtils.copyProperties(userRegisterDTO, user);
        user.setCreateTime(LocalDateTime.now()); // 设置注册时间
        user.setRole(RoleEnum.USER.getRole()); // 设置初始角色 - 用户
        userMapper.save(user);
        return ApiResult.success("注册成功");
    }

    /**
     * 用户登录
     *
     * @param userLoginDTO 登录入参
     * @return Result<String> 响应结果
     */
    @Override
    public Result<Object> login(UserLoginDto userLoginDTO) {
        AssertUtils.hasText(userLoginDTO.getAccount(), "账号不能为空");
        AssertUtils.hasText(userLoginDTO.getAccount(), "密码不能为空");
        User user = userMapper.getUserByAccount(userLoginDTO.getAccount());
        AssertUtils.isTrue(user != null, "账户不存在");
        AssertUtils.equals(userLoginDTO.getPassword(), user.getPassword(), "密码错误");
        String token = JwtUtil.toToken(user.getId(), user.getRole());
        TokenResponseVO tokenResponseVO = new TokenResponseVO(user.getId(), token, user.getRole());
        return ApiResult.success("登录成功", tokenResponseVO);
    }

    /**
     * token检验
     *
     * @return Result<UserVO>
     */
    @Override
    public Result<UserVO> auth() {
        User user = userMapper.getUserById(LocalThreadHolder.getUserId());
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return ApiResult.success(userVO);
    }

    /**
     * 分页查询用户数据
     *
     * @param userQueryDto 分页参数
     * @return Result<List < User>> 响应结果
     */
    @Override
    public Result<List<User>> query(UserQueryDto userQueryDto) {
        List<User> users = userMapper.query(userQueryDto);
        Integer count = userMapper.queryCount(userQueryDto);
        return ApiResult.success(users, count);
    }

    /**
     * 用户信息修改
     *
     * @param userUpdateDTO 修改信息入参
     * @return Result<UserVO> 响应结果
     */
    @Override
    public Result<UserVO> update(UserUpdateDto userUpdateDTO) {
        // 用户填写了邮箱。要检验号码格式
        if (StringUtils.hasText(userUpdateDTO.getEmail())) {
            if (!ValidationUtils.isValidEmail(userUpdateDTO.getEmail())) {
                return ApiResult.error("请填写正确邮箱");
            }
        }
        // 用户填写了号码。要检验号码格式
        if (StringUtils.hasText(userUpdateDTO.getPhone())) {
            // 国内号码
            AssertUtils.isTrue(
                    ValidationUtils.isValidMobile(userUpdateDTO.getPhone()),
                    "号码格式错误，请重新检查"
            );
        }
        User updateEntity = User.builder().id(LocalThreadHolder.getUserId()).build();
        BeanUtils.copyProperties(userUpdateDTO, updateEntity);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(updateEntity, userVO);
        userMapper.update(updateEntity);
        return ApiResult.success(userVO);
    }


    /**
     * 批量删除用户信息
     */
    @Override
    public Result<String> batchDelete(List<Integer> ids) {
        userMapper.batchDelete(ids);
        return ApiResult.success();
    }

    /**
     * 修改密码
     *
     * @param userUpdatePasswordDto 密码入参
     * @return Result<String> 响应结果
     */
    @Override
    public Result<String> updatePassword(UserUpdatePasswordDto userUpdatePasswordDto) {
        User user = userMapper.getUserById(LocalThreadHolder.getUserId());
        AssertUtils.equals(
                userUpdatePasswordDto.getNewPassword(),
                userUpdatePasswordDto.getAgainPassword(),
                "前后密码输入不一致"
        );
        AssertUtils.equals(
                user.getPassword(),
                userUpdatePasswordDto.getOldPassword(),
                "原始密码验证失败"
        );
        user.setPassword(userUpdatePasswordDto.getNewPassword());
        userMapper.update(user);
        return ApiResult.success("密码修改成功，请重新登录");
    }

    /**
     * 通过ID查询用户信息
     *
     * @param id 用户ID
     */
    @Override
    public Result<UserVO> getById(Integer id) {
        User user = userMapper.getUserById(id);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return ApiResult.success(userVO);
    }

    /**
     * 后台新增用户
     *
     * @param userRegisterDTO 注册入参
     * @return Result<String> 响应结果
     */
    @Override
    public Result<String> save(UserRegisterDto userRegisterDTO) {
        return register(userRegisterDTO);
    }

    /**
     * 后台用户信息修改
     *
     * @param user 信息实体
     * @return Result<String> 响应结果
     */
    @Override
    public Result<String> backUpdate(User user) {
        AssertUtils.isTrue(StringUtils.hasText(user.getUsername()), "账号不能为空");
        // 用户填写了邮箱，要检验邮箱格式
        if (StringUtils.hasText(user.getEmail())) {
            if (!ValidationUtils.isValidEmail(user.getEmail())) {
                return ApiResult.error("请填写正确邮箱");
            }
        }
        // 用户填写了号码。要检验号码格式
        if (StringUtils.hasText(user.getPhone())) {
            // 国内号码
            AssertUtils.isTrue(
                    ValidationUtils.isValidMobile(user.getPhone()),
                    "号码格式错误，请重新检查"
            );
        }
        userMapper.update(user);
        return ApiResult.success();
    }

    /**
     * 统计指定时间里面的用户存量数据
     *
     * @param day 天数
     * @return Result<List < ChartVO>>
     */
    @Override
    public Result<List<ChartVO>> daysQuery(Integer day) {
        QueryDto queryDto = DateUtil.startAndEndTime(day);
        UserQueryDto userQueryDto = new UserQueryDto();
        userQueryDto.setStartTime(queryDto.getStartTime());
        userQueryDto.setEndTime(queryDto.getEndTime());
        List<User> userList = userMapper.query(userQueryDto);
        List<LocalDateTime> localDateTimes = userList.stream().map(User::getCreateTime).collect(Collectors.toList());
        List<ChartVO> chartVOS = DateUtil.countDatesWithinRange(day, localDateTimes);
        return ApiResult.success(chartVOS);
    }

    /**
     * 通过ID删除用户数据
     *
     * @param id 用户ID
     * @return Result<String>
     */
    @Override
    public Result<String> deleteById(Integer id) {
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(id);
        userMapper.batchDelete(ids);
        return ApiResult.success();
    }
}
