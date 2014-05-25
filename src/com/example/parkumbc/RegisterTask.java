package com.example.parkumbc;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/* An asynchronous task to register with server for notifications
 */
public class RegisterTask extends AsyncTask<String, Integer, ServerResponse> {

    static final String TAG = "REGISTER_TASK";
    private Context context;

    public RegisterTask(Context context) {
        this.context = context;
    }

    @Override
    protected ServerResponse doInBackground(String... params) {
        return Server.register(context, params[0]);
    }

    @Override
    protected void onPostExecute(ServerResponse response) {
        super.onPostExecute(response);
        Log.d(TAG, response.getMessage());
    }

}
