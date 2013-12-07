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
                if (gps.canGetLocation()) {
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    Button ToggleButton = (Button) findViewById(R.id.button1);
                    
                    if(ToggleButton.getText() == "Park"){
                        entryRepository.createEntry(latitude, longitude, 1, 1);
                    	ToggleButton.setText("Checkout");
                        Toast.makeText(getApplicationContext(), "Thank you for making UMBC better! :-)", 1).show();
                    } else {
                        entryRepository.createEntry(latitude, longitude, 1, 0);
                    	ToggleButton.setText("Park");
                        Toast.makeText(getApplicationContext(), "Have a safe ride! :-)", 1).show();
                    }
                } else {
                    gps.showSettingsAlert();
                }
            }
        });        
    }

    private void addMarkers(GoogleMap map) {
        map.addMarker(new MarkerOptions()
		        .position(new LatLng(39.2549, -76.71073))
		        .title("Hello world"));
        PolygonOptions rectOptions = new PolygonOptions()
		        .add(new LatLng(39.25531666, -76.71158333),
		        new LatLng(39.255, -76.710483333),
		        new LatLng(39.254633333, -76.710666667),
		        new LatLng(39.254616667, -76.710766667),
		        new LatLng(39.2545, -76.7109),
		        new LatLng(39.2544, -76.710983333),
		        new LatLng(39.254483333, -76.71125),
		        new LatLng(39.2549, -76.711016667),
		        new LatLng(39.2551, -76.7116));
        map.addPolygon(rectOptions);
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
