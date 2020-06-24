package com.spring.boot.dao;

import com.spring.boot.entity.Admin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminDao {

    Admin getAdminByAid(@Param("aid") String aid);


}
