package com.example.madhur.moneytap.response;

import com.google.gson.annotations.SerializedName;

public class ContinueResponse {

    @SerializedName("gpsoffset")
    private int gpsoffset;

    public int getGpsoffset() {
        return gpsoffset;
    }
}
