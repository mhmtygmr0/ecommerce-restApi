package com.ecommerceAPI.core.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class ResultHelper {

    public static <T> Map<String, Object> created(T data) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("success", true);
        response.put("message", Msg.CREATED);
        response.put("status", "201");
        response.put("data", data);
        return response;
    }

    public static <T> Map<String, Object> validateError(T data) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("success", false);
        response.put("message", Msg.VALIDATE_ERROR);
        response.put("status", "400");
        response.put("data", data);
        return response;
    }

    public static <T> Map<String, Object> success(T data) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("success", true);
        response.put("message", Msg.SUCCESS);
        response.put("status", "200");
        response.put("data", data);
        return response;
    }

    public static Map<String, Object> ok() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("success", true);
        response.put("message", Msg.SUCCESS);
        response.put("status", "200");
        return response;
    }

    public static Map<String, Object> notFoundError(String msg) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("success", false);
        response.put("message", msg);
        response.put("status", "404");
        return response;
    }

    public static Map<String, Object> forbiddenError(String message) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("success", false);
        response.put("message", message);
        response.put("status", "403");
        return response;
    }

    public static Map<String, Object> businessError(String message) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("success", false);
        response.put("message", message);
        response.put("status", "400");
        return response;
    }
}
