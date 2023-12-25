package com.yang.cloud.wms.common.vo.userService.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
public class RegisterVo {

    @NotBlank(message = "username 不能为空")
    private String username;

    @NotBlank(message = "password 不能为空")
    private String password;

    @NotBlank(message = "name 不能为空")
    private String name;

    private String phone;
    private Boolean gender;
    private LocalDate birthday;
}
