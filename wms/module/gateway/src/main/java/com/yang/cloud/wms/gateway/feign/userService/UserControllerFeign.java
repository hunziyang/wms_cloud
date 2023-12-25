package com.yang.cloud.wms.gateway.feign.userService;

import com.yang.cloud.wms.common.dto.userService.user.JwtUserInfoDto;
import com.yang.cloud.wms.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service",path = "/user-service/user")
public interface UserControllerFeign {

    @GetMapping("/getUserInfoByJwt")
    public Result<JwtUserInfoDto> getUserInfoByJwt(@RequestParam("jwt") String jwt);
}
