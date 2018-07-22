package com.example.madhur.moneytap.request;

import com.android.volley.Response;
import com.example.madhur.moneytap.DB.DBManager;
import com.example.madhur.moneytap.DB.WikiSearch;
import com.example.madhur.moneytap.DB.WikiSearchDAO;
import com.example.madhur.moneytap.MyApplication;
import com.example.madhur.moneytap.response.SearchResultResponse;
import com.example.madhur.moneytap.utils.Utils;
import com.google.gson.reflect.TypeToken;

public class GetSearchResultRequest extends GsonRequest<SearchResultResponse> {
    private String url;
    private final String query;
    private boolean DBEntry;

    public GetSearchResultRequest(String url, Response.ErrorListener errorListener, Response.Listener listener, String query, boolean DBEntry) {
        super(Method.GET, url, errorListener, new TypeToken<SearchResultResponse>() {
        }.getType(), listener);
        this.url = url;
        this.query = query;
        this.DBEntry = DBEntry;
    }

    @Override
    public void performJsonUpdate(final byte[] responseStream, SearchResultResponse response) {
        if (response == null)
            return;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    WikiSearchDAO searchDAO = new WikiSearchDAO(MyApplication.getAppContext());
                    WikiSearch search;
                    search = new WikiSearch(Utils.md5(url), query, Utils.getCurrentLinuxTimeInSeconds(), responseStream);
                    if (search != null) {
                        searchDAO.create(search);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        if(DBEntry) {
            runnable.run();
        }
    }
}
