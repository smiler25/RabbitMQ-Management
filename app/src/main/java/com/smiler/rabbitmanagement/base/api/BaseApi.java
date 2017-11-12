package com.smiler.rabbitmanagement.base.api;

import com.android.volley.Request;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.smiler.rabbitmanagement.ManagementApplication;
import com.smiler.rabbitmanagement.R;
import com.smiler.rabbitmanagement.VolleyClient;
import com.smiler.rabbitmanagement.profiles.ActiveProfile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BaseApi {
    public interface ApiCallback<T> {
        void onResult(T result);
        void onError(String msg);
    }

    private static Map<String, String> getHeaders(final String authKey) {
        return new HashMap<String, String>() {{
            put("Authorization", "Basic " + authKey);
//           put("Content-Type", "application/json");
        }};
    }

    public static <T> void requestObject(ManagementApplication context, final ApiCallback<T> callback,
                                         String path, final Class<T> classType) {
        ActiveProfile profile = context.getProfile();
        if (profile == null) {
            callback.onError(context.getString(R.string.profile_null));
            return;
        }

        ApiRequest request = new ApiRequest(Request.Method.GET, profile.getHost() + path, BaseApi.getHeaders(profile.getAuthKey()),
                response -> {
                    try {
                        callback.onResult(new ObjectMapper().readValue(response, classType));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }, error -> callback.onError(error.toString()));

        VolleyClient.getInstance(context).addToRequestQueue(request);
    }

    public static <T> void requestList(ManagementApplication context, final ApiCallback<ArrayList<T>> callback,
                                       String path, final Class<T> classType) {
        ActiveProfile profile = context.getProfile();
        if (profile == null) {
            callback.onError(context.getString(R.string.profile_null));
            return;
        }

        ApiRequest request = new ApiRequest(Request.Method.GET, profile.getHost() + path, BaseApi.getHeaders(profile.getAuthKey()),
                response -> {
                    try {
                        callback.onResult((ArrayList<T>) new ObjectMapper().readValue(response, TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, classType)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }, error -> callback.onError(error.toString()));

        VolleyClient.getInstance(context).addToRequestQueue(request);
    }
}