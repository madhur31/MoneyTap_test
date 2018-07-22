package com.example.madhur.moneytap.datahandler;

import android.util.Log;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import java.lang.reflect.Type;

abstract public class BaseDataHandler<T> {

    protected Response.ErrorListener errorListener;
    protected Response.Listener<T> listner;

    protected Request request;

    Type ctype;

    public BaseDataHandler() {
        try{
            errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error.networkResponse != null) {
                        Log.d("BASEVDATAHANDELR", error.toString() + error.networkResponse.statusCode);
                        errorReceived(error.networkResponse.statusCode, -1, "Something Went Wrong");
                    }
                    if (error instanceof TimeoutError) {
                        errorReceived(504, -1, "Request Timesout!");
                    }
                    else if(error instanceof NoConnectionError){
                        errorReceived(-1, -1, "Something wen Wrong");
                    }
                    else{

                    }
                }
            };
            listner = new Response.Listener<T>() {
                @Override
                public void onResponse(T response) {
                    Log.d("RESPONSE", "In on Response");
                    resultReceived(response , false);
                }
            };
        }catch (AssertionError ex) {
        } catch (IllegalStateException e2) {
        } catch (Exception e) {
        }
    }

    abstract public void  resultReceived(T response, boolean fromDB);

    abstract public void errorReceived(int responseCode, int errorCode, String errorMessage);
}
