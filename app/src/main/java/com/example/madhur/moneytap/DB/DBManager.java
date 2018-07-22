package com.example.madhur.moneytap.DB;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

/**
 * Created by Madhur on 7/21/18.
 */

public class DBManager {
    private  DataBaseHelper databaseHelper = null;

    public DataBaseHelper getHelper(Context context){
        if(databaseHelper == null){
            databaseHelper = OpenHelperManager.getHelper(context, DataBaseHelper.class);
        }
        return databaseHelper;
    }

    public void releaseHelper(DataBaseHelper helper)
    {
        if(databaseHelper != null){
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
}
