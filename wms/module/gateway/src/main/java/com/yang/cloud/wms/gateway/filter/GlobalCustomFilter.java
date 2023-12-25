package com.yang.cloud.wms.gateway.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yang.cloud.wms.common.dto.userService.user.JwtUserInfoDto;
import com.yang.cloud.wms.common.result.Result;
import com.yang.cloud.wms.common.result.ResultCodeBase;
import com.yang.cloud.wms.common.result.ResultGatewayCode;
import com.yang.cloud.wms.common.vo.userService.user.JwtVo;
import com.yang.cloud.wms.gateway.config.JacksonConfig;
import com.yang.cloud.wms.gateway.feign.userService.UserControllerFeign;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@Order(1)
@Slf4j
public class GlobalCustomFilter implements GlobalFilter {

    @Autowired
    private UserControllerFeign userControllerFeign;

    private ObjectMapper objectMapper = new JacksonConfig().objectMapper();
    private static final String AUTH_HEADER = "Authorization";
    private static final String JWT_ERROR = "Authorization Header Not Exits";
    private static final String JWT_BLANK = "Jwt Blank";
    private static final String NO_PERMISSION = "No Permission";
    private static final String USER_NAME = "USER_NAME";
    private static final String USER_ID = "USER_ID";
    private static final String JWT = "JWT";
    private static final String REQUEST_ID = "REQUEST_ID";
    private static final String ANONYMOUS = "/anonymous";


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String path = request.getPath().value();
        String requestId = UUID.randomUUID().toString();
        if (isAnonymousPath(path)) {
            request.mutate().headers(httpHeaders ->
                    httpHeaders.add(REQUEST_ID, requestId)
            );
            return chain.filter(exchange);
        } else {
            HttpHeaders headers = request.getHeaders();
            if (!headers.containsKey(AUTH_HEADER)) {
                DataBuffer wrap = errMessage(ResultGatewayCode.AUTHENTICATION_ERROR, response, JWT_ERROR);
                return response.writeWith(Mono.just(wrap));
            }
            String jwt = headers.get(AUTH_HEADER).stream().findFirst().orElse("");
            if (StringUtils.isBlank(jwt)) {
                DataBuffer wrap = errMessage(ResultGatewayCode.AUTHENTICATION_ERROR, response, JWT_BLANK);
                return response.writeWith(Mono.just(wrap));
            }
            JwtVo jwtVo = new JwtVo();
            jwtVo.setJwt(jwt);
            JwtUserInfoDto jwtUserInfoDto = userControllerFeign.getUserInfoByJwt(jwtVo).getBody();
            String per = jwtUserInfoDto.getPermissions().stream().filter(permission -> path.startsWith(permission)).findFirst().orElse(null);
            if (StringUtils.isBlank(per)) {
                DataBuffer wrap = errMessage(ResultGatewayCode.ACCESS_DENIED, response, NO_PERMISSION);
                return response.writeWith(Mono.just(wrap));
            }
            setUserHeader(request, jwtUserInfoDto, requestId);
            return chain.filter(exchange);
        }
    }

    private boolean isAnonymousPath(String path) {
        int index = path.indexOf("/", 1);
        return path.substring(index).startsWith(ANONYMOUS);
    }

    @SneakyThrows
    private DataBuffer errMessage(ResultCodeBase resultCodeBase, ServerHttpResponse response, String message) {
        Map<String, String> errors = new HashMap<String, String>() {
            {
                put("errors", message);
            }
        };
        byte[] bytes = objectMapper.writeValueAsBytes(Result.error(resultCodeBase, errors));
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return response.bufferFactory().wrap(bytes);
    }

    private void setUserHeader(ServerHttpRequest request, JwtUserInfoDto jwtUserInfoDto, String requestId) {
        request.mutate().headers(httpHeaders -> {
            httpHeaders.add(USER_ID, String.valueOf(jwtUserInfoDto.getId()));
            httpHeaders.add(USER_NAME, jwtUserInfoDto.getUsername());
            httpHeaders.add(JWT, jwtUserInfoDto.getJwtToken());
            httpHeaders.add(REQUEST_ID, requestId);
        });
    }
}
