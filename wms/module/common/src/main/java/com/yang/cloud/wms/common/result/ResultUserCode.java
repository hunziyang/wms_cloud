package com.yang.cloud.wms.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultUserCode implements ResultCodeBase {
    AUTHENTICATION_ERROR(20000, "认证错误"),
    USER_NOT_EXISTS(20001, "用户不存在"),
    USER_LOCKED(20002, "用户锁定"),
    USER_BAD_CREDENTIALS(20003, "密码错误");

    private Integer code;
    private String msg;
}
