package com.yang.cloud.wms.user.contextHolder;

import lombok.Data;

@Data
public class UserContextHolder {

    private static final ThreadLocal<String> USERNAME = new ThreadLocal<>();
    private static final ThreadLocal<Long> USER_ID = new ThreadLocal<>();
    private static final ThreadLocal<String> JWT = new ThreadLocal<>();

    public static void setUsername(String username) {
        USERNAME.set(username);
    }

    public static String getUsername() {
        return USERNAME.get();
    }

    public static void setUserId(Long userId) {
        USER_ID.set(userId);
    }

    public static Long getUserId() {
        return USER_ID.get();
    }

    public static void setJwt(String jwt) {
        JWT.set(jwt);
    }

    public static String getJwt() {
        return JWT.get();
    }

    public static void close() {
        USERNAME.remove();
        USER_ID.remove();
        JWT.remove();
    }
}
