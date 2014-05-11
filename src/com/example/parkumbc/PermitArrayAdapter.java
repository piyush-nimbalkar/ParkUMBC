package com.example.parkumbc;

import android.content.Context;
import android.util.Log;
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
        TextView textViewPermitAbbrev = (TextView) rowView.findViewById(R.id.textViewPermitAbbrev);
        textViewPermitName.setText(values.get(position).getName());
        textViewPermitAbbrev.setText(values.get(position).getLetter());
        String color = values.get(position).getColor();
        int viewColor = 0xffD9A404;

        if (color.equals("red"))
            viewColor = 0xffFA0081;
        else if (color.equals("green"))
            viewColor = 0xff129101;
        else if (color.equals("yellow"))
            viewColor = 0xffDDE324;
        else if (color.equals("violet"))
            viewColor = 0xffAE04D9;
        else if (color.equals("orange"))
            viewColor = 0xffFF8400;
        else if (color.equals("blue"))
            viewColor = 0xff18769E;
        else if (color.equals("dark_green"))
            viewColor = 0xff00BA6D;

        textViewPermitAbbrev.setBackgroundColor(viewColor);
        return rowView;
    }

}
