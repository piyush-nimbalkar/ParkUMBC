package com.example.parkumbc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import model.PermitGroup;

import java.util.ArrayList;

public class PermitArrayAdapter extends ArrayAdapter<PermitGroup> {

    private final Context context;
    private final ArrayList<PermitGroup> values;

    public PermitArrayAdapter(Context context, ArrayList<PermitGroup> values) {
        super(context, R.layout.permit_group_row_layout, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.permit_group_row_layout, parent, false);
        TextView textViewPermitName = (TextView) rowView.findViewById(R.id.textViewPermitName);
        textViewPermitName.setText(values.get(position).getName());
        return rowView;
    }

}
