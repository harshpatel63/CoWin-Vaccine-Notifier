package com.example.cowinvaccinenotifier.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.MutableLiveData;
import com.example.cowinvaccinenotifier.ui.MainActivity;
import com.example.cowinvaccinenotifier.R;
import com.example.cowinvaccinenotifier.network.properties.Sessions;
import com.example.cowinvaccinenotifier.repository.MainRepository;
import com.example.cowinvaccinenotifier.util.NotificationUtil;
import java.util.List;

public class TrackingService extends Service {

    final static String NOTIFICATION_CHANNEL_ID = "tracking_channel";
    final static String NOTIFICATION_CHANNEL_NAME = "Tracking";
    final int NOTIFICATION_ID = 1;

    public static MutableLiveData<Boolean> isTracking = new MutableLiveData<>();
    public static MutableLiveData<Boolean> stopService = new MutableLiveData<>();

    private MainRepository mainRepository;

    private Handler handler;

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

        Intent stopServiceIntent = new Intent(getApplication(), StopReceiver.class);
        PendingIntent stopPendingIntent = PendingIntent.getBroadcast(
                this,
                1,
                stopServiceIntent,
                0
        );

        Notification notification = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setContentTitle("CoWin Vaccine Notifier")
                .setContentText("Vaccine Tracking is on...")
                .setSmallIcon(R.drawable.ic_baseline_all_inclusive_24)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.ic_baseline_all_inclusive_24,
                        "Stop",
                        stopPendingIntent
                        )
                .build();
        startForeground(NOTIFICATION_ID, notification);
        isTracking.setValue(true);
        mainRepository = new MainRepository(getApplication());
        startNetworkCalls(mainRepository);

        return super.onStartCommand(intent, flags, startId);

    }

    private void killService()
    {
        getApplication().stopService(new Intent(this, TrackingService.class));
        isTracking.setValue(false);
        stopForeground(true);
        Log.i("kill Service", "inside kill service");
        stopSelf();
    }

    @Override
    public boolean stopService(Intent name) {
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.cancelAll();
        isTracking.setValue(false);
        stopForeground(true);
        stopSelf();
        return super.stopService(name);
    }

    private void startNetworkCalls(MainRepository repository)
    {
        final int delay = 4000; // 1000 milliseconds == 1 second
        handler = new Handler();
        handler.postDelayed(new Runnable() {

            public void run() {

                if(stopService.getValue())
                {
                    killService();
                    handler.removeCallbacksAndMessages(this);
                }

                if(isTracking.getValue()){
                    Log.i("refresher", "hi");

                    List<Sessions> data = mainRepository.getListOfSessionsFromNetwork();

                    if (data != null) {
                        int availableDoses = 0;

                        for (int i = 0; i < data.size(); i++) {
                            availableDoses += data.get(i).getAvailableCapacity();
                        }

                        Log.i("available doses", "" + availableDoses);
                        if (availableDoses > 0)
                            NotificationUtil.sendNotification("Hey, " + availableDoses + " vaccines are available in your area", getApplicationContext());
                    } else
                        Log.i("available doses", "data is null");
                    handler.postDelayed(this, delay);
                }
                else
                    handler.removeCallbacksAndMessages(this);
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
        Log.i("TrackingService", "onCreate called");
        postInitialValues();
    }

    private void postInitialValues() {
        isTracking.setValue(false);
        stopService.setValue(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isTracking.setValue(false);
    }
}
