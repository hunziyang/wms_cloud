package com.yang.cloud.wms.trade.utils;

import com.yang.cloud.wms.core.utils.SpringUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class NumberUtil {

    private static final String NUMBER_PREFIX = "T";
    private static final StringRedisTemplate stringRedisTemplate = SpringUtil.getBean(StringRedisTemplate.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final String LUA_PATH = "/lua/numberSuffix.lua";



    public static String getNumber(){
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.systemDefault());
        String format = zonedDateTime.format(dateTimeFormatter);
        String suffix = numberSuffix(format);
        return String.format("%s%s%s", NUMBER_PREFIX, format, suffix);
    }

    private static String numberSuffix(String format){
        DefaultRedisScript<String> defaultRedisScript = new DefaultRedisScript<>();
        defaultRedisScript.setResultType(String.class);
        defaultRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(LUA_PATH)));
        String num = stringRedisTemplate.execute(defaultRedisScript, Arrays.asList(format));
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = num.length();i<=8;i++){
            stringBuffer.append("0");
        }
        stringBuffer.append(num);
        return stringBuffer.toString();
    }
}
