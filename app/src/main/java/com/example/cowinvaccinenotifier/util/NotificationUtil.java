package com.example.cowinvaccinenotifier.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import com.example.cowinvaccinenotifier.R;

public class NotificationUtil{

    private static int NOTIFICATION_ID = 0;
    private int REQUEST_CODE = 0;


    public static void sendNotification(String messageBody, Context context)
        {
        Intent contentIntent = new Intent(Intent.ACTION_VIEW);
        contentIntent.setData(Uri.parse("https://selfregistration.cowin.gov.in/"));

            PendingIntent contentPendingIntent = PendingIntent.getActivity(
                    context,
                    NOTIFICATION_ID,
                    contentIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT

            );

            NotificationCompat.Builder builder = new NotificationCompat.Builder(
                    context,
                    context.getString(R.string.cowin_service_notification_channel_id)
            );
            builder.setContentTitle("Vaccine slots found")
            .setContentText(messageBody)
            .setSmallIcon(R.drawable.ic_baseline_done_24)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(contentPendingIntent);



            NotificationManager manager = ContextCompat.getSystemService(
                    context,
                    NotificationManager.class
            );

            String channelId = "Your_channel_id";
            NotificationChannel channel = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                channel = new NotificationChannel(
                        channelId,
                        "Channel human readable title",
                        NotificationManager.IMPORTANCE_HIGH);

                manager.createNotificationChannel(channel);
                builder.setChannelId(channelId);
            }



            manager.notify(NOTIFICATION_ID, builder.build());
        }




}
