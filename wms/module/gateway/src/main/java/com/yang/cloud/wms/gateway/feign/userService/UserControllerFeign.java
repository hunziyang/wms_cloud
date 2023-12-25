package com.yang.cloud.wms.gateway.feign.userService;

import com.yang.cloud.wms.common.dto.userService.user.JwtUserInfoDto;
import com.yang.cloud.wms.common.result.Result;
import com.yang.cloud.wms.common.vo.userService.user.JwtVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", path = "/user-service/anonymous/user")
public interface UserControllerFeign {

    @PostMapping("/getUserInfoByJwt")
    Result<JwtUserInfoDto> getUserInfoByJwt(@RequestBody JwtVo jwtVo);
}
