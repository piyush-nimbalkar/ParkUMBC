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
        TextView textViewPermitAbbrev = (TextView) rowView.findViewById(R.id.textViewPermitAbbrev);
        textViewPermitName.setText(values.get(position).getName());
        setPermitGroupIcon(position, textViewPermitAbbrev);
        return rowView;
    }

    private void setPermitGroupIcon(int position, TextView textViewPermitAbbrev) {
        if (values.get(position).getLetter().equals("A"))
            textViewPermitAbbrev.setBackground(context.getResources().getDrawable(R.drawable.ic_permit_group_a));
        else if (values.get(position).getLetter().equals("B"))
            textViewPermitAbbrev.setBackground(context.getResources().getDrawable(R.drawable.ic_permit_group_b));
        else if (values.get(position).getLetter().equals("C"))
            textViewPermitAbbrev.setBackground(context.getResources().getDrawable(R.drawable.ic_permit_group_c));
        else if (values.get(position).getLetter().equals("D"))
            textViewPermitAbbrev.setBackground(context.getResources().getDrawable(R.drawable.ic_permit_group_d));
        else if (values.get(position).getLetter().equals("E"))
            textViewPermitAbbrev.setBackground(context.getResources().getDrawable(R.drawable.ic_permit_group_e));
        else if (values.get(position).getLetter().equals("F"))
            textViewPermitAbbrev.setBackground(context.getResources().getDrawable(R.drawable.ic_permit_group_f));
        else if (values.get(position).getLetter().equals("P"))
            textViewPermitAbbrev.setBackground(context.getResources().getDrawable(R.drawable.ic_permit_group_p));
        else if (values.get(position).getLetter().equals("PE"))
            textViewPermitAbbrev.setBackground(context.getResources().getDrawable(R.drawable.ic_permit_group_pe));
        else if (values.get(position).getLetter().equals("PH"))
            textViewPermitAbbrev.setBackground(context.getResources().getDrawable(R.drawable.ic_permit_group_ph));
        else if (values.get(position).getLetter().equals("EV"))
            textViewPermitAbbrev.setBackground(context.getResources().getDrawable(R.drawable.ic_permit_group_ev));
        else
            textViewPermitAbbrev.setBackground(context.getResources().getDrawable(R.drawable.ic_permit_group_all));
    }

}
