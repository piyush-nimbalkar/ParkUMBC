package com.example.parkumbc;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import model.ParkingLot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static com.example.parkumbc.Constant.PARKING_LOTS;

public class NotifyListActivity extends Activity implements AdapterView.OnItemClickListener {

    private ArrayList<ParkingLot> parkingLots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_list);
        ListView listview = (ListView) findViewById(R.id.listViewParkingLots);

        parkingLots = getIntent().getParcelableArrayListExtra(PARKING_LOTS);

        final ParkingLotArrayAdapter adapter = new ParkingLotArrayAdapter(this, parkingLots);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, parkingLots.get(position).getLotName(), Toast.LENGTH_SHORT).show();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Set<String> newSelectedLots = new HashSet<String>();
        Set<String> oldLots = preferences.getStringSet(PARKING_LOTS, null);
        if (oldLots != null)
            newSelectedLots.addAll(oldLots);
        newSelectedLots.add(parkingLots.get(position).getLotName());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(PARKING_LOTS, newSelectedLots);
        editor.commit();
    }

}
