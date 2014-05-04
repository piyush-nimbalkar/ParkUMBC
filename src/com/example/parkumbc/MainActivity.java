package com.example.parkumbc;

import android.content.Context;
import repository.EntryRepository;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private static final int THRESHOLD = 1;
    private Context context;

    private GoogleMap map;
    private LocationTracker locationTracker;

    private Button btnShowLocation;
    private EntryRepository entryRepository;
    private int current_count = 0;

    double coord[][][] = {{{39.25531666, -76.71158333},
            {39.255, -76.710483333},
            {39.254633333, -76.710666667},
            {39.254616667, -76.710766667},
            {39.2545, -76.7109},
            {39.2544, -76.710983333},
            {39.254483333, -76.71125},
            {39.2549, -76.711016667},
            {39.2551, -76.7116}}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(39.255, -76.710), 15));

        entryRepository = new EntryRepository(getApplicationContext());
        addMarkers();
        addPolgons();

        btnShowLocation = (Button) findViewById(R.id.toggleButton);
        btnShowLocation.setOnClickListener(this);
    }

    private void addMarkers() {
        map.addMarker(new MarkerOptions()
                .position(new LatLng(39.2549, -76.71073))
                .title("Commons").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        map.addMarker(new MarkerOptions()
                .position(new LatLng(39.254266667, -76.707533333))
                .title("Transit").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
    }

    private void addPolgons() {
        entryRepository.getParkingLots();
        PolygonOptions rectOptions = new PolygonOptions()
                .add(new LatLng(39.25531666, -76.71158333))
                .add(new LatLng(39.255, -76.710483333))
                .add(new LatLng(39.254633333, -76.710666667))
                .add(new LatLng(39.254616667, -76.710766667))
                .add(new LatLng(39.2545, -76.7109))
                .add(new LatLng(39.2544, -76.710983333))
                .add(new LatLng(39.254483333, -76.71125))
                .add(new LatLng(39.2549, -76.711016667))
                .add(new LatLng(39.2551, -76.7116));
        map.addPolygon(rectOptions.fillColor(0x500011FF).strokeColor(0x50444444).strokeWidth(0));

        PolygonOptions Transit = new PolygonOptions()
                .add(new LatLng(39.254783333, -76.707516667),
                        new LatLng(39.254433333, -76.706733333),
                        new LatLng(39.254133333, -76.706916667),
                        new LatLng(39.254466667, -76.70765),
                        new LatLng(39.254383333, -76.707716667),
                        new LatLng(39.254016667, -76.707083333),
                        new LatLng(39.2535, -76.707583333),
                        new LatLng(39.253783333, -76.708),
                        new LatLng(39.254116667, -76.708116667));
        map.addPolygon(Transit.fillColor(0x500011FF).strokeColor(0x50444444).strokeWidth(0));

    }

    private double calculateClosest() {
        int n, i;
        double slope;
        long parking_lots = coord.length;
        for (n = 0; n < parking_lots; n++) {
            for (i = 0; i < coord[n].length; i++) {

                Toast.makeText(getApplicationContext(), Double.toString(coord[n][0][0]), 1).show();
                Toast.makeText(getApplicationContext(), Double.toString(coord[n][i][1]), 1).show();
            }
        }
        return 1.3;
    }

    @Override
    public void onClick(View v) {
        locationTracker = new LocationTracker(context);
        if (locationTracker.canGetLocation()) {
            double latitude = locationTracker.getLatitude();
            double longitude = locationTracker.getLongitude();
            Button toggleButton = (Button) findViewById(R.id.toggleButton);

            if (toggleButton.getText() == getString(R.string.park)) {
                entryRepository.createEntry(latitude, longitude, 1, true);
                toggleButton.setText(getString(R.string.checkout));
                Toast.makeText(getApplicationContext(), getString(R.string.on_park_message), Toast.LENGTH_SHORT).show();
                current_count += 1;
            } else {
                entryRepository.createEntry(latitude, longitude, 1, false);
                toggleButton.setText(getString(R.string.park));
                Toast.makeText(getApplicationContext(), R.string.on_checkout_message, Toast.LENGTH_SHORT).show();
                current_count -= 1;
//                calculateClosest();
            }

            if (current_count < THRESHOLD) {
                map.clear();
                addPolgons();
                map.addMarker(new MarkerOptions()
                        .position(new LatLng(39.2549, -76.71073))
                        .title("Commons").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

                map.addMarker(new MarkerOptions()
                        .position(new LatLng(39.254266667, -76.707533333))
                        .title("Transit").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            } else {
                map.clear();
                addPolgons();
                map.addMarker(new MarkerOptions()
                        .position(new LatLng(39.2549, -76.71073))
                        .title("Commons").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                map.addMarker(new MarkerOptions()
                        .position(new LatLng(39.254266667, -76.707533333))
                        .title("Transit").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            }
        } else {
            locationTracker.showSettingsAlert();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (locationTracker != null)
            locationTracker.removeLocationUpdates();
    }

}
