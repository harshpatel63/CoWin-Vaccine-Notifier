package com.example.cowinvaccinenotifier.ui.home;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cowinvaccinenotifier.network.CowinApiService;
import com.example.cowinvaccinenotifier.network.RetrofitClientInstance;
import com.example.cowinvaccinenotifier.network.properties.SessionClass;
import com.example.cowinvaccinenotifier.network.properties.Sessions;
import com.example.cowinvaccinenotifier.repository.MainRepository;
import com.example.cowinvaccinenotifier.service.TrackingService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import kotlin.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<List<Sessions>> sessionsData;

    private MutableLiveData<Boolean> nullDataEvent;

    private MutableLiveData<String> pincodeFromDb;

    private MutableLiveData<String> dateFromDevice;

    private MainRepository mainRepository;

    public HomeViewModel(Application application) {
        super(application);
        mainRepository = new MainRepository(application);
        sessionsData = new MutableLiveData<>();
        nullDataEvent = new MutableLiveData<>();
        pincodeFromDb = new MutableLiveData<>();
        dateFromDevice = new MutableLiveData<>();
        nullDataEvent.setValue(false);
        pincodeFromDb.setValue(mainRepository.getPincodeFromDb());
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        dateFromDevice.setValue(df.format(Calendar.getInstance().getTime()));
        sessionsData = mainRepository.getSessionsFromNetwork();

        if(sessionsData == null)
            nullDataEvent.setValue(true);
        else
            nullDataEvent.setValue(false);

    }

    public LiveData<List<Sessions>> getSessionsData() { return sessionsData; }

    public LiveData<Boolean> getNullDataEvent() { return nullDataEvent; }

    public LiveData<String> getPincodeFromDb() { return pincodeFromDb; }

    public LiveData<String> getDateFromDevice() { return dateFromDevice; }

    public void updateData()
    {
        Log.i("hga", "slfj");
        sessionsData = mainRepository.getSessionsFromNetwork();
    }
}