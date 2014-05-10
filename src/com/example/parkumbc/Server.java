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
import storage.ServerResponse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import static com.example.parkumbc.Constant.REGISTER_URL;
import static com.example.parkumbc.Constant.REGISTRATION_ID;

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
        } catch (Exception e) {
            e.printStackTrace();
        }
        GCMRegistrar.setRegisteredOnServer(context, true);
        return serverResponse;
    }

    static void unregister(final Context context, final String registrationId) {
        Log.i(TAG, "Unregistering the device");
        GCMRegistrar.setRegisteredOnServer(context, false);
    }

}
