package com.yang.cloud.wms.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultGatewayCode implements ResultCodeBase {

    GATEWAY_ERROR(10000,"Gateway Error"),
    AUTHENTICATION_ERROR(20000, "认证错误"),
    ACCESS_DENIED(20004, "无权限访问");

    private Integer code;
    private String msg;
}
