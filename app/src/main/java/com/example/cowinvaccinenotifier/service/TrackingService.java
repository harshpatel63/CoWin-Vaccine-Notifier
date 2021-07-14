package com.example.cowinvaccinenotifier.service;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.cowinvaccinenotifier.MainActivity;
import com.example.cowinvaccinenotifier.R;
import com.example.cowinvaccinenotifier.network.properties.Sessions;
import com.example.cowinvaccinenotifier.repository.MainRepository;

import java.util.List;

public class TrackingService extends Service {

    final static String NOTIFICATION_CHANNEL_ID = "tracking_channel";
    final static String NOTIFICATION_CHANNEL_NAME = "Tracking";
    final int NOTIFICATION_ID = 1;

    private MutableLiveData<List<Sessions>> sessionData;
    private LiveData<List<Sessions>> sessionList;
    private MainRepository mainRepository;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
        }
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                notificationIntent,
                0
                );

        Notification notification = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setContentTitle("CoWin Vaccine Notifier")
                .setContentText("Vaccine Tracking is on...")
                .setSmallIcon(R.drawable.ic_baseline_all_inclusive_24)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(NOTIFICATION_ID, notification);
        startNetworkCalls();

        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public boolean stopService(Intent name) {
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.cancelAll();
        return super.stopService(name);
    }

    private void startNetworkCalls()
    {
        final Handler handler = new Handler();
        final int delay = 4000; // 1000 milliseconds == 1 second

        handler.postDelayed(new Runnable() {
            public void run() {

                Log.i("refresher", "hi");
                handler.postDelayed(this, delay);
            }
        }, delay);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel()
    {
        NotificationChannel channel = new NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
        );
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
