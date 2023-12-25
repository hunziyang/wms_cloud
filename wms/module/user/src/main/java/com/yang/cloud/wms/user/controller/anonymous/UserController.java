package com.yang.cloud.wms.user.controller.anonymous;

import com.yang.cloud.wms.common.dto.userService.user.JwtUserInfoDto;
import com.yang.cloud.wms.common.result.Result;
import com.yang.cloud.wms.common.vo.userService.user.JwtVo;
import com.yang.cloud.wms.common.vo.userService.user.LoginVo;
import com.yang.cloud.wms.common.vo.userService.user.RegisterVo;
import com.yang.cloud.wms.core.annotation.AnonymousController;
import com.yang.cloud.wms.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@AnonymousController("/user")
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

    @PostMapping("/getUserInfoByJwt")
    public Result<JwtUserInfoDto> getUserInfoByJwt(@RequestBody JwtVo jwtVo) {
        return userService.getUserInfoByJwt(jwtVo.getJwt());
    }
}
