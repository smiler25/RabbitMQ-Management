package com.smiler.rabbitmanagement.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smiler.rabbitmanagement.VolleyClient;
import com.smiler.rabbitmanagement.overview.Overview;

import java.io.IOException;

public class OverviewApi {
    public static void getInfo(Context context) {
        String domain = "http://89.108.84.67:15672";

        ApiRequest stringRequest = new ApiRequest(Request.Method.GET, domain + RequestPath.OVERVIEW, BaseApi.getHeaders(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        final ObjectMapper mapper = new ObjectMapper();
                        try {
                            System.out.println("Overview = " + mapper.readValue(response, Overview.class));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error " + error);
            }
        });

        VolleyClient.getInstance(context).addToRequestQueue(stringRequest);
    }
}