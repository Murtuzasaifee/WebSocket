package com.bitoasis.websocket.dbhelper;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.bitoasis.websocket.dao.UserDao;
import com.bitoasis.websocket.entity.User;


@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
}

