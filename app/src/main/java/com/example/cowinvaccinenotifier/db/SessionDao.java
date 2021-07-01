package com.example.cowinvaccinenotifier.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SessionDao {

    @Insert
    void insert(Session session);

    @Delete
    void delete(Session session);

    @Query("SELECT * FROM session")
    List<Session> getAllSessions();


}
