package com.example.cowinvaccinenotifier.ui.settings;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cowinvaccinenotifier.db.User;
import com.example.cowinvaccinenotifier.repository.MainRepository;

import java.util.List;

public class SettingsViewModel extends AndroidViewModel {

    private MainRepository mainRepository;

    private MutableLiveData<String> pincodeDb;
    private MutableLiveData<String> usernameDb;

    public SettingsViewModel(Application application) {
        super(application);
        mainRepository = new MainRepository(application);
        pincodeDb = new MutableLiveData<>();
        usernameDb = new MutableLiveData<>();
        pincodeDb.setValue(mainRepository.getPincodeFromDb());
        usernameDb.setValue(mainRepository.getUsernameFromDb());
    }

    void changeData(int p, String n)
    {
        mainRepository.changeUserData(p, n);
    }

    Boolean checkPincode(int pin)
    {
        return pin > 0 && pin <= 999999;
    }

    public LiveData<String> getPincodeDb() { return pincodeDb; }
    public LiveData<String> getUsernameDb() { return usernameDb; }



}