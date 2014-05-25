package com.example.parkumbc;

import android.os.AsyncTask;
import android.util.Log;

/* An asynchronous task to update the parking lot capacity when their
 * is either a park or checkout request
 */
public class UpdateParkingTask extends AsyncTask<String, Void, ServerResponse> {

    static final String TAG = "UPDATE_PARKING";
    private long parkingLotId;
    private boolean isParked;

    public UpdateParkingTask(long parkingLotId, boolean isParked) {
        this.parkingLotId = parkingLotId;
        this.isParked = isParked;
    }

    @Override
    protected ServerResponse doInBackground(String... params) {
        return Server.reportParking(parkingLotId, isParked);
    }

    @Override
    protected void onPostExecute(ServerResponse response) {
        super.onPostExecute(response);
        Log.d(TAG, response.getMessage());
    }

}