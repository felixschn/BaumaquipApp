package com.example.rentalApplication.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalApplication.R;

public class BaumaschinenViewHolder extends RecyclerView.ViewHolder {
    private final TextView baumaschineName;
    private final TextView baumaschineAnzahl;
    private final TextView baumaschinePreis;

    public BaumaschinenViewHolder(@NonNull View itemView) {
        super(itemView);
        baumaschineName = itemView.findViewById(R.id.baumaschineName);
        baumaschineAnzahl = itemView.findViewById(R.id.baumaschineAnzahl);
        baumaschinePreis = itemView.findViewById(R.id.baumaschinePreis);
    }

    public void bind(String name, @NonNull Integer anzahl, @NonNull Double preis){
        baumaschineName.setText(name);
        baumaschineAnzahl.setText(anzahl.toString());
        baumaschinePreis.setText(preis.toString());
    }
    static BaumaschinenViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_baumaschinen_item, parent, false);
        return new BaumaschinenViewHolder(view);
    }
}
