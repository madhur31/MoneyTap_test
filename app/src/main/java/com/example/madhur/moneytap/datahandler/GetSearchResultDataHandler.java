package com.example.madhur.moneytap.datahandler;

import com.example.madhur.moneytap.DB.WikiSearch;
import com.example.madhur.moneytap.DB.WikiSearchDAO;
import com.example.madhur.moneytap.MyApplication;
import com.example.madhur.moneytap.request.GetSearchResultRequest;
import com.example.madhur.moneytap.response.SearchResultResponse;
import com.example.madhur.moneytap.utils.Constants;
import com.example.madhur.moneytap.utils.Utils;
import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

abstract public class GetSearchResultDataHandler extends BaseDataHandler<SearchResultResponse> {

    String mSearchQuery;
    int mOffset;
    String searchUrl;
    private boolean fetchFromDB;
    private boolean makeDBEntry;

    public GetSearchResultDataHandler(String mSearchQuery, int gpsOffset) {
        this.mSearchQuery = mSearchQuery;
        this.mOffset = gpsOffset;
    }

    public void SearchQuery(boolean fetchFromDB, boolean makeDBEntry) {
        this.fetchFromDB = fetchFromDB;
        this.makeDBEntry = makeDBEntry;
        searchUrl = Constants.SEARCH_URL + "&gpssearch=" + mSearchQuery + "&gpsoffset=" + mOffset;
        SearchResultResponse result = fetchFromDB(searchUrl);
        if(result != null) {
            resultReceivedSearchResult(200, "", result);
        } else {
            GetSearchResultRequest request = new GetSearchResultRequest(searchUrl, errorListener, listner, mSearchQuery, makeDBEntry);
            this.request = request;
            MyApplication.getInstance().addToRequestQueue(request);
        }
    }

    private SearchResultResponse fetchFromDB(String URL) {
        WikiSearchDAO searchDAO = new WikiSearchDAO(MyApplication.getAppContext());
        WikiSearch searchData = searchDAO.getSearchById(Utils.md5(URL));
        SearchResultResponse response = null;
        if (searchData != null) {
            long lTimeout = Constants.SEARCH_TIMEOUT;
            if (!(Utils.getCurrentLinuxTimeInSeconds() - searchData.getTime() > lTimeout) || fetchFromDB) {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(searchData.getResponse());
                Reader jsonReader = new InputStreamReader(inputStream);
                response = new Gson().fromJson(jsonReader, SearchResultResponse.class);
            }
        }
        return response;
    }

    @Override
    public void resultReceived(SearchResultResponse response, boolean fromDB) {
        if(response != null) {
            resultReceivedSearchResult(200, "", response);
        } else {
            resultReceivedSearchResult(-1, Constants.ERROR_STRING, null);
        }
    }

    @Override
    public void errorReceived(int responseCode, int errorCode, String errorMessage) {
        SearchResultResponse result = fetchFromDB(searchUrl);
        if(result != null) {
            resultReceivedSearchResult(200, "", result);
        } else {
            resultReceivedSearchResult(responseCode, errorMessage, null);
        }
    }

     public abstract void resultReceivedSearchResult(int resultCode, String errorCode, SearchResultResponse response);
}
