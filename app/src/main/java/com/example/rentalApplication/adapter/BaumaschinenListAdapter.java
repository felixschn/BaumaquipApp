package com.example.rentalApplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalApplication.R;
import com.example.rentalApplication.models.Baumaschine;

import java.util.ArrayList;
import java.util.List;

public class BaumaschinenListAdapter extends RecyclerView.Adapter<BaumaschinenListAdapter.BaumaschinenViewHolder> {

    private LayoutInflater mInflater;
    private List<Baumaschine> baumaschineList = new ArrayList<>();

    @NonNull
    @Override
    public BaumaschinenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_baumaschinen_item, parent, false);
        return new BaumaschinenViewHolder(itemView);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(BaumaschinenViewHolder holder, int position) {
        if (baumaschineList != null) {
            Baumaschine current = baumaschineList.get(position);
            holder.baumaschineName.setText(current.getMachineName());
            holder.baumaschineAnzahl.setText(current.getAmount().toString());
            holder.baumaschinePreisPerDay.setText(current.getPricePerDay().toString());
            holder.baumaschinePreisPerWeekend.setText(current.getPricePerWeekend().toString());
            holder.baumaschinePreisPerMonth.setText(current.getPricePerMonth().toString());

            boolean isExpanded = baumaschineList.get(position).getExpanded();
            holder.expandableConstraintLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        }
        /*else{
            holder.baumaschinePreis.setText("TestMaschine");
            holder.baumaschineAnzahl.setText("1");
            holder.baumaschinePreis.setText("10");
        }
*/
    }

    @Override
    public int getItemCount() {
        if (baumaschineList != null) {
            return baumaschineList.size();
        } else {
            return 0;
        }
    }
    public void setBaumaschinen(List<Baumaschine> baumaschineList){
        this.baumaschineList = baumaschineList;
        notifyDataSetChanged();
    }


    class BaumaschinenViewHolder extends RecyclerView.ViewHolder {
        private final TextView baumaschineName;
        private final TextView baumaschineAnzahl;
        private final TextView baumaschinePreisPerDay;
        private final TextView baumaschinePreisPerWeekend;
        private final TextView baumaschinePreisPerMonth;
        private final ConstraintLayout expandableConstraintLayout;




        public BaumaschinenViewHolder(@NonNull View itemView) {
            super(itemView);
            baumaschineName = itemView.findViewById(R.id.baumaschineName);
            baumaschineAnzahl = itemView.findViewById(R.id.baumaschineAnzahl);
            baumaschinePreisPerDay = itemView.findViewById(R.id.baumaschinePreisPerDay);
            baumaschinePreisPerWeekend = itemView.findViewById(R.id.baumaschinePreisPerWeekend);
            baumaschinePreisPerMonth = itemView.findViewById(R.id.baumaschinePreisPerMonth);
            expandableConstraintLayout = itemView.findViewById(R.id.expandableConstraintLayout);

            baumaschineName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Baumaschine baumaschine = baumaschineList.get(getAdapterPosition());
                    baumaschine.setExpanded(!baumaschine.getExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }


    }


}
