package com.example.madhur.moneytap.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchPages {

    @SerializedName("pageid")
    private int pageid;

    @SerializedName("title")
    private String pageTitle;

    @SerializedName("thumbnail")
    private ImageThumbnail thumbnail;

    @SerializedName("terms")
    private Description terms;

    public int getPageid() {
        return pageid;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public String getTerms() {
        if (terms != null && terms.description != null && terms.description.size() > 0) {
            return terms.description.get(0);
        }
        return "";
    }

    public String getThumbnail() {
        if (thumbnail != null) {
            return thumbnail.source;
        }
        return "";
    }

    private class ImageThumbnail {
        @SerializedName("source")
        private String source;
    }

    private class Description {
        @SerializedName("description")
        private List<String> description;
    }
}
