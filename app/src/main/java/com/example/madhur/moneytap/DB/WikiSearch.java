package com.example.madhur.moneytap.DB;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "wikisearch")
public class WikiSearch {

    @DatabaseField(id=true, columnName = "md5")
    private String md5;
    @DatabaseField(columnName = "query")
    private String query;
    @DatabaseField(columnName = "time")
    private long time;
    @DatabaseField(dataType = DataType.BYTE_ARRAY, columnName = "response")
    private byte[] response;

    public WikiSearch(){

    }

    public WikiSearch(String md5, String query, long time, byte[] response) {
        this.md5 = md5;
        this.time = time;
        this.response = response;
        this.query = query;
    }

    public String getMd5() {
        return md5;
    }

    public long getTime() {
        return time;
    }

    public byte[] getResponse() {
        return response;
    }

    public String getQuery() {
        return query;
    }
}
