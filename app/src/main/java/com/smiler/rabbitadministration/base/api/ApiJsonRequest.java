package com.smiler.rabbitadministration.base.api;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Map;

import static com.smiler.rabbitadministration.Constants.REQUEST_BACKOFF_MULT;
import static com.smiler.rabbitadministration.Constants.REQUEST_MAX_RETRIES;
import static com.smiler.rabbitadministration.Constants.REQUEST_TIMEOUT_MS;

public class ApiJsonRequest extends JsonRequest<String> {
    private final Response.Listener<String> mListener;
    private final Map<String, String> headers;

    ApiJsonRequest(int method, String url, Map<String, String> headers, JSONObject body,
                   Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, body.toString(), listener, errorListener);
        mListener = listener;
        this.headers = headers;
        setRetryPolicy(new DefaultRetryPolicy(REQUEST_TIMEOUT_MS, REQUEST_MAX_RETRIES, REQUEST_BACKOFF_MULT));
    }

    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }

    public Map<String, String> getHeaders() throws AuthFailureError {
        if (headers == null) {
            return Collections.emptyMap();
        }
        return headers;
    }

}
