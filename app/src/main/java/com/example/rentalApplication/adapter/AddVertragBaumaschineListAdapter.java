package com.example.rentalApplication.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalApplication.R;
import com.example.rentalApplication.models.Baumaschine;

import java.util.ArrayList;
import java.util.List;

public class AddVertragBaumaschineListAdapter extends RecyclerView.Adapter<AddVertragBaumaschineListAdapter.AddVertragViewHolder> {
    private List<Baumaschine> baumaschineList = new ArrayList<>();
    private String amountBaumaschineString;
    private static final String TAG = "no machine left";

    @NonNull
    @Override
    public AddVertragViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_addvertragbaumaschine_item, parent, false);
        return new AddVertragViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AddVertragBaumaschineListAdapter.AddVertragViewHolder holder, int position) {
        if (baumaschineList != null) {
            Baumaschine current = baumaschineList.get(position);
            if(current.getAmount() > 0) {
                holder.baumaschineName.setText(current.getMachineName().toString());
                holder.amountBaumaschine.setText(current.getAmount().toString());
            }
            else{
                Log.d(TAG,current.getMachineName() +" schon verliehen");
            }
        }
    }


    @Override
    public int getItemCount() {
        if (baumaschineList != null) {
            return baumaschineList.size();
        } else {
            return 0;
        }
    }

    public void setAddVertragBaumaschinen(List<Baumaschine> baumaschineList){
        this.baumaschineList = baumaschineList;
        notifyDataSetChanged();
    }

    class AddVertragViewHolder extends RecyclerView.ViewHolder {
        private final TextView baumaschineName;
        private final TextView amountBaumaschine;

        public AddVertragViewHolder(@NonNull View itemView) {
            super(itemView);
            baumaschineName = itemView.findViewById(R.id.addVertragBaumaschineName);
            amountBaumaschine = itemView.findViewById(R.id.addVertragAmountBaumaschine);
        }
    }
}
