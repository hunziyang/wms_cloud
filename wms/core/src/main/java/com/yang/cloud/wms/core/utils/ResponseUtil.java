package com.yang.cloud.wms.core.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ResponseUtil {

    public static void setResponse(HttpServletResponse response,String json) throws IOException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.getWriter().write(json);
    }
}
