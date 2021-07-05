package com.example.cowinvaccinenotifier.repository;


import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.cowinvaccinenotifier.db.AppDatabase;
import com.example.cowinvaccinenotifier.db.User;
import com.example.cowinvaccinenotifier.db.UserDao;
import com.example.cowinvaccinenotifier.network.CowinApiService;
import com.example.cowinvaccinenotifier.network.RetrofitClientInstance;

public class MainRepository {




    private UserDao userDao;
    private CowinApiService networkService;

    public MainRepository(Application application)
    {
        //Setup for Database DAO
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();

        //Setup for API call
        networkService = RetrofitClientInstance
                .getRetrofitInstance()
                .create(CowinApiService.class);
    }

    public void changeUserData(int p, String n)
    {

        AppDatabase.databaseWriteExecutor.execute(() -> {
            User newUser = new User(p, n);
            Log.i("asdgadsg", ""+userDao.getCount());
            if(userDao.getCount() > 0) {
                User oldUser = userDao.getAllSessions();
                userDao.delete(oldUser);
            }
            userDao.insert(newUser);
        });


    }

    public User getUserFromDb()
    {
        return userDao.getAllSessions();
    }





}
