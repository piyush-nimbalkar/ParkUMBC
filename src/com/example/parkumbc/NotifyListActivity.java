package com.example.parkumbc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import model.ParkingLot;

import java.util.ArrayList;

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
    }

}
