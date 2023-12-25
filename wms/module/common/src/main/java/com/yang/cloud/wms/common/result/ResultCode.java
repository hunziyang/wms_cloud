package com.yang.cloud.wms.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode implements ResultCodeBase{
    SUCCESS(200,"Success"),
    BAD_REQUEST(400, "Bad Request"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    FEIGN_ERROR(600, "Feign Error");

    private Integer code;
    private String msg;
}
