package com.example.rentalApplication.ui.Vertraege.Spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.example.rentalApplication.R;
import com.example.rentalApplication.models.Baumaschine;
import com.example.rentalApplication.ui.Vertraege.AddVertragActivity;
import com.example.rentalApplication.ui.Vertraege.ModifyVertragViewModel;
import com.example.rentalApplication.ui.Vertraege.Stuecklisteneintrag.AddStuecklisteneintragViewModel;

import java.util.ArrayList;
import java.util.List;

public class CustomBaumaschinenAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    private List<Baumaschine> baumaschineList = new ArrayList<>();
    private AddStuecklisteneintragViewModel addStuecklisteneintragViewModel;


    public CustomBaumaschinenAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater.from(context));
        addStuecklisteneintragViewModel = new ViewModelProvider(((AddVertragActivity)context)).get(AddStuecklisteneintragViewModel.class);
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
        return baumaschineList.get(position);
        //return baumaschineList.get(position).getMachineName();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    //method to retrieve a list of all Database entries from the calling activity
    public void setBaumaschinen(List<Baumaschine> baumaschineList) {
        //check for machines who are currently not available and remove them from the list so that the spinner can't show them
        /*for(int i = 0; i < baumaschineList.size(); i++){
            if(((AddVertragActivity)context).getAvailableBaumaschinenAmount(addStuecklisteneintragViewModel, baumaschineList.get(i)) == 0){
                baumaschineList.remove(i);
            }*/
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
        amount.setText(String.valueOf(((AddVertragActivity) context).getAvailableBaumaschinenAmount(addStuecklisteneintragViewModel, current)));


        return view;
    }


}
