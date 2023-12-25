package com.yang.cloud.wms.user.controller;

import com.yang.cloud.wms.common.entity.user.User;
import com.yang.cloud.wms.common.result.Result;
import com.yang.cloud.wms.core.annotation.WmsController;
import com.yang.cloud.wms.core.contextHolder.UserContextHolder;
import com.yang.cloud.wms.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@WmsController("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/detail/{id}")
    public Result<User> userDetail(@PathVariable("id") Long id) {
        System.out.println("userId:"+UserContextHolder.getUserId());
        return Result.success(userService.userDetail(id));
    }
}
