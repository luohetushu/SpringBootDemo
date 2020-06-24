package com.spring.boot.web;

import com.spring.boot.entity.Admin;
import com.spring.boot.entity.User;
import com.spring.boot.service.goods.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "管理员管理")
@RestController
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 处理"/admins/{aid}"的GET请求，用来获取url中id值的Admin信息
     * @param aid
     * @return
     */
    @GetMapping("/{aid}")
    @ApiOperation(value = "获取管理员详细信息", notes = "根据url的aid来获取管理员详细信息", httpMethod = "GET")
    @ApiImplicitParam(name = "aid", value = "管理员id", paramType = "path", dataType = "String", required = true)
    public Admin getAdmin(@PathVariable String aid) {
        if (adminService == null){
            System.out.println("*****NullPointerException********");
        }
        // url中的id可通过@PathVariable绑定到函数的参数中
        return adminService.getAdminByUid(aid);
    }

}
