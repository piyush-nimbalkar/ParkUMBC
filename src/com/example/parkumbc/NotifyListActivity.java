package com.example.parkumbc;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import model.ParkingLot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static com.example.parkumbc.Constant.PARKING_LOTS;
import static com.example.parkumbc.Constant.SELECTION_COLOR;

/* Generate a list of all parking lots and display them in a list view
 */
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

    /* On click of a parking lot, toggle the lot for subscription of notification
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        Set<String> oldLots = preferences.getStringSet(PARKING_LOTS, null);
        Set<String> newSelectedLots = new HashSet<String>();
        String lotName = parkingLots.get(position).getLotName();
        if (oldLots != null)
            newSelectedLots.addAll(oldLots);

        if (oldLots != null && oldLots.contains(lotName)) {
            newSelectedLots.remove(lotName);
            view.setBackgroundColor(0xFFFFFFFF);
        } else {
            newSelectedLots.add(lotName);
            view.setBackgroundColor(SELECTION_COLOR);
        }

        editor.putStringSet(PARKING_LOTS, newSelectedLots);
        editor.commit();
    }

}
