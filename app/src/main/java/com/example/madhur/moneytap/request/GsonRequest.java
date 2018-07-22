package com.example.madhur.moneytap.request;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.madhur.moneytap.MyApplication;
import com.example.madhur.moneytap.response.SearchResultResponse;
import com.google.gson.Gson;

import java.lang.reflect.Type;

abstract public class GsonRequest<T> extends Request<T> {

    private final Type classType;
    private final Response.Listener listner;
    private Gson gson;
    private String url = null;
    private int method;

    public GsonRequest(int method, String url, Response.ErrorListener listener, Type classType, Response.Listener listner) {
        super(method, url, listener);
        this.classType = classType;
        this.listner = listner;
        this.url = url;
        this.method = method;
        gson = MyApplication.getGsonInstance();
        Log.d("URL", url);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        String responseData = new String(response.data);
        Log.d("ParseNetworkResponse", new String(response.data));

        if(response.statusCode != 200) {
            return Response.error(new VolleyError(response));
        }
        if(classType == SearchResultResponse.class) {
            T responseObj = new Gson().fromJson(responseData, classType);
            performJsonUpdate(response.data, responseObj);
            if(responseObj == null) {
                return Response.error(new VolleyError(response));
            }
            return Response.success(responseObj, null);
        }

        return Response.error(new VolleyError(response));
    }

    @Override
    protected void deliverResponse(T response) {
        listner.onResponse(response);
    }

    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);
    }

    public abstract void performJsonUpdate(byte[] responseStream, T response);
}
