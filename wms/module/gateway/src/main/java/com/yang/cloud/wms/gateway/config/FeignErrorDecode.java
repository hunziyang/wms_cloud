package com.yang.cloud.wms.gateway.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yang.cloud.wms.common.exception.FeignClientException;
import com.yang.cloud.wms.common.result.Result;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;

public class FeignErrorDecode implements ErrorDecoder {

    private static final ObjectMapper objectMapper = new JacksonConfig().objectMapper();
    @Override
    public Exception decode(String s, Response response) {
        try {
            Result result = objectMapper.readValue(response.body().asInputStream(), Result.class);
            return new FeignClientException(result.getErrors());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
