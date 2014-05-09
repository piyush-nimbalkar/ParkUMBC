package com.example.parkumbc;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import android.os.Build;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;

import static com.example.parkumbc.Constant.*;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private static final int THRESHOLD = 1;
    private static final int REQUEST_CODE = 1;
    private static final String TAG = "MAIN_ACTIVITY";

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

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
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

    private double squaredLength(LatLng a, LatLng b) {
        return Math.pow((a.latitude - b.latitude), 2) + Math.pow((a.latitude - b.longitude), 2);
    }

    private double distanceFromSideSquared(LatLng a, LatLng b, LatLng p) {
        double tmp = squaredLength(a, b);
        if (tmp == 0.0)
            return squaredLength(p, a);
        double t = (p.latitude - a.latitude) * (b.latitude - a.latitude) +  (p.longitude - a.longitude) * (b.longitude - a.longitude)/ tmp;
        if (t < 0.0)
            return squaredLength(p, a);
        if (t > 1.0)
            return squaredLength(p, b);
        return squaredLength(p, new LatLng((a.latitude + t * (b.latitude - a.latitude)),
                (a.longitude + t * (b.longitude - a.longitude))));
    }

    private double distanceFromSide(LatLng a, LatLng b, LatLng p){
        return Math.sqrt(distanceFromSideSquared(a, b, p));
    }

    private  double getDistance(ParkingLot parkingLot, LatLng p){
        double min_dist = Double.MAX_VALUE, tmp_dist;
        ArrayList<LatLng> corners = parkingLot.getCorners();
        for (int i = 0; i < corners.size(); i++){
            tmp_dist = distanceFromSide(corners.get(i), corners.get((i+1)%corners.size()), p);
            if (tmp_dist < min_dist)
                min_dist = tmp_dist;
        }
        return min_dist;
    }

    private double calculateClosest(LatLng p) {

        double min_dist = Double.MAX_VALUE, tmp_dist;

        for (int i = 0; i < parkingLots.size(); i++) {
            tmp_dist = getDistance(parkingLots.get(i), p);
            if (tmp_dist < min_dist)
                min_dist = tmp_dist;
        }
        return min_dist;
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
                LatLng current_location = new LatLng(latitude, longitude);
                double min_dist = calculateClosest(current_location);
                Log.d(TAG,"CLOSEST LOT IS " + min_dist + " FROM HERE");
                Toast.makeText(context, getString(R.string.on_park_message), Toast.LENGTH_SHORT).show();
                current_count += 1;
            } else {
                repository.createEntry(latitude, longitude, parkingLots.get(0).getLotId(), false);
                parkButton.setText(getString(R.string.park));
                Toast.makeText(context, R.string.on_checkout_message, Toast.LENGTH_SHORT).show();
                current_count -= 1;
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
            Log.d(TAG, "PROMPT");
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
