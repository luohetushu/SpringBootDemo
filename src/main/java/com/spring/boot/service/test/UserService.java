package com.spring.boot.service.test;

import com.spring.boot.dao.UserDao;
import com.spring.boot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User getUserByUid(long uid){
        return userDao.getUserByUid(uid);
    }

}
