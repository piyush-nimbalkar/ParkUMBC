package com.example.parkumbc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class PermitGroupActivity extends Activity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permit_group);
        ListView listview = (ListView) findViewById(R.id.listViewPermitGroups);

        List<String> values = new ArrayList<String>();
        values.add("Student Commuters");
        values.add("Faculty");

        final PermitArrayAdapter adapter = new PermitArrayAdapter(this, values);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

}
