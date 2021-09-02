package com.example.rentalApplication.ui.Vertraege.Spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rentalApplication.R;
import com.example.rentalApplication.models.Kunde;
import com.example.rentalApplication.ui.Kunde.KundenViewModel;

import java.util.ArrayList;
import java.util.List;

//Adapter für den Spinner
public class CustomKundeAdapter extends BaseAdapter {
    Context context;
    KundenViewModel kundenViewModel;
    LayoutInflater inflater;
    private List<Kunde> kundenList = new ArrayList<>();

    public CustomKundeAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater.from(context));

    }
    //method to retrieve a list of all Database entries from the calling activity
    public void setKunden(List<Kunde> kundenList){
        this.kundenList = kundenList;
        notifyDataSetChanged();
    }

    @Override
    //amount of spinner elements, depending on the size of kundenList
    public int getCount() {
        if (kundenList != null) {
            return kundenList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        //aus KundenListe ein Element anhand der Spinner position wählen
        Kunde current = kundenList.get(position);
        view = inflater.inflate(R.layout.custom_spinner_kunde, null);
        TextView name = (TextView) view.findViewById(R.id.customSpinnerKundeName);
        TextView location = (TextView) view.findViewById(R.id.customSpinnerKundeLocation);
        name.setText(current.getName().toString());
        location.setText(current.getLocation().toString());
        return view;
    }
}
