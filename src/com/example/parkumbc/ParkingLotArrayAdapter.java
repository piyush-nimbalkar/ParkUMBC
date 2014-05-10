package com.example.parkumbc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import model.ParkingLot;

import java.util.ArrayList;

public class ParkingLotArrayAdapter extends ArrayAdapter<ParkingLot> {

    private final Context context;
    private final ArrayList<ParkingLot> values;

    public ParkingLotArrayAdapter(Context context, ArrayList<ParkingLot> values) {
        super(context, R.layout.parking_lot_row_layout, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.parking_lot_row_layout, parent, false);
        TextView textViewPermitName = (TextView) rowView.findViewById(R.id.textViewParkingLotName);
        textViewPermitName.setText(values.get(position).getLotName());
        return rowView;
    }

}
