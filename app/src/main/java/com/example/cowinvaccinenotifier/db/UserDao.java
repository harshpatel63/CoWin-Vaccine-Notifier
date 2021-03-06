package com.example.cowinvaccinenotifier.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM User")
    User getAllSessions();

    @Query("SELECT COUNT(uid) FROM User")
    int getCount();

}
