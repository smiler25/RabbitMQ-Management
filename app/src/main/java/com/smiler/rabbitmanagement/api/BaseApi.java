package com.smiler.rabbitmanagement.api;

import android.util.Base64;

import java.util.HashMap;
import java.util.Map;

public class BaseApi {
    public static Map<String, String> getHeaders() {
        String credentials = "guest:guest";
        final String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        return new HashMap<String, String>() {{
            put("Authorization", auth);
//           put("Content-Type", "application/json");
        }};
    }
}