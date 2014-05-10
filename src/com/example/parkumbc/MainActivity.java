package com.example.parkumbc;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
import java.util.Set;
import java.util.TreeMap;

import static com.example.parkumbc.Constant.*;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private static final int THRESHOLD = 1;
    private static final int REQUEST_CODE = 1;
    private static final String TAG = "MAIN_ACTIVITY";
    private static final double PI = 3.141592653589793;
    private static final double RADIUS = 6378.16;
    private AlertDialog selectLotDialog;

    private Context context;

    private GoogleMap map;
    private LocationTracker locationTracker;

    private Repository repository;
    private List<ParkingLot> parkingLots;
    private PermitGroup permitGroup;

    private int current_count = 0;

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
        Log.d(TAG, "No. of parking lots: " + parkingLots.size());

        for (ParkingLot lot : parkingLots)
            addPolygon(lot, BitmapDescriptorFactory.HUE_GREEN);

        TextView parkButton = (TextView) findViewById(R.id.park_button);
        TextView permitButton = (TextView) findViewById(R.id.permit_button);
        TextView notifyButton = (TextView) findViewById(R.id.notify_button);
        parkButton.setOnClickListener(this);
        permitButton.setOnClickListener(this);
        notifyButton.setOnClickListener(this);
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

    public static double radians(double x) {
        return x * PI / 180;
    }

    private double getDistance(ParkingLot parkingLot, LatLng p) {
        LatLng entrance = parkingLot.getEntrance();
        double dlong = radians(p.longitude - entrance.longitude);
        double dlat = radians(p.latitude - entrance.latitude);
        double a = (Math.sin(dlat / 2) * Math.sin(dlat / 2)) +
                Math.cos(radians(entrance.latitude)) * Math.cos(radians(p.latitude)) *
                        (Math.sin(dlong / 2) * Math.sin(dlong / 2));
        double angle = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return angle * RADIUS;
    }

    private void findClosest(LatLng p) {
        TreeMap<Double, ParkingLot> closest = new TreeMap<Double, ParkingLot>();
        ArrayList<String> temp = new ArrayList<String>();

        Log.d(TAG, "POINT:" + p.latitude + " " + p.longitude);
        Log.d(TAG, "PARKING LOTS: " + parkingLots.size());

        for (ParkingLot parkingLot : parkingLots) {
            closest.put(getDistance(parkingLot, p), parkingLot);
        }
        for (Object key : closest.keySet().toArray()) {
            ParkingLot value = closest.get(key);
            if (temp.size() < 3)
                temp.add(value.getLotName());
        }
        showDialog(temp);
    }

    private void showDialog(ArrayList<String> contents) {
        CharSequence[] closestLots = {contents.get(0), contents.get(1), contents.get(2)};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Parking Lot");
        builder.setSingleChoiceItems(closestLots, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        Toast.makeText(context, "first", Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        Toast.makeText(context, "second", Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        Toast.makeText(context, "third", Toast.LENGTH_LONG).show();
                        break;

                }
                selectLotDialog.dismiss();
            }
        });
        selectLotDialog = builder.create();
        selectLotDialog.show();
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
                findClosest(current_location);
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
                for (ParkingLot lot : parkingLots) {
                    addPolygon(lot, BitmapDescriptorFactory.HUE_GREEN);
                }
            } else {
                map.clear();
                for (ParkingLot lot : parkingLots) {
                    addPolygon(lot, BitmapDescriptorFactory.HUE_GREEN);
                }
                addPolygon(parkingLots.get(0), BitmapDescriptorFactory.HUE_RED);
            }
        } else {
            Log.d(TAG, "PROMPT");
            locationTracker.showEnableGpsDialog();
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.park_button:
                reportParking();
                break;
            case R.id.permit_button:
                intent = new Intent(context, PermitGroupActivity.class);
                intent.putParcelableArrayListExtra(PERMIT_GROUPS, repository.getPermitGroups());
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.notify_button:
                intent = new Intent(context, NotifyListActivity.class);
                intent.putParcelableArrayListExtra(PARKING_LOTS, repository.getParkingLots());
                startActivity(intent);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                Set<String> lots = preferences.getStringSet(PARKING_LOTS, null);
                if (lots != null) {
                    String[] lotsString = lots.toArray(new String[lots.size()]);
                    for (String s : lotsString) {
                        Toast.makeText(context, "Selected: " + s, Toast.LENGTH_LONG).show();
                    }
                }
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
