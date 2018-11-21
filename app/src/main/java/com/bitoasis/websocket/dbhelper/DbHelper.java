package com.bitoasis.websocket.dbhelper;

import android.arch.persistence.room.Room;
import android.content.Context;

public class DbHelper {

    private static final String DATABASE_NAME = "websocket_db";
    private static AppDatabase appDatabase;

    private DbHelper() {
    }

    public static AppDatabase getAppDatabase(Context context) {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
        }
        return appDatabase;
    }

    public static void closeDB() {
        if (appDatabase != null) appDatabase.close();
    }
}
