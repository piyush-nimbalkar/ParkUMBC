package com.example.parkumbc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import model.PermitGroup;

import java.util.ArrayList;

public class PermitGroupActivity extends Activity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permit_group);
        ListView listview = (ListView) findViewById(R.id.listViewPermitGroups);

        ArrayList<PermitGroup> permitGroups = getIntent().getParcelableArrayListExtra("PermitGroups");

        final PermitArrayAdapter adapter = new PermitArrayAdapter(this, permitGroups);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

}
