package com.kmbeast.mapper;

import com.kmbeast.pojo.dto.UserQueryDto;
import com.kmbeast.pojo.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户持久化接口
 */
public interface UserMapper {

    int save(User userInsert);

    List<User> query(UserQueryDto userQueryDto);

    int queryCount(UserQueryDto userQueryDto);

    int update(User user);

    void batchDelete(@Param(value = "ids") List<Integer> ids);

    User getUserById(@Param(value = "id") Integer id);

    User getUserByAccount(@Param(value = "account") String account);

    User getUserByUsername(@Param(value = "username") String account);

}
