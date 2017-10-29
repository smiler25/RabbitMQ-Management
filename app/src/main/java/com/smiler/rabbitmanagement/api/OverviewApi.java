package com.smiler.rabbitmanagement.api;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smiler.rabbitmanagement.ManagementApplication;
import com.smiler.rabbitmanagement.R;
import com.smiler.rabbitmanagement.VolleyClient;
import com.smiler.rabbitmanagement.overview.Overview;
import com.smiler.rabbitmanagement.profiles.Profile;

import java.io.IOException;

public class OverviewApi {
    public interface OverviewApiCallback {
        void onResult(Overview result);
        void onError(String msg);
    }

    public static void getInfo(ManagementApplication context, final OverviewApiCallback callback) {
        Profile profile = context.getProfile();
        if (profile == null) {
            callback.onError(context.getString(R.string.profile_null));
            return;
        }

        ApiRequest request = new ApiRequest(Request.Method.GET, profile.getHost() + RequestPath.OVERVIEW, BaseApi.getHeaders(profile.getAuthKey()),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        final ObjectMapper mapper = new ObjectMapper();
                        try {
                            callback.onResult(mapper.readValue(response, Overview.class));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error.toString());
            }
        });

        VolleyClient.getInstance(context).addToRequestQueue(request);
    }
}