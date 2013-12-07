package com.example.parkumbc;

import model.Entry;
import repository.EntryRepository;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
//import android.content.Intent;

public class MainActivity extends FragmentActivity {	
    GPSTracker gps;    
    Button btnShowLocation;
    EntryRepository entryRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GoogleMap map = ((MapFragment) getFragmentManager()
                .findFragmentById(R.id.map)).getMap();

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(39.255, -76.710), 15));
        
        addMarkers(map);
        entryRepository = new EntryRepository(getApplicationContext());
        
        btnShowLocation = (Button) findViewById(R.id.button1);
        btnShowLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {        
                gps = new GPSTracker(MainActivity.this);
                if(gps.canGetLocation()){
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    
                    Toast.makeText(getApplicationContext(), "Latitude: " + latitude + "\nLongitude: " + longitude, Toast.LENGTH_LONG).show();
                    entryRepository.createEntry(latitude, longitude);
                    Entry entry = entryRepository.getEntry();
                    Toast.makeText(getApplicationContext(), "DB Latitude: " + entry.getLatitude() + "\nDB Longitude: " + entry.getLongitude(), Toast.LENGTH_LONG).show();
                } else {
                    gps.showSettingsAlert();
                }
            }
        });        
    }

    private void addMarkers(GoogleMap map) {
        map.addMarker(new MarkerOptions()
		        .position(new LatLng(39.255, -76.710))
		        .title("Hello world"));
        PolygonOptions rectOptions = new PolygonOptions()
		        .add(new LatLng(39.2535666, -76.7093),
		             new LatLng(39.2527, -76.708616),
		             new LatLng(39.2545666, -76.70635),
		             new LatLng(39.254916667, -76.707533333));
        map.addPolygon(rectOptions);
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
