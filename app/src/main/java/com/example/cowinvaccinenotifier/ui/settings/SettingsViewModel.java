package com.example.cowinvaccinenotifier.ui.settings;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.cowinvaccinenotifier.repository.MainRepository;
import java.util.ArrayList;

public class SettingsViewModel extends AndroidViewModel {

    private MainRepository mainRepository;

    private MutableLiveData<String> pincodeDb;
    private MutableLiveData<String> usernameDb;
    private MutableLiveData<ArrayList<String>> vaccineList;
    private MutableLiveData<ArrayList<String>> ageList;
    private MutableLiveData<ArrayList<String>> feeList;

    public SettingsViewModel(Application application) {
        super(application);
        mainRepository = new MainRepository(application);
        pincodeDb = new MutableLiveData<>();
        usernameDb = new MutableLiveData<>();
        vaccineList = new MutableLiveData<>();
        ageList = new MutableLiveData<>();
        feeList = new MutableLiveData<>();
        pincodeDb.setValue(mainRepository.getPincodeFromDb());
        usernameDb.setValue(mainRepository.getUsernameFromDb());
        vaccineList.setValue(mainRepository.getVaccineListFromDb());
        ageList.setValue(mainRepository.getAgeListFromDb());
        feeList.setValue(mainRepository.getFeesListFromDb());
    }

    void changeData(int p, String n, ArrayList<String> vl, ArrayList<String> fl, ArrayList<String> al)
    {
        mainRepository.changeUserData(p, n, vl, fl, al);
    }

    Boolean checkPincode(int pin)
    {
        return pin > 0 && pin <= 999999;
    }

    public LiveData<String> getPincodeDb() { return pincodeDb; }
    public LiveData<String> getUsernameDb() { return usernameDb; }
    public LiveData<ArrayList<String>> getVaccineListDb() { return vaccineList; }
    public LiveData<ArrayList<String>> getAgeListDb() { return ageList; }
    public LiveData<ArrayList<String>> getFeeListDb() { return feeList; }


    public boolean checkLists(ArrayList<String> vaccineList, ArrayList<String> ageList, ArrayList<String> feeList) {
        if(vaccineList.isEmpty() || ageList.isEmpty() || feeList.isEmpty())
            return false;
        return true;
    }
}