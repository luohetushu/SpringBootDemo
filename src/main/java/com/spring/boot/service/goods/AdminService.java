package com.spring.boot.service.goods;

import com.spring.boot.dao.AdminDao;
import com.spring.boot.dao.UserDao;
import com.spring.boot.entity.Admin;
import com.spring.boot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminDao adminDao;

    public Admin getAdminByUid(String aid){
        return adminDao.getAdminByAid(aid);
    }

}
