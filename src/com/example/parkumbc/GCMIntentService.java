package com.example.parkumbc;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.google.android.gcm.GCMBaseIntentService;
import storage.ServerResponse;

import static com.example.parkumbc.Constant.PARKING_LOT_ID;
import static com.example.parkumbc.Constant.SENDER_ID;

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
        Log.d(TAG, intent.getExtras().getString(PARKING_LOT_ID));
    }

    @Override
    protected void onError(Context context, String s) {

    }

}
