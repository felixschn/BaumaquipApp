package com.example.rentalApplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalApplication.R;
import com.example.rentalApplication.models.Vertrag;

import java.util.ArrayList;
import java.util.List;

public class VertragListAdapter extends RecyclerView.Adapter<VertragListAdapter.VertragViewHolder>{
    private LayoutInflater mInflater;
    private List<Vertrag> vertragList = new ArrayList<>();

    @NonNull
    @Override
    public VertragViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_vertrag_item, parent, false);
        return new VertragViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VertragViewHolder holder, int position) {
        if(vertragList != null){
            Vertrag current = vertragList.get(position);
            //holder.baumaschineVertrag.setText(current.getVertragBaumaschineList());
            holder.kundenVertrag.setText(current.getVertragKunde());
            holder.beginnLeihe.setText(current.getBeginnLeihe());
            holder.endeLeihe.setText(current.getEndeLeihe());
        }

    }
    @Override
    public int getItemCount(){
        if(vertragList != null){
            return vertragList.size();
        }
        else{
            return 0;
        }
    }

    public void setVertrag(List<Vertrag> vertragList){
        this.vertragList = vertragList;
        notifyDataSetChanged();
    }

    class VertragViewHolder extends  RecyclerView.ViewHolder{
        private final TextView baumaschineVertrag;
        private final TextView kundenVertrag;
        private final TextView beginnLeihe;
        private final TextView endeLeihe;

        public VertragViewHolder(@NonNull View itemView) {
            super(itemView);
            baumaschineVertrag = itemView.findViewById(R.id.baumaschineVertrag);
            kundenVertrag = itemView.findViewById(R.id.baumaschineVertrag);
            beginnLeihe = itemView.findViewById(R.id.beginnLeihe);
            endeLeihe = itemView.findViewById(R.id.endeLeihe);
        }
    }
}
