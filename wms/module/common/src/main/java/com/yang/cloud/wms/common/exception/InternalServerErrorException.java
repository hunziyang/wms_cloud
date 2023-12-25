package com.yang.cloud.wms.common.exception;


import com.yang.cloud.wms.common.result.ResultCode;

import java.util.Map;

public class InternalServerErrorException extends BaseException {
    public InternalServerErrorException() {
        super(ResultCode.INTERNAL_SERVER_ERROR);
    }

    public InternalServerErrorException(Map<String, ?> errors) {
        super(ResultCode.INTERNAL_SERVER_ERROR, errors);
    }
}
