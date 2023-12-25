package com.yang.cloud.wms.gateway.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yang.cloud.wms.common.result.Result;
import com.yang.cloud.wms.common.result.ResultGatewayCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Order(-1)
@Slf4j
public class GlobalErrorExceptionHandler implements ErrorWebExceptionHandler {

    private static final String GATEWAY_ERROR = "gateway error:{}";

    private ObjectMapper objectMapper = new JacksonConfig().objectMapper();

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        if (response.isCommitted()) {
            return Mono.error(ex);
        }
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return exchange.getResponse().writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            Map<String, String> errors = new HashMap<String, String>() {
                {
                    put("error", ex.getMessage());
                }
            };
            try {
                log.warn(GATEWAY_ERROR,ex);
                return bufferFactory.wrap(objectMapper.writeValueAsBytes(Result.error(ResultGatewayCode.GATEWAY_ERROR, errors)));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return bufferFactory.wrap(new byte[0]);
            }
        }));
    }
}
