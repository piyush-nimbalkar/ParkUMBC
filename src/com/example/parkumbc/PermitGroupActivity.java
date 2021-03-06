package com.example.parkumbc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import model.PermitGroup;

import java.util.ArrayList;

import static com.example.parkumbc.Constant.PERMIT_GROUP;
import static com.example.parkumbc.Constant.PERMIT_GROUPS;

/* Generate a list of all parking permit groups and display them in a list view
 */
public class PermitGroupActivity extends Activity implements AdapterView.OnItemClickListener {

    private ArrayList<PermitGroup> permitGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permit_group);
        ListView listview = (ListView) findViewById(R.id.listViewPermitGroups);

        permitGroups = getIntent().getParcelableArrayListExtra(PERMIT_GROUPS);
        permitGroups.add(0, new PermitGroup(0, getString(R.string.all_parking_lots), "", ""));

        final PermitArrayAdapter adapter = new PermitArrayAdapter(this, permitGroups);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
    }

    /* On click of a permit group, return the selected value to the map
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        if (position > 0)
            intent.putExtra(PERMIT_GROUP, permitGroups.get(position));
        setResult(RESULT_OK, intent);
        finish();
    }

}
