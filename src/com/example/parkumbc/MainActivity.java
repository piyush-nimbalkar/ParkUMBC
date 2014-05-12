package com.example.parkumbc;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.util.Log;
import com.google.android.gcm.GCMRegistrar;
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

public class MainActivity extends FragmentActivity implements View.OnClickListener, DataReceiver, DialogInterface.OnClickListener {

    private static final String TAG = "MAIN_ACTIVITY";
    private static final int REQUEST_CODE = 1;
    private static final double RADIUS = 6371;

    private AlertDialog selectLotDialog;
    private Context context;

    private GoogleMap map;
    private LocationTracker locationTracker;

    private Repository repository;
    private List<ParkingLot> parkingLots;
    private ArrayList<ParkingLot> closestLots;
    private PermitGroup permitGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        permitGroup = getIntent().getParcelableExtra(PERMIT_GROUP);

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(39.255, -76.710), 15));

        SyncParkingLotsTask task = new SyncParkingLotsTask(context);
        task.delegate = (DataReceiver) context;
        task.execute();

        ImageButton notifyButton = (ImageButton) findViewById(R.id.notify_button);
        ImageButton parkButton = (ImageButton) findViewById(R.id.park_button);
        ImageButton permitButton = (ImageButton) findViewById(R.id.permit_button);
        TextView notifyButtonText = (TextView) findViewById(R.id.notify_button_text);
        TextView parkButtonText = (TextView) findViewById(R.id.park_button_text);
        TextView permitButtonText = (TextView) findViewById(R.id.permit_button_text);

        notifyButton.setOnClickListener(this);
        parkButton.setOnClickListener(this);
        permitButton.setOnClickListener(this);
        notifyButtonText.setOnClickListener(this);
        parkButtonText.setOnClickListener(this);
        permitButtonText.setOnClickListener(this);

        GCMRegistrar.checkDevice(this);
        String regId = GCMRegistrar.getRegistrationId(this);

        if (regId.equals("")) {
            GCMRegistrar.register(this, SENDER_ID);
        } else {
            if (GCMRegistrar.isRegisteredOnServer(this))
                Log.d(TAG, "Already registered on the server.");
            else
                new RegisterTask(context).execute(regId);
        }
    }


    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.park_button:
            case R.id.park_button_text:
                onParkButtonClick();
                break;
            case R.id.permit_button:
            case R.id.permit_button_text:
                intent = new Intent(context, PermitGroupActivity.class);
                intent.putParcelableArrayListExtra(PERMIT_GROUPS, repository.getPermitGroups());
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.notify_button:
            case R.id.notify_button_text:
                intent = new Intent(context, NotifyListActivity.class);
                intent.putParcelableArrayListExtra(PARKING_LOTS, repository.getParkingLots());
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void onParkButtonClick() {
        locationTracker = new LocationTracker(context);
        Log.d(TAG, "Location: " + locationTracker.getLatitude() + ", " + locationTracker.getLongitude());

        if (locationTracker.canGetLocation()) {
            TextView parkButtonText = (TextView) findViewById(R.id.park_button_text);

            if (parkButtonText.getText() == getString(R.string.park)) {
                findClosest(new LatLng(locationTracker.getLatitude(), locationTracker.getLongitude()));
            } else {
                parkButtonText.setText(getString(R.string.park));
                Toast.makeText(context, R.string.on_checkout_message, Toast.LENGTH_SHORT).show();

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                long parkedLotId = preferences.getLong(PARKED_LOT_ID, -1);
                int parkingLotIndex = -1;
                for (int i = 0; i < parkingLots.size(); i++) {
                    if (parkingLots.get(i).getLotId() == parkedLotId) {
                        parkingLotIndex = i;
                        break;
                    }
                }
                repository.updateParkingLot(parkedLotId, false);
                parkingLots.get(parkingLotIndex).decrementCount();
                plotParkingLots();
            }
        } else {
            locationTracker.showEnableGpsDialog();
        }
    }

    private void showDialog() {
        CharSequence[] closestLotNames = new CharSequence[closestLots.size()];
        for (int i = 0; i < closestLots.size(); i++)
            closestLotNames[i] = closestLots.get(i).getLotName();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Select Parking Lot");
        builder.setSingleChoiceItems(closestLotNames, -1, (DialogInterface.OnClickListener) context);

        selectLotDialog = builder.create();
        selectLotDialog.show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        changeParkingStatus(closestLots.get(which).getLotId());
        selectLotDialog.dismiss();
    }

    private void changeParkingStatus(long parkingLotId) {
        int parkingLotIndex = -1;
        for (int i = 0; i < parkingLots.size(); i++) {
            if (parkingLots.get(i).getLotId() == parkingLotId) {
                parkingLotIndex = i;
                break;
            }
        }

        TextView parkButtonText = (TextView) findViewById(R.id.park_button_text);
        parkButtonText.setText(getString(R.string.checkout));
        Toast.makeText(context, getString(R.string.on_park_message), Toast.LENGTH_SHORT).show();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(PARKED_LOT_ID, parkingLotId);
        editor.commit();

        repository.updateParkingLot(parkingLotId, true);
        parkingLots.get(parkingLotIndex).incrementCount();
        plotParkingLots();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK)
            permitGroup = data.getParcelableExtra(PERMIT_GROUP);
        plotParkingLots();
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (locationTracker != null)
            locationTracker.removeLocationUpdates();
    }

    @Override
    public void receive(ArrayList<ParkingLot> lots) {
        repository = new Repository(context);
        repository.storeParkingLots(lots);
        parkingLots = repository.getParkingLots();
        plotParkingLots();
    }

    private void plotParkingLots() {
        map.clear();
        for (ParkingLot lot : parkingLots)
            addBoundary(lot);
    }

    private void addBoundary(ParkingLot lot) {
        if (permitGroup != null && !lot.getPermitGroups().contains(permitGroup))
            return;
        PolygonOptions options = new PolygonOptions();
        for (LatLng corner : lot.getCorners())
            options.add(corner);
        map.addPolygon(options.fillColor(0x500011FF).strokeColor(0x50444444).strokeWidth(0));

        if (lot.isFull())
            addMarker(lot, R.drawable.ic_marker_red);
        else if (lot.isAlmostFull())
            addMarker(lot, R.drawable.ic_marker_orange);
        else
            addMarker(lot, R.drawable.ic_marker_green);
    }

    private void addMarker(ParkingLot lot, int marker) {
        map.addMarker(new MarkerOptions()
                .position(lot.getMarkerPosition())
                .title(lot.getLotName())
                .icon(BitmapDescriptorFactory.fromResource(marker)));
    }

    private double getDistance(ParkingLot parkingLot, LatLng p) {
        LatLng entrance = parkingLot.getEntrance();
        double dlong = Math.toRadians(p.longitude - entrance.longitude);
        double dlat = Math.toRadians(p.latitude - entrance.latitude);
        double a = (Math.sin(dlat / 2) * Math.sin(dlat / 2)) +
                Math.cos(Math.toRadians(entrance.latitude)) * Math.cos(Math.toRadians(p.latitude)) *
                        (Math.sin(dlong / 2) * Math.sin(dlong / 2));
        double angle = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return angle * RADIUS;
    }

    private void findClosest(LatLng p) {
        TreeMap<Double, ParkingLot> closest = new TreeMap<Double, ParkingLot>();
        closestLots = new ArrayList<ParkingLot>();

        Log.d(TAG, "POINT:" + p.latitude + " " + p.longitude);
        Log.d(TAG, "PARKING LOTS: " + parkingLots.size());

        for (ParkingLot parkingLot : parkingLots)
            closest.put(getDistance(parkingLot, p), parkingLot);

        for (Object key : closest.keySet().toArray()) {
            ParkingLot value = closest.get(key);
            Log.d(TAG, key.toString());
            if (closestLots.size() < 3)
                closestLots.add(value);
        }
        showDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sync_lots:
                SyncParkingLotsTask task = new SyncParkingLotsTask(context);
                task.delegate = (DataReceiver) context;
                task.execute();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
