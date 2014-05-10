package com.example.parkumbc;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.google.android.gcm.GCMBaseIntentService;
import storage.ServerResponse;

import java.util.Set;

import static com.example.parkumbc.Constant.*;

public class GCMIntentService extends GCMBaseIntentService {

    static final String TAG = "SERVICE";

    public GCMIntentService() {
        super(SENDER_ID);
    }

    @Override
    protected void onRegistered(Context context, String registrationId) {
        ServerResponse response = Server.register(context, registrationId);
        if (response != null)
            Log.d(TAG, response.getMessage());
    }

    @Override
    protected void onUnregistered(Context context, String registrationId) {
        Server.unregister(context, registrationId);
    }

    @Override
    protected void onMessage(Context context, Intent intent) {
        String parkingLotId = intent.getExtras().getString(PARKING_LOT_ID);
        String parkingLotName = intent.getExtras().getString(PARKING_LOT_NAME);
        generateNotification(context, parkingLotId, parkingLotName);
    }

    @Override
    protected void onError(Context context, String s) {

    }

    private void generateNotification(Context context, String lotId, String lotName) {
        String message = "No parking spaces left in " + lotName;
        String tickerText = lotName + " is full!";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        Set<String> selectedLots = preferences.getStringSet(PARKING_LOTS, null);
        if (selectedLots != null) {
            int icon = R.drawable.ic_launcher;
            long when = System.currentTimeMillis();

            Intent notificationIntent = new Intent(context, MainActivity.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

            Notification notification = new Notification.Builder(context)
                    .setContentTitle(context.getString(R.string.app_name))
                    .setContentText(message)
                    .setSmallIcon(icon)
                    .setWhen(when)
                    .setTicker(tickerText)
                    .setContentIntent(pendingIntent)
                    .build();

            NotificationManager notificationManager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(0, notification);
        }
    }

}
