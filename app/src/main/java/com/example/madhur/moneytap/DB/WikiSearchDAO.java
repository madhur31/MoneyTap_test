package com.example.madhur.moneytap.DB;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

public class WikiSearchDAO {

    private DataBaseHelper db;
    Dao<WikiSearch, String> searchDao;

    public WikiSearchDAO(Context context){
        try{
            DBManager dbManager = new DBManager();
            db = dbManager.getHelper(context);
            searchDao = db.getSearchDao();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public int create(WikiSearch wikiSearch)
    {
        try{
            Dao.CreateOrUpdateStatus status = searchDao.createOrUpdate(wikiSearch);
            return status.getNumLinesChanged();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return  0;
    }

    public int update(WikiSearch wikiSearch)
    {
        try{
            Dao.CreateOrUpdateStatus status = searchDao.createOrUpdate(wikiSearch);
            return status.getNumLinesChanged();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public int delete(WikiSearch wikiSearch)
    {
        try{
            return searchDao.delete(wikiSearch);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public WikiSearch getSearchById(String md5){
        try{
            return searchDao.queryForId(md5);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<WikiSearch> getAll()
    {
        try{
            return searchDao.queryForAll();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public void deleteAll(){
        try{
            searchDao.callBatchTasks(new Callable<Object>() {
                @Override
                public Void call() throws SQLException {
                    List<WikiSearch> sellerEntries = getAll();
                    if(sellerEntries != null){
                        for(int i=0; i<sellerEntries.size(); i++)
                            delete(sellerEntries.get(i));
                    }
                    return null;
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return;
    }
}
