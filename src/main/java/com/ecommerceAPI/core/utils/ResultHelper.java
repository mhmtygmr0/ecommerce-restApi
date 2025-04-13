package com.ecommerceAPI.core.utils;

import org.springframework.http.HttpStatus;

import java.util.LinkedHashMap;
import java.util.Map;

public class ResultHelper {

    public static <T> Map<String, Object> created(T data) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("success", true);
        response.put("message", Msg.CREATED);
        response.put("status", HttpStatus.CREATED.value());
        response.put("data", data);
        return response;
    }

    public static <T> Map<String, Object> success(T data) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("success", true);
        response.put("message", Msg.SUCCESS);
        response.put("status", HttpStatus.OK.value());
        response.put("data", data);
        return response;
    }

    public static Map<String, Object> ok() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("success", true);
        response.put("message", Msg.SUCCESS);
        response.put("status", HttpStatus.OK.value());
        return response;
    }
}
