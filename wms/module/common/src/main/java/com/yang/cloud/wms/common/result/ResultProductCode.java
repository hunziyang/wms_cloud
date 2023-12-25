package com.yang.cloud.wms.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResultProductCode implements ResultCodeBase {

    SQL_OPERATION_ERROR(30000,"操作数据异常");

    private Integer code;
    private String msg;
}
