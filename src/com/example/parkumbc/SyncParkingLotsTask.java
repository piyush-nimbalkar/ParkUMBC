package com.example.parkumbc;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import model.ParkingLot;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SyncParkingLotsTask extends AsyncTask<String, Integer, ServerResponse> {

    static final String TAG = "SYNC_LOTS_TASK";

    public DataReceiver delegate;
    private ProgressDialog dialog;

    public SyncParkingLotsTask(Context context) {
        dialog = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.dialog.setMessage("Loading...");
        this.dialog.show();
    }

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
        if (dialog.isShowing())
            dialog.dismiss();
    }

}
