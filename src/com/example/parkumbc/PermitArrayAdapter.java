package com.example.parkumbc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class PermitArrayAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final List<String> values;

    public PermitArrayAdapter(Context context_, List<String> values_) {
        super(context_, R.layout.permit_group_row_layout, values_);
        context = context_;
        values = values_;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.permit_group_row_layout, parent, false);
        TextView textViewPermitName = (TextView) rowView.findViewById(R.id.textViewPermitName);
        textViewPermitName.setText(values.get(position));
        return rowView;
    }

}
