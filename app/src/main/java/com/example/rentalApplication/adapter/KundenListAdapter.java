package com.example.rentalApplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalApplication.R;
import com.example.rentalApplication.models.Baumaschine;
import com.example.rentalApplication.models.Kunde;

import java.util.ArrayList;
import java.util.List;

public class KundenListAdapter extends RecyclerView.Adapter<KundenListAdapter.KundenViewHolder> {

    private LayoutInflater mInflater;
    private List<Kunde> kundeList = new ArrayList<>();


    @NonNull
    @Override
    public KundenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_kunden_item, parent, false );
        return new KundenViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull KundenViewHolder holder, int position) {
        if(kundeList != null){
            Kunde current = kundeList.get(position);
            holder.kundenName.setText(current.getName());
            holder.kundenStrasse.setText(current.getStreetName());
            holder.kundenStrassenNummer.setText(current.getStreetNumber());
            holder.kundenZip.setText(current.getZip());
            holder.kundenOrt.setText(current.getLocation());
            holder.kundenTelefonnummer.setText(current.getTelefonNumber());
            holder.kundenBaustelle.setText(current.getConstructionSide());
            holder.kundenAnsprechpartner.setText(current.getContactPerson());
        }

    }

    private void insertNewVertrag(){

    }


    @Override
    public int getItemCount() {
        if(kundeList != null){
            return kundeList.size();
        }
        else{
            return 0;
        }
    }
    public void setKunden(List<Kunde> kundenList){
        this.kundeList = kundenList;
        notifyDataSetChanged();
    }

    class KundenViewHolder extends RecyclerView.ViewHolder{
        private final TextView kundenName;
        private final TextView kundenStrasse;
        private final TextView kundenStrassenNummer;
        private final TextView kundenZip;
        private final TextView kundenOrt;
        private final TextView kundenTelefonnummer;
        private final TextView kundenBaustelle;
        private final TextView kundenAnsprechpartner;

        public KundenViewHolder(@NonNull View itemView) {
            super(itemView);
            kundenName = itemView.findViewById(R.id.kundenName);
            kundenStrasse = itemView.findViewById(R.id.kundenStraße);
            kundenStrassenNummer = itemView.findViewById(R.id.kundenStraßenNummer);
            kundenZip = itemView.findViewById(R.id.kundenZip);
            kundenOrt = itemView.findViewById(R.id.kundenOrt);
            kundenTelefonnummer = itemView.findViewById(R.id.kundenTelefonnummer);
            kundenBaustelle = itemView.findViewById(R.id.kundenBaustelle);
            kundenAnsprechpartner = itemView.findViewById(R.id.kundenAnsprechpartner);
        }
    }
}
