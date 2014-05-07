package com.example.parkumbc;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

public class LocationTracker extends Service implements LocationListener {

    private static final String TAG = "LOCATION TRACKER";
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BETWEEN_UPDATES = 1000 * 60 * 1;

    private final Context mContext;
    private LocationManager locationManager;
    private Location location;

    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;

    public LocationTracker(Context context) {
        this.mContext = context;
        updateLocation();
    }

    public void updateLocation() {
        try {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (isGPSEnabled || isNetworkEnabled) {
                this.canGetLocation = true;
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BETWEEN_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d(TAG, "Network");
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
                if (isGPSEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BETWEEN_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d(TAG, "GPS Enabled");
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeLocationUpdates() {
        if (locationManager != null) {
            locationManager.removeUpdates(LocationTracker.this);
        }
    }

    public double getLatitude() {
        if (location != null)
            return location.getLatitude();
        return 0.0;
    }

    public double getLongitude() {
        if (location != null)
            return location.getLongitude();
        return 0.0;
    }

    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    public void showEnableGpsDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle(getString(R.string.gps_dialog_title));
        alertDialog.setMessage(getString(R.string.gps_dialog_message));
        alertDialog.setPositiveButton(getString(R.string.settings_button), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });
        alertDialog.setNegativeButton(getString(R.string.cancel_button), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}