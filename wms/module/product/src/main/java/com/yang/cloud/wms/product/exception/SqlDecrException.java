package com.yang.cloud.wms.product.exception;

import com.yang.cloud.wms.common.exception.BaseException;
import com.yang.cloud.wms.common.result.ResultProductCode;

import java.util.Map;

public class SqlDecrException extends BaseException {
    public SqlDecrException() {
        super(ResultProductCode.SQL_OPERATION_ERROR);
    }

    public SqlDecrException(Map<String, ?> errors) {
        super(ResultProductCode.SQL_OPERATION_ERROR, errors);
    }
}
