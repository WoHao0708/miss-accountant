package com.g.miss.accountant.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    public static <T> String toJson(T object){
        ObjectMapper objectMapper = new ObjectMapper();
        String result = "";
        try {
            result = objectMapper.writeValueAsString(object);
        } catch (Exception e){
            System.out.println(e.toString());
        }

        return result;
    }
}
