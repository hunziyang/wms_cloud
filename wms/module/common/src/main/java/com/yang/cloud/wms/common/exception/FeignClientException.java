package com.yang.cloud.wms.common.exception;

import com.yang.cloud.wms.common.result.ResultCode;

import java.util.Map;

public class FeignClientException extends BaseException {
    public FeignClientException() {
        super(ResultCode.FEIGN_ERROR);
    }

    public FeignClientException(Map<String, ?> errors) {
        super(ResultCode.FEIGN_ERROR, errors);
    }
}
