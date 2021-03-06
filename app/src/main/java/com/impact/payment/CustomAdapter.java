package com.impact.payment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<CustomItem> {
    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     */
    public CustomAdapter(@NonNull Context context, ArrayList<CustomItem> customItem) {
        super(context, 0, customItem);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_spinner, parent, false);
        }
        CustomItem item = getItem(position);
        TextView textTV = convertView.findViewById(R.id.spinnerText);
        if(item != null){
            textTV.setText(item.getSpinnerData());
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_dropdown, parent, false);
        }
        CustomItem item = getItem(position);
        TextView textTV = convertView.findViewById(R.id.spinnerText);
        if(item != null){
            textTV.setText(item.getSpinnerData());
        }

        return convertView;
    }
}
