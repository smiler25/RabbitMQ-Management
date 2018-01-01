package com.smiler.rabbitadministration.base.api;

import android.os.AsyncTask;
import android.widget.Toast;

import com.android.volley.Request;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.smiler.rabbitadministration.ManagementApplication;
import com.smiler.rabbitadministration.R;
import com.smiler.rabbitadministration.VolleyClient;
import com.smiler.rabbitadministration.base.AsyncTaskResult;
import com.smiler.rabbitadministration.profiles.Profile;

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

    public static <T> void getObject(ManagementApplication context, final ApiCallback<T> callback,
                                     String path, final Class<T> classType) {
        Profile profile = context.getProfile();
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

    public static <T> void getList(ManagementApplication context, final ApiCallback<ArrayList<T>> callback,
                                   String path, final Class<T> classType) {
        Profile profile = context.getProfile();
        if (profile == null) {
            callback.onError(context.getString(R.string.profile_null));
            return;
        }
        if (profile.getAuthKey().isEmpty()) {
            Toast.makeText(context, R.string.credentials_not_specified, Toast.LENGTH_LONG).show();
            callback.onError(context.getString(R.string.credentials_not_specified));
            return;
        }

        ApiRequest request = new ApiRequest(Request.Method.GET, profile.getHost() + path, BaseApi.getHeaders(profile.getAuthKey()),
                response -> new PrepareListTask<T>(callback, classType).execute(response), error -> callback.onError(error.toString()));

        VolleyClient.getInstance(context).addToRequestQueue(request);
    }

    public static <T> void delete(ManagementApplication context, final ApiCallback<Boolean> callback,
                                  String path, final Class<T> classType) {
        Profile profile = context.getProfile();
        if (profile == null) {
            callback.onError(context.getString(R.string.profile_null));
            return;
        }

        ApiRequest request = new ApiRequest(Request.Method.DELETE, profile.getHost() + path, BaseApi.getHeaders(profile.getAuthKey()),
                response -> callback.onResult(true),
                error -> callback.onError(error.toString()));

        VolleyClient.getInstance(context).addToRequestQueue(request);
    }


    static class PrepareListTask<T> extends AsyncTask<String, Void, AsyncTaskResult> {
        ApiCallback<ArrayList<T>> callback;
        Class<T> classType;

        PrepareListTask(ApiCallback<ArrayList<T>> callback, Class<T> classType) {
            this.callback = callback;
            this.classType = classType;
        }

        @Override
        protected final AsyncTaskResult doInBackground(String... data) {
            try {
                return new AsyncTaskResult<>((ArrayList<T>) new ObjectMapper().readValue(data[0], TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, classType)));
            } catch (IOException e) {
                return new AsyncTaskResult(e);
            }
        }
        @Override
        protected void onPostExecute(AsyncTaskResult result) {
            super.onPostExecute(result);
            if (result.getResult() != null ) {
                callback.onResult((ArrayList<T>) result.getResult());
            } else if (result.getError() != null) {
//                e.printStackTrace();
                callback.onError(result.getError().toString());
            }
        }
    }
}