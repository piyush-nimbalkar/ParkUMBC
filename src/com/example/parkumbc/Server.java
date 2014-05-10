package com.example.parkumbc;

import android.content.Context;
import android.util.Log;
import com.google.android.gcm.GCMRegistrar;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
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
        ServerResponse serverResponse = null;

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(REGISTER_URL);

        List<NameValuePair> value = new LinkedList<NameValuePair>();
        value.add(new BasicNameValuePair(REGISTRATION_ID, registrationId));

        try {
            post.setEntity(new UrlEncodedFormEntity(value));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            HttpResponse httpResponse = client.execute(post);
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            String responseString = reader.readLine();
            serverResponse = new ServerResponse(httpResponse.getStatusLine().getStatusCode(), responseString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        GCMRegistrar.setRegisteredOnServer(context, true);
        return serverResponse;
    }

    static ServerResponse reportParking(long parking_lot_id, boolean is_parked) {
        ServerResponse serverResponse = null;
        String url = (is_parked ? PARK_URL : CHECKOUT_URL);

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        List<NameValuePair> value = new LinkedList<NameValuePair>();
        value.add(new BasicNameValuePair(PARKING_LOT_ID, String.valueOf(parking_lot_id)));

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

    static void unregister(final Context context, final String registrationId) {
        Log.i(TAG, "Unregistering the device");
        GCMRegistrar.setRegisteredOnServer(context, false);
    }

}
