package com.example.madhur.moneytap.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchQuery {

    @SerializedName("pages")
    private List<SearchPages> pages;

    public List<SearchPages> getPages() {
        return pages;
    }
}
