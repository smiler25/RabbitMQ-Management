package com.smiler.rabbitadministration.base.api;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Map;

public class ApiRequest extends Request<String> {
    private final Response.Listener<String> mListener;
    private final Map<String, String> headers;


    public ApiRequest(int method, String url, Map<String, String> headers,
                      Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
        this.headers = headers;
    }

    public ApiRequest(String url, Map<String, String> headers,
                      Response.Listener<String> listener, Response.ErrorListener errorListener) {
        this(Method.GET, url, headers, listener, errorListener);
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
