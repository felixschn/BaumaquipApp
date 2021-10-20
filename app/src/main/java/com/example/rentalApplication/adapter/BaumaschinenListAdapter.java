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
    private static String TAG = "BaumaschinenListAdapter.java";
    private BaumaschinenFragment baumaschinenFragment;
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
            holder.baumaschinePreisPerDay.setText(current.getPricePerDay().toString());
            holder.baumaschinePreisPerWeekend.setText(current.getPricePerWeekend().toString());
            holder.baumaschinePreisPerMonth.setText(current.getPricePerMonth().toString());
            holder.baumaschineAmountOfGas.setText(current.getAmountOfGas().toString());
            holder.baumaschineDegreeOfWear.setText(current.getDegreeOfWear().toString());
            holder.baumaschineOperatingHours.setText(current.getOperatingHours().toString());

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

    public void setExpandableToFalse(int position){
        baumaschineList.get(position).setExpanded(false);
    }



    public void setBaumaschinen(List<Baumaschine> baumaschineList) {
        this.baumaschineList = baumaschineList;
        notifyItemInserted(baumaschineList.size()-1);
    }


    class BaumaschinenViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView baumaschineName;
        private final TextView baumaschineAnzahl;
        private final TextView baumaschinePreisPerDay;
        private final TextView baumaschinePreisPerWeekend;
        private final TextView baumaschinePreisPerMonth;
        private final TextView baumaschineAmountOfGas;
        private final TextView baumaschineDegreeOfWear;
        private final TextView baumaschineOperatingHours;
        private final ImageButton modifyButton;
        private final ImageButton deleteButton;
        private final ConstraintLayout expandableConstraintLayout;
        private WeakReference<BaumaschinenClickListener> listenerRef;

        public BaumaschinenViewHolder(@NonNull View itemView, BaumaschinenClickListener baumaschinenClickListener) {
            super(itemView);
            listenerRef = new WeakReference<>(baumaschinenClickListener);
            baumaschineName = itemView.findViewById(R.id.baumaschineName);
            baumaschineAnzahl = itemView.findViewById(R.id.baumaschineAnzahl);
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
            baumaschineName.setOnClickListener(this);
            modifyButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);

           /* //create OnClickListener to baumaschineName expand the recyclerview after userclick on the name
            baumaschineName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Baumaschine baumaschine = baumaschineList.get(getAdapterPosition());
                    baumaschine.setExpanded(!baumaschine.getExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });

            modifyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Modify Button clicked!");
                    Baumaschine baumaschine = baumaschineList.get(getAdapterPosition());
                    Log.d("BaumaschinenListAdapter.java", "RowID Baumaschine: " + baumaschine.getRowid());
                    Intent modifyBaumaschineIntent = new Intent(context, AddBaumaschinenActivity.class);
                    modifyBaumaschineIntent.putExtra("baumaschineneRowId", baumaschine.getRowid());
                    modifyBaumaschineIntent.putExtra("Class", "BaumaschinenListAdapter");
                    context.startActivity(modifyBaumaschineIntent);

                }
            });
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Delete Button clicked");
                    int position = getAdapterPosition();
                    baumaschinenFragment.archiveBaumaschine(baumaschineList.get(getAdapterPosition()).getRowid());
                    removeBaumaschine(getAdapterPosition());


                }
            });*/
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
