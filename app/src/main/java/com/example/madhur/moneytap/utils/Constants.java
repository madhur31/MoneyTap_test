package com.example.madhur.moneytap.utils;

public class Constants {

    public static final String SEARCH_URL = "https://en.wikipedia.org/w/api.php?action=query&format=" +
            "json&prop=pageimages%7Cpageterms&generator=prefixsearch&formatversion=2&piprop=thumbnail" +
            "&pithumbsize=50&pilimit=10&wbptterms=description&gpslimit=10";

    public static final String ERROR_STRING = "Something went wrong";


    public static final long SEARCH_TIMEOUT = 10 * 60;
}
