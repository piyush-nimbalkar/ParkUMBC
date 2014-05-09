package com.example.parkumbc;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import model.ParkingLot;
import model.PermitGroup;
import repository.Repository;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.List;

import static com.example.parkumbc.Constant.*;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private static final int THRESHOLD = 1;
    private static final int REQUEST_CODE = 1;
    private Context context;

    private GoogleMap map;
    private LocationTracker locationTracker;

    private Repository repository;
    private List<ParkingLot> parkingLots;
    private PermitGroup permitGroup;

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

        permitGroup = getIntent().getParcelableExtra(PERMIT_GROUP);

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(39.255, -76.710), 15));

        repository = new Repository(getApplicationContext());
        parkingLots = repository.getParkingLots();
        for (ParkingLot lot : parkingLots)
            addPolygon(lot, BitmapDescriptorFactory.HUE_GREEN);

        TextView parkButton = (TextView) findViewById(R.id.park_button);
        TextView permitButton = (TextView) findViewById(R.id.permit_button);
        parkButton.setOnClickListener(this);
        permitButton.setOnClickListener(this);
    }

    private void addPolygon(ParkingLot lot, float color) {
        if (permitGroup != null && !lot.getPermitGroups().contains(permitGroup))
            return;
        PolygonOptions options = new PolygonOptions();
        for (LatLng l : lot.getCorners())
            options.add(new LatLng(l.latitude, l.longitude));
        map.addPolygon(options.fillColor(0x500011FF).strokeColor(0x50444444).strokeWidth(0));
        addMarker(lot, color);
    }

    private void addMarker(ParkingLot lot, float color) {
        map.addMarker(new MarkerOptions()
                .position(lot.getMarkerPosition())
                .title(lot.getLotName())
                .icon(BitmapDescriptorFactory.defaultMarker(color)));
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

    private void reportParking() {
        locationTracker = new LocationTracker(context);
        if (locationTracker.canGetLocation()) {
            double latitude = locationTracker.getLatitude();
            double longitude = locationTracker.getLongitude();
            TextView parkButton = (TextView) findViewById(R.id.park_button);

            if (parkButton.getText() == getString(R.string.park)) {
                repository.createEntry(latitude, longitude, parkingLots.get(0).getLotId(), true);
                parkButton.setText(getString(R.string.checkout));
                Toast.makeText(getApplicationContext(), getString(R.string.on_park_message), Toast.LENGTH_SHORT).show();
                current_count += 1;
            } else {
                repository.createEntry(latitude, longitude, parkingLots.get(0).getLotId(), false);
                parkButton.setText(getString(R.string.park));
                Toast.makeText(getApplicationContext(), R.string.on_checkout_message, Toast.LENGTH_SHORT).show();
                current_count -= 1;
//                calculateClosest();
            }

            if (current_count < THRESHOLD) {
                map.clear();
                addPolygon(parkingLots.get(0), BitmapDescriptorFactory.HUE_GREEN);
                addPolygon(parkingLots.get(1), BitmapDescriptorFactory.HUE_GREEN);
            } else {
                map.clear();
                addPolygon(parkingLots.get(0), BitmapDescriptorFactory.HUE_RED);
                addPolygon(parkingLots.get(1), BitmapDescriptorFactory.HUE_GREEN);
            }
        } else {
            locationTracker.showEnableGpsDialog();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.park_button:
                reportParking();
                break;
            case R.id.permit_button:
                Intent intent = new Intent(context, PermitGroupActivity.class);
                intent.putParcelableArrayListExtra(PERMIT_GROUPS, repository.getPermitGroups());
                startActivityForResult(intent, REQUEST_CODE);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK)
            permitGroup = data.getParcelableExtra(PERMIT_GROUP);
        map.clear();
        for (ParkingLot lot : parkingLots)
            addPolygon(lot, BitmapDescriptorFactory.HUE_GREEN);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (locationTracker != null)
            locationTracker.removeLocationUpdates();
    }

}
