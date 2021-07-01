package com.example.cowinvaccinenotifier.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Session.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract SessionDao sessionDao();

}
