package com.example.cowinvaccinenotifier.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cowinvaccinenotifier.network.CowinApiService;
import com.example.cowinvaccinenotifier.network.RetrofitClientInstance;
import com.example.cowinvaccinenotifier.network.properties.SessionClass;
import com.example.cowinvaccinenotifier.network.properties.Sessions;

import java.util.List;

import kotlin.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }

    private void faltu()
    {
        CowinApiService service = RetrofitClientInstance
                .getRetrofitInstance()
                .create(CowinApiService.class);

        Call<SessionClass> call = service.getSessions("396210", "04-07-2021");

        call.enqueue(new Callback<SessionClass>() {
            @Override
            public void onResponse(Call<SessionClass> call, Response<SessionClass> response) {
                Log.i("asklfjla", "on Response is this"+response.body().getSessions());
                changeText(response.body().getSessions());
            }

            @Override
            public void onFailure(Call<SessionClass> call, Throwable t) {
                Log.i("asklfjla", "on Failure is this "+t.getMessage());
            }
        });
    }

    private void changeText(List<Sessions> s) {

        mText.setValue(s.get(0).getAddress());
    }
}