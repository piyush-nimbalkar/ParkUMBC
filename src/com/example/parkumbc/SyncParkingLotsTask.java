package com.example.parkumbc;

import android.os.AsyncTask;
import android.util.Log;
import model.ParkingLot;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SyncParkingLotsTask extends AsyncTask<String, Integer, ServerResponse> {

    static final String TAG = "SYNC_LOTS_TASK";
    public DataReceiver delegate;

    @Override
    protected ServerResponse doInBackground(String... params) {
        return Server.syncParkingLots();
    }

    @Override
    protected void onPostExecute(ServerResponse response) {
        super.onPostExecute(response);
        ArrayList<ParkingLot> parkingLots = new ArrayList<ParkingLot>();

        try {
            JSONObject jsonObject = new JSONObject(response.getMessage());
            JSONArray parkingLotArray = (JSONArray) jsonObject.get("parking_lots");

            for (int i = 0; i < parkingLotArray.length(); i++) {
                long lotId = Long.parseLong(parkingLotArray.getJSONObject(i).getString("parking_lot_id"));
                String lotName = parkingLotArray.getJSONObject(i).getString("parking_lot_name");
                long currentCount = Long.parseLong(parkingLotArray.getJSONObject(i).getString("current_count"));
                long capacity = Long.parseLong(parkingLotArray.getJSONObject(i).getString("capacity"));
                parkingLots.add(new ParkingLot(lotId, lotName, currentCount, capacity));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        delegate.receive(parkingLots);
    }

}
