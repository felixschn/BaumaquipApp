package com.example.rentalApplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalApplication.R;
import com.example.rentalApplication.models.Baumaschine;
import com.example.rentalApplication.ui.Baumaschine.AddBaumaschinenActivity;
import com.example.rentalApplication.ui.Baumaschine.BaumaschinenClickListener;
import com.example.rentalApplication.ui.Baumaschine.BaumaschinenFragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class BaumaschinenListAdapter extends RecyclerView.Adapter<BaumaschinenListAdapter.BaumaschinenViewHolder> {

    private LayoutInflater mInflater;
    private List<Baumaschine> baumaschineList = new ArrayList<>();
    private Context context;
    private BaumaschinenListAdapter baumaschinenListAdapter;
    private static final String TAG = "BaumaschinenListAdapter.java";
    private final BaumaschinenFragment baumaschinenFragment;
    private final BaumaschinenClickListener listener;

    public BaumaschinenListAdapter(BaumaschinenFragment baumaschinenFragment, BaumaschinenClickListener listener) {
        this.baumaschinenFragment = baumaschinenFragment;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BaumaschinenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_baumaschinen_item, parent, false);
        context = parent.getContext();
        return new BaumaschinenViewHolder(itemView, listener);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(BaumaschinenViewHolder holder, int position) {

        if (baumaschineList != null) {
            Baumaschine current = baumaschineList.get(position);
            holder.baumaschineName.setText(current.getMachineName());
            holder.baumaschineAnzahl.setText(current.getAmount().toString());
            holder.currentlyRented.setText(baumaschinenFragment.getAmountOfCurrentlyRentedMachine(current.getIdBaumaschine()).toString());
            holder.baumaschinePreisPerDay.setText(current.getPricePerDay().toString());
            holder.baumaschinePreisPerWeekend.setText(current.getPricePerWeekend().toString());
            holder.baumaschinePreisPerMonth.setText(current.getPricePerMonth().toString());
            holder.baumaschineOperatingHours.setEnabled(false);
            holder.baumaschineOperatingHours.setText("-");
            holder.baumaschineAmountOfGas.setEnabled(false);
            holder.baumaschineAmountOfGas.setText("-");
            holder.baumaschineDegreeOfWear.setEnabled(false);
            holder.baumaschineDegreeOfWear.setText("-");

            if (current.getOperatingHours() != null) {
                holder.baumaschineOperatingHours.setEnabled(true);
                holder.baumaschineOperatingHours.setText(current.getOperatingHours().toString());
            }

            if (current.getAmountOfGas() != null) {
                holder.baumaschineAmountOfGas.setEnabled(true);
                holder.baumaschineAmountOfGas.setText(current.getAmountOfGas());
            }

            if (current.getDegreeOfWear() != null) {
                holder.baumaschineDegreeOfWear.setEnabled(true);
                holder.baumaschineDegreeOfWear.setText(current.getDegreeOfWear());
            }

            boolean isExpanded = baumaschineList.get(position).getExpanded();
            //set Visibility to visible when isExpanded = true and to invisible when isExpanded is false
            holder.expandableConstraintLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
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

    public void setBaumaschinen(List<Baumaschine> baumaschineList) {
        this.baumaschineList = baumaschineList;
        notifyDataSetChanged();
    }

    class BaumaschinenViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView baumaschineName;
        private final TextView baumaschineAnzahl;
        private final TextView currentlyRented;
        private final TextView baumaschinePreisPerDay;
        private final TextView baumaschinePreisPerWeekend;
        private final TextView baumaschinePreisPerMonth;
        private final TextView baumaschineAmountOfGas;
        private final TextView baumaschineDegreeOfWear;
        private final TextView baumaschineOperatingHours;
        private final ImageButton modifyButton;
        private final ImageButton deleteButton;
        private final ConstraintLayout expandableConstraintLayout;
        private final WeakReference<BaumaschinenClickListener> listenerRef;

        public BaumaschinenViewHolder(@NonNull View itemView, BaumaschinenClickListener baumaschinenClickListener) {
            super(itemView);
            listenerRef = new WeakReference<>(baumaschinenClickListener);
            baumaschineName = itemView.findViewById(R.id.baumaschineName);
            baumaschineAnzahl = itemView.findViewById(R.id.baumaschineAnzahl);
            currentlyRented = itemView.findViewById(R.id.currentlyRent);
            baumaschinePreisPerDay = itemView.findViewById(R.id.baumaschinePreisPerDay);
            baumaschinePreisPerWeekend = itemView.findViewById(R.id.baumaschinePreisPerWeekend);
            baumaschinePreisPerMonth = itemView.findViewById(R.id.baumaschinePreisPerMonth);
            expandableConstraintLayout = itemView.findViewById(R.id.expandableConstraintLayout);
            baumaschineAmountOfGas = itemView.findViewById(R.id.baumschineAmountOfGas);
            baumaschineDegreeOfWear = itemView.findViewById(R.id.baumaschineDegreeOfWear);
            baumaschineOperatingHours = itemView.findViewById(R.id.baumaschineOperatingHours);
            modifyButton = itemView.findViewById(R.id.modifyButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            itemView.setOnClickListener(this);
            modifyButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
        }

        public void removeBaumaschine(int position) {
            baumaschineList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, baumaschineList.size());
        }


        @Override
        public void onClick(View v) {
            Baumaschine baumaschine = baumaschineList.get(getAdapterPosition());

            if (v.getId() == modifyButton.getId()) {
                Log.d(TAG, "Modify Button clicked!");
                Baumaschine modifyBaumaschine = baumaschineList.get(getAdapterPosition());
                Log.d("BaumaschinenListAdapter.java", "RowID Baumaschine: " + modifyBaumaschine.getIdBaumaschine());
                Intent modifyBaumaschineIntent = new Intent(context, AddBaumaschinenActivity.class);
                modifyBaumaschineIntent.putExtra("baumaschineneRowId", modifyBaumaschine.getIdBaumaschine());
                modifyBaumaschineIntent.putExtra("Class", "BaumaschinenListAdapter");
                context.startActivity(modifyBaumaschineIntent);
            }

            if (v.getId() == deleteButton.getId()) {
                baumaschinenFragment.archiveBaumaschine(baumaschine.getIdBaumaschine());

            } else {
                baumaschineList.get(getAdapterPosition()).setExpanded(!baumaschine.getExpanded());
                notifyItemChanged(getAdapterPosition());
            }

            listenerRef.get().onPositionClicked(getAdapterPosition());
        }
    }
}
