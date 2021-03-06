package com.example.cowinvaccinenotifier.repository;


import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.example.cowinvaccinenotifier.db.AppDatabase;
import com.example.cowinvaccinenotifier.db.User;
import com.example.cowinvaccinenotifier.db.UserDao;
import com.example.cowinvaccinenotifier.network.CowinApiService;
import com.example.cowinvaccinenotifier.network.RetrofitClientInstance;
import com.example.cowinvaccinenotifier.network.properties.SessionClass;
import com.example.cowinvaccinenotifier.network.properties.Sessions;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {

    private UserDao userDao;
    private CowinApiService networkService;

    private List<Sessions> dataSet;

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

    public void changeUserData(int p, String n, ArrayList<String> vl, ArrayList<String> fl, ArrayList<String> al)
    {

        AppDatabase.databaseWriteExecutor.execute(() -> {
            User newUser = new User(p, n, vl, fl, al);
            Log.i("changeUserData", ""+userDao.getCount());
            if(userDao.getCount() > 0) {
                User oldUser = userDao.getAllSessions();
                userDao.delete(oldUser);
            }
            userDao.insert(newUser);
        });
    }

    public List<Sessions> getListOfSessionsFromNetwork()
    {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String date = df.format(Calendar.getInstance().getTime());

        CowinApiService service = RetrofitClientInstance
                .getRetrofitInstance()
                .create(CowinApiService.class);

        Call<SessionClass> call = service.getSessions(getPincodeFromDb(), date);

        call.enqueue(new Callback<SessionClass>() {
            @Override
            public void onResponse(Call<SessionClass> call, Response<SessionClass> response) {
                Log.i("networkRequest","Inside onResponse ");
                if(response.body() != null) {

                    List<Sessions> receivedData = response.body().getSessions();
                    ArrayList<Sessions> filteredData = new ArrayList<>();

                    for(int i = 0;i < receivedData.size();i++)
                    {
                        Sessions curr = receivedData.get(i);
                        if(getAgeListFromDb().contains(curr.getMinAgeLimit().toString()) &&
                                getFeesListFromDb().contains(curr.getFeeType()) &&
                                getVaccineListFromDb().contains(curr.getVaccine()))
                            filteredData.add(curr);
                    }
                    dataSet = filteredData;
                }
            }

            @Override
            public void onFailure(Call<SessionClass> call, Throwable t) {
                Log.i("networkRequest", "on Failure is this "+t.getMessage());
            }
        });
        return dataSet;
    }



    public MutableLiveData<List<Sessions>> getSessionsFromNetwork()
    {

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String date = df.format(Calendar.getInstance().getTime());


        final MutableLiveData<List<Sessions>> data = new MutableLiveData<>();

        CowinApiService service = RetrofitClientInstance
                .getRetrofitInstance()
                .create(CowinApiService.class);

        Call<SessionClass> call = service.getSessions(getPincodeFromDb(), date);

        call.enqueue(new Callback<SessionClass>() {
            @Override
            public void onResponse(Call<SessionClass> call, Response<SessionClass> response) {
                Log.i("networkRequest","Inside onResponse ");
                if(response.body() != null) {

                    List<Sessions> receivedData = response.body().getSessions();
                    ArrayList<Sessions> filteredData = new ArrayList<>();

                    for(int i = 0;i < receivedData.size();i++)
                    {
                        Sessions curr = receivedData.get(i);
                        if(getAgeListFromDb().contains(curr.getMinAgeLimit().toString()) &&
                        getFeesListFromDb().contains(curr.getFeeType()) &&
                        getVaccineListFromDb().contains(curr.getVaccine()))
                            filteredData.add(curr);
                    }
                    data.setValue(filteredData);
                }
            }

            @Override
            public void onFailure(Call<SessionClass> call, Throwable t) {
                Log.i("networkRequest", "on Failure is this "+t.getMessage());
            }
        });
            return data;
    }

    private User getUserFromDb(){
        try {
            return new getUserAsyncTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new User();
    }


    public String getPincodeFromDb()
    {
        if(getUserFromDb()!=null)
            return String.valueOf(getUserFromDb().pincode);
        return "";
    }

    public String getUsernameFromDb()
    {
        if(getUserFromDb()!=null)
            return String.valueOf(getUserFromDb().username);
        return "";
    }

    public ArrayList<String> getVaccineListFromDb()
    {
        if(getUserFromDb()!=null)
            return getUserFromDb().vaccines;
        return null;
    }

    public ArrayList<String> getAgeListFromDb()
    {
        if(getUserFromDb()!=null)
            return getUserFromDb().age;
        return null;
    }

    public ArrayList<String> getFeesListFromDb()
    {
        if(getUserFromDb()!=null)
            return getUserFromDb().fees;
        return null;
    }



private class getUserAsyncTask extends AsyncTask<Void, Void, User>
{

    @Override
    protected User doInBackground(Void... voids) {
        return userDao.getAllSessions();
    }
}

}
