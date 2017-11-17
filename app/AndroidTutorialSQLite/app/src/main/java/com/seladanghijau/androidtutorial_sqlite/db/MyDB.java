package com.seladanghijau.androidtutorial_sqlite.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.seladanghijau.androidtutorial_sqlite.dao.UserDAO;
import com.seladanghijau.androidtutorial_sqlite.model.User;


@Database(entities = {User.class}, version = 1)
public abstract class MyDB extends RoomDatabase {
    public abstract UserDAO userDAO();
}
