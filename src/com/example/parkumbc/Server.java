package com.example.parkumbc;

import android.content.Context;
import android.util.Log;
import com.google.android.gcm.GCMRegistrar;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import static com.example.parkumbc.Constant.*;

public class Server {

    static final String TAG = "SERVER";

    static ServerResponse register(final Context context, String registrationId) {
        List<NameValuePair> value = new LinkedList<NameValuePair>();
        value.add(new BasicNameValuePair(REGISTRATION_ID, registrationId));
        GCMRegistrar.setRegisteredOnServer(context, true);
        return postRequest(REGISTER_URL, value);
    }

    static ServerResponse reportParking(long parking_lot_id, boolean is_parked) {
        List<NameValuePair> value = new LinkedList<NameValuePair>();
        value.add(new BasicNameValuePair(PARKING_LOT_ID, String.valueOf(parking_lot_id)));
        return postRequest((is_parked ? PARK_URL : CHECKOUT_URL), value);
    }

    static ServerResponse syncParkingLots() {
        return getRequest(PARKING_LOTS_URL);
    }

    static void unregister(final Context context, final String registrationId) {
        Log.i(TAG, "Unregistering the device");
        GCMRegistrar.setRegisteredOnServer(context, false);
    }

    private static ServerResponse postRequest(String url, List<NameValuePair> value) {
        ServerResponse serverResponse = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(value));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            HttpResponse httpResponse = httpClient.execute(httpPost);
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            String responseString = reader.readLine();
            serverResponse = new ServerResponse(httpResponse.getStatusLine().getStatusCode(), responseString);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return serverResponse;
    }

    private static ServerResponse getRequest(String url) {
        ServerResponse serverResponse = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);

        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            String responseString = reader.readLine();
            serverResponse = new ServerResponse(httpResponse.getStatusLine().getStatusCode(), responseString);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return serverResponse;
    }

}
