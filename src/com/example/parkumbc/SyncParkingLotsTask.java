package com.example.parkumbc;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SyncParkingLotsTask extends AsyncTask<String, Integer, ServerResponse> {

    static final String TAG = "SYNC_LOTS_TASK";

    @Override
    protected ServerResponse doInBackground(String... params) {
        return Server.syncParkingLots();
    }

    @Override
    protected void onPostExecute(ServerResponse response) {
        super.onPostExecute(response);
        try {
            JSONObject jsonObject = new JSONObject(response.getMessage());
            JSONArray parkingLotArray = (JSONArray) jsonObject.get("parking_lots");
            for (int i = 0; i < parkingLotArray.length(); i++)
                Log.d(TAG, parkingLotArray.getJSONObject(i).getString("parking_lot_name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, response.getMessage());
    }

}
