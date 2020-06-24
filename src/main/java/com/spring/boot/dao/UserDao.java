package com.spring.boot.dao;

import com.spring.boot.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户数据的增删改查操作
 */
@Repository
public interface UserDao {

    User getUserByUid(@Param("uid") long uid);

    /**
     * 获取指定用户数
     * @param offset  查询起始位置
     * @param limit   查询条数
     * @return
     */
    List<User> getUsers(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 根据实体类对象插入对应数据
     * @param user
     * @return
     */
    int insertUser(@Param("user") User user);

    int updateUserByUid(@Param("uid") long uid, @Param("user") User user);

    int deleteUserByUid(@Param("uid") long uid);

}
