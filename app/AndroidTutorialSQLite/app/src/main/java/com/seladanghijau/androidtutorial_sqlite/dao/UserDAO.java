package com.seladanghijau.androidtutorial_sqlite.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.seladanghijau.androidtutorial_sqlite.model.User;

import java.util.List;


@Dao
public interface UserDAO {
    @Query("SELECT * FROM users")
    List<User> getAll();

    @Query("SELECT * FROM users WHERE id LIKE :id LIMIT 1")
    User findByID(String id);

    @Query("SELECT * FROM users WHERE name LIKE :name LIMIT 1")
    User findByName(String name);

    @Query("SELECT * FROM users WHERE email LIKE :email LIMIT 1")
    User findByEmail(String email);

    @Query("SELECT * FROM users WHERE email LIKE :email AND password LIKE :password")
    User findByEmailAndPassword(String email, String password);

    @Insert
    long insert(User user);
}
