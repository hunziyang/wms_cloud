package com.yang.cloud.wms.common.vo.userService.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginVo {

    @NotBlank(message = "username 不能为空")
    private String username;
    @NotBlank(message = "password 不能为空")
    private String password;
}
