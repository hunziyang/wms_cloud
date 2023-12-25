package com.yang.cloud.wms.common.dto.userService.user;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class JwtUserInfoDto {

    private Long id;
    private String username;
    private String name;
    private String phone;
    private List<String> permissions;
    private String jwtToken;
}
