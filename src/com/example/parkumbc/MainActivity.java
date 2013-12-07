package com.example.parkumbc;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;


public class MainActivity extends FragmentActivity implements
	GooglePlayServicesClient.ConnectionCallbacks,
	GooglePlayServicesClient.OnConnectionFailedListener{	

    private final static int
    CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    LocationClient mLocationClient;
    Location mCurrentLocation;
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mLocationClient = new LocationClient(this, this, this);
//        mLocationClient.connect();
//        mCurrentLocation = mLocationClient.getLastLocation();
      /* Use the LocationManager class to obtain GPS locations */
//        LocationManager mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//        LocationListener mlocListener = new MyLocationListener();
//        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
        
    }
    
//	@Override
//    protected void onStart() {
//        mLocationClient.connect();
//    }
//	
//	@Override
//    protected void onStop() {
//        super.onStop();
//    }
	
    @Override
    public void onConnected(Bundle dataBundle) {
        // Display the connection status
        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();

    }
    
    @Override
    public void onDisconnected() {
        // Display the connection status
        Toast.makeText(this, "Disconnected. Please re-connect.",
                Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(
                        this,
                        CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
//            showErrorDialog(connectionResult.getErrorCode());
        }
    }
    
    
    
    
    
    
    
    
    /* Class My Location Listener */
//    public class MyLocationListener implements LocationListener {
//
//	    @Override
//	    public void onLocationChanged(Location loc)
//	    {
//		    tempLoc = loc;
//	    	loc.getLatitude();
//		    loc.getLongitude();
//		    String Text = "My current location is: " + "Latitude = " + loc.getLatitude() + "Longitude = " + loc.getLongitude();
//		    Toast.makeText( getApplicationContext(), Text, Toast.LENGTH_LONG).show();
//	
//	    }
//	
//	    @Override
//	    public void onProviderDisabled(String provider) {
//	    	Toast.makeText( getApplicationContext(), "Gps Disabled", Toast.LENGTH_SHORT ).show();
//	    }
//	
//	
//	    @Override
//	    public void onProviderEnabled(String provider) {
//	    	Toast.makeText( getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();
//	    }
//	
//	
//	    @Override
//	    public void onStatusChanged(String provider, int status, Bundle extras) {
//	    }
//
//    }/* End of Class MyLocationListener */


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
