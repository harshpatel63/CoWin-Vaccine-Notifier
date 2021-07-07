package com.example.cowinvaccinenotifier.ui.home;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cowinvaccinenotifier.network.CowinApiService;
import com.example.cowinvaccinenotifier.network.RetrofitClientInstance;
import com.example.cowinvaccinenotifier.network.properties.SessionClass;
import com.example.cowinvaccinenotifier.network.properties.Sessions;
import com.example.cowinvaccinenotifier.repository.MainRepository;

import java.util.List;

import kotlin.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<List<Sessions>> sessionsData;

    private MutableLiveData<Boolean> nullDataEvent;

    private MainRepository mainRepository;

    public HomeViewModel(Application application) {
        super(application);
        mainRepository = new MainRepository(application);
        sessionsData = new MutableLiveData<>();
        nullDataEvent = new MutableLiveData<>();
        nullDataEvent.setValue(false);
        sessionsData = mainRepository.getSessionsFromNetwork();

        if(sessionsData == null)
            nullDataEvent.setValue(true);

    }

    public LiveData<List<Sessions>> getSessionsData() { return sessionsData; }

    public LiveData<Boolean> getNullDataEvent() { return nullDataEvent; }

    public void updateData()
    {
        sessionsData = new MutableLiveData<>();
        sessionsData = mainRepository.getSessionsFromNetwork();
    }
}