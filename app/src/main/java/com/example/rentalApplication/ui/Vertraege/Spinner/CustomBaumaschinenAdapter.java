package com.example.rentalApplication.ui.Vertraege.Spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rentalApplication.R;
import com.example.rentalApplication.models.Baumaschine;


import java.util.ArrayList;
import java.util.List;

public class CustomBaumaschinenAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    private List<Baumaschine> baumaschineList = new ArrayList<>();

    public CustomBaumaschinenAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    //amount of spinner elements, depending of the size of baumaschinenList
    public int getCount() {
        if (baumaschineList != null) {
            return baumaschineList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return null;
        //return baumaschineList.get(position).getMachineName();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    //method to retrieve a list of all Database entries from the calling activity
    public void setBaumaschinen(List<Baumaschine> baumaschineList){
        this.baumaschineList = baumaschineList;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Baumaschine current = baumaschineList.get(position);
        view = inflater.inflate(R.layout.custom_spinner_baumaschine_item, null);
        TextView name = (TextView) view.findViewById(R.id.customSpinnerBaumaschinenName);
        TextView amount = (TextView) view.findViewById(R.id.customSpinnerBaumaschinenAmount);
        name.setText(current.getMachineName());
        amount.setText(current.getAmount().toString());
        return view;
    }
}
