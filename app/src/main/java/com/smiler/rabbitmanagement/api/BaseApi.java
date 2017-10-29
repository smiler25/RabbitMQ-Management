package com.smiler.rabbitmanagement.api;

import java.util.HashMap;
import java.util.Map;

public class BaseApi {
    public static Map<String, String> getHeaders(final String authKey) {
        return new HashMap<String, String>() {{
            put("Authorization", "Basic " + authKey);
//           put("Content-Type", "application/json");
        }};
    }
}