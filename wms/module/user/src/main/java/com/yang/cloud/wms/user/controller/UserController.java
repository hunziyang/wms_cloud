package com.yang.cloud.wms.user.controller;

import com.yang.cloud.wms.common.dto.userService.user.JwtUserInfoDto;
import com.yang.cloud.wms.common.entity.user.User;
import com.yang.cloud.wms.common.vo.userService.user.LoginVo;
import com.yang.cloud.wms.common.vo.userService.user.RegisterVo;
import com.yang.cloud.wms.core.annotation.WmsController;
import com.yang.cloud.wms.common.result.Result;
import com.yang.cloud.wms.user.contextHolder.UserContextHolder;
import com.yang.cloud.wms.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@WmsController("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@RequestBody RegisterVo registerVo) {
        userService.register(registerVo);
        return Result.success();
    }

    @PostMapping("/login")
    public Result<JwtUserInfoDto> login(@RequestBody LoginVo loginVo) {
        return userService.login(loginVo);
    }

    @GetMapping("/getUserInfoByJwt")
    public Result<JwtUserInfoDto> getUserInfoByJwt(@RequestParam("jwt") String jwt) {
        return userService.getUserInfoByJwt(jwt);
    }

    @GetMapping("/detail/{id}")
    public Result<User> userDetail(@PathVariable("id") Long id) {
        System.out.println("userId:"+UserContextHolder.getUserId());
        return Result.success(userService.userDetail(id));
    }
}
