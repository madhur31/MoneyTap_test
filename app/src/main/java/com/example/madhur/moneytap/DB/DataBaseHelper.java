package com.example.madhur.moneytap.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DataBaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME="moneytap.db";
    private static final int DATABASE_VERSION = 2;

    private Dao<WikiSearch, String> searchDao;
    private RuntimeExceptionDao<WikiSearch, String> searchRuntimeDao;

    public DataBaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        createTable(connectionSource, WikiSearch.class);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            dropTable(connectionSource, WikiSearch.class);
            onCreate(database, connectionSource);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Dao<WikiSearch, String> getSearchDao() throws SQLException {
        if(searchDao == null){
            searchDao = getDao(WikiSearch.class);
        }
        return searchDao;
    }

    public RuntimeExceptionDao<WikiSearch, String> getSearchRuntimeDao() {
        if(searchRuntimeDao == null) {
            searchRuntimeDao = getRuntimeExceptionDao(WikiSearch.class);
        }
        return searchRuntimeDao;
    }

    private void createTable(ConnectionSource connectionSource, Class classType){
        try{
            TableUtils.createTable(connectionSource, classType);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        super.close();
        searchDao = null;
    }

    private void dropTable(ConnectionSource connectionSource, Class classType){
        try{
            TableUtils.dropTable(connectionSource, classType, true);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
