package com.example.madhur.moneytap.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SearchResultResponse {

    @SerializedName("continue")
    public ContinueResponse continueResponse;

    @SerializedName("query")
    private SearchQuery query;

    public ContinueResponse getContinueResponse() {
        return continueResponse;
    }

    public List<SearchPages> getQuery() {
        if(query != null) {
            return query.getPages();
        }
        return new ArrayList<>();
    }

    public List<String> getSuggestions() {
        List<SearchPages> pages = getQuery();
        if(pages != null) {
            List<String> suggestions = new ArrayList<>();
            for (SearchPages page : pages) {
                suggestions.add(page.getPageTitle());
            }
            return suggestions;
        }
        return null;
    }
}
