package com.example.cowinvaccinenotifier.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StopReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("StopReceiver", "onReceive is this");
        TrackingService.stopService.setValue(true);
    }
}
