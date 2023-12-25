package com.yang.cloud.wms.user.service;

import com.yang.cloud.wms.common.dto.userService.user.JwtUserInfoDto;
import com.yang.cloud.wms.common.entity.user.User;
import com.yang.cloud.wms.common.result.Result;
import com.yang.cloud.wms.common.vo.userService.user.LoginVo;
import com.yang.cloud.wms.common.vo.userService.user.RegisterVo;

public interface UserService {
    void register(RegisterVo registerVo);

    Result<JwtUserInfoDto> login(LoginVo loginVo);

    User userDetail(Long id);

    Result<JwtUserInfoDto> getUserInfoByJwt(String jwt);
}
