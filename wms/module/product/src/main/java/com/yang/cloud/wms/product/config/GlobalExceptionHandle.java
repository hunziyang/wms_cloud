package com.yang.cloud.wms.product.config;

import com.yang.cloud.wms.common.exception.BaseException;
import com.yang.cloud.wms.common.exception.InternalServerErrorException;
import com.yang.cloud.wms.common.result.Result;
import com.yang.cloud.wms.common.result.ResultCode;
import com.yang.cloud.wms.product.exception.SqlDecrException;
import com.yang.cloud.wms.product.exception.SqlIncrException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 设置 status 是让 feign 感受到报错
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandle {

    private static final String ERR_MESSAGE = "service err,err message:{}";
    private static final String ERROR = "error";

    @ExceptionHandler(SqlDecrException.class)
    public Result sqlDecrException(SqlDecrException sqlDecrException, HttpServletResponse response) {
        return baseException(sqlDecrException, response);
    }

    @ExceptionHandler(SqlIncrException.class)
    public Result sqlIncrException(SqlIncrException sqlIncrException, HttpServletResponse response) {
        return baseException(sqlIncrException, response);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result methodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletResponse response) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : exception.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return baseException(new BaseException(ResultCode.BAD_REQUEST, errors), response);
    }

    @ExceptionHandler(BindException.class)
    public Result bindException(BindException exception, HttpServletResponse response) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : exception.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return baseException(new BaseException(ResultCode.BAD_REQUEST, errors), response);
    }

    @ExceptionHandler(Exception.class)
    public Result exception(Exception exception, HttpServletResponse response) {
        Map<String, String> errors = new HashMap<>();
        errors.put(ERROR, exception.getMessage());
        return baseException(new BaseException(ResultCode.INTERNAL_SERVER_ERROR, errors), response);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public Result internalServerErrorException(InternalServerErrorException internalServerErrorException, HttpServletResponse response) {
        return baseException(internalServerErrorException, response);
    }

    @ExceptionHandler(BaseException.class)
    public Result baseException(BaseException baseException, HttpServletResponse response) {
        log.warn(ERR_MESSAGE,
                ObjectUtils.isNotEmpty(baseException.getErrors()) ? baseException.getErrors() : baseException.getMessage(),
                baseException);
        response.setStatus(500);
        return Result.error(baseException.getResultCodeBase(), baseException.getErrors());
    }

}
