package com.example.rentalApplication.ui.main;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalApplication.models.Baumaschine;

import java.util.ArrayList;
import java.util.List;

public class BaumaschinenListAdapter extends RecyclerView.Adapter<Baumaschine,BaumaschinenViewHolder> {

    private ArrayList<Baumaschine> baumaschines;
    protected BaumaschinenListAdapter(ArrayList<Baumaschine> mBaumaschine) {
        this.baumaschines = mBaumaschine;
    }


    @NonNull
    @Override
    public BaumaschinenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return BaumaschinenViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull Baumaschine holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull BaumaschinenViewHolder holder, int position) {
        Baumaschine current = getItem(position);
        holder.bind(current.getMachineName(),current.getAmount(), current.getPricePerDay());
    }

}
