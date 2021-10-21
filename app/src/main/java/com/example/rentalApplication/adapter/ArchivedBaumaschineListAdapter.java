package com.example.rentalApplication.adapter;

import android.app.AlertDialog;
import android.content.Context;
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
import com.example.rentalApplication.ui.Baumaschine.ArchivedBaumaschinenActivity;

import java.util.ArrayList;
import java.util.List;

public class ArchivedBaumaschineListAdapter extends RecyclerView.Adapter<ArchivedBaumaschineListAdapter.ArchivedViewHolder> {
    private List<Baumaschine> baumaschineList = new ArrayList<>();
    private Context context;
    private static final String TAG = "ArchivedBaumaschinenListAdapter.java";
    private final ArchivedBaumaschinenActivity archivedBaumaschinenActivity;

    //constructor expanded with Activity Param to call the method delete later on in the activity (in RecyclerView Adapter no ViewModel is creatable therefor we execute the delete method in the calling activity)
    public ArchivedBaumaschineListAdapter(ArchivedBaumaschinenActivity archivedBaumaschinenActivity) {
        this.archivedBaumaschinenActivity = archivedBaumaschinenActivity;
    }

    @NonNull
    @Override
    public ArchivedBaumaschineListAdapter.ArchivedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_baumaschinen_item, parent, false);
        context = parent.getContext();
        return new ArchivedBaumaschineListAdapter.ArchivedViewHolder(itemView);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(@NonNull ArchivedBaumaschineListAdapter.ArchivedViewHolder holder, int position) {
        if (baumaschineList != null) {
            Baumaschine current = baumaschineList.get(position);
            holder.baumaschineName.setText(current.getMachineName());
            holder.baumaschineAnzahl.setText(current.getAmount().toString());
            holder.baumaschinePreisPerDay.setText(current.getPricePerDay().toString());
            holder.baumaschinePreisPerWeekend.setText(current.getPricePerWeekend().toString());
            holder.baumaschinePreisPerMonth.setText(current.getPricePerMonth().toString());
            holder.baumaschineAmountOfGas.setText(current.getAmountOfGas());
            holder.baumaschineDegreeOfWear.setText(current.getDegreeOfWear());
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

    public void setBaumaschinen(List<Baumaschine> baumaschineList) {
        this.baumaschineList = baumaschineList;
        notifyDataSetChanged();
    }

    public void deleteBaumaschine(Baumaschine baumaschine) {
        archivedBaumaschinenActivity.deleteBaumaschine(baumaschine);

    }


    class ArchivedViewHolder extends RecyclerView.ViewHolder {
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


        public ArchivedViewHolder(@NonNull View itemView) {
            super(itemView);
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
            modifyButton.setImageResource(R.drawable.ic_baseline_add_24);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            //create OnClickListener to baumaschineName expand the recyclerview after userclick on the name
            baumaschineName.setOnClickListener(v -> {
                Baumaschine baumaschine = baumaschineList.get(getAdapterPosition());
                baumaschine.setExpanded(!baumaschine.getExpanded());
                notifyItemChanged(getAdapterPosition());
            });

            modifyButton.setOnClickListener(v -> {
                archivedBaumaschinenActivity.restoreBaumaschine(baumaschineList.get(getAdapterPosition()).getIdBaumaschine());

            });
            deleteButton.setOnClickListener(v -> {
                Log.d(TAG, "Delete Button clicked");
                Baumaschine baumaschine = baumaschineList.get(getAdapterPosition());
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(context.getResources().getString(R.string.alertDialog));
                builder.setCancelable(true);
                builder.setPositiveButton(context.getResources().getString(R.string.okDialog), (dialog, which) -> deleteBaumaschine(baumaschine));
                builder.setNegativeButton(context.getResources().getString(R.string.cancelDialog), (dialog, which) -> dialog.cancel());
                AlertDialog deleteAlert = builder.create();
                deleteAlert.show();


            });
        }
    }
}





