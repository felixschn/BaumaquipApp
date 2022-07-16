package com.example.rentalApplication.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalApplication.R;
import com.example.rentalApplication.models.Baumaschine;
import com.example.rentalApplication.models.Stuecklisteneintrag;
import com.example.rentalApplication.models.Vertrag;
import com.example.rentalApplication.ui.Vertraege.VertragDetailsActivity;
import com.example.rentalApplication.ui.Vertraege.VertragDetailsClickListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class VertragDetailsListAdapter extends RecyclerView.Adapter<VertragDetailsListAdapter.VertragDetailsViewHolder> {
    private List<Baumaschine> baumaschineVertragDetailsList;
    private List<Stuecklisteneintrag> stuecklisteneintragVertragDetailsList;
    private List<Integer> baumaschineContractAmount;
    private Context context;
    private VertragDetailsActivity vertragDetailsActivity;
    private final VertragDetailsClickListener vertragDetailsClickListener;
    private Baumaschine currentBaumaschine;
    private Vertrag vertrag;


    public VertragDetailsListAdapter(VertragDetailsActivity vertragDetailsActivity, VertragDetailsClickListener vertragDetailsClickListener) {
        this.vertragDetailsActivity = vertragDetailsActivity;
        this.vertragDetailsClickListener = vertragDetailsClickListener;
    }

    @NonNull
    @Override
    public VertragDetailsListAdapter.VertragDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.recyclerview_vertrag_details_item, parent, false);
        return new VertragDetailsViewHolder(itemView, vertragDetailsClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull VertragDetailsViewHolder holder, int position) {
        Baumaschine current = baumaschineVertragDetailsList.get(position);
        holder.vertragDetailsBaumaschineName.setText(current.getMachineName());
        holder.vertragDetailsBaumaschineAnzahl.setText(String.valueOf(baumaschineContractAmount.get(position)));
        holder.baumaschineVertragDetailsOperatingHours.setText(String.valueOf(current.getOperatingHours()));
        holder.baumschineVertragDetailsAmountOfGas.setText(current.getAmountOfGas());
        holder.baumaschineVertragDetailsDegreeOfWear.setText(current.getDegreeOfWear());

        holder.baumaschineVertragDetailsOperatingHours.setEnabled(false);
        holder.baumaschineVertragDetailsOperatingHours.setText("-");
        holder.baumschineVertragDetailsAmountOfGas.setEnabled(false);
        holder.baumschineVertragDetailsAmountOfGas.setText("-");
        holder.baumaschineVertragDetailsDegreeOfWear.setEnabled(false);
        holder.baumaschineVertragDetailsDegreeOfWear.setText("-");

        if(current.getOperatingHours() != null){
            holder.baumaschineVertragDetailsOperatingHours.setEnabled(true);
            holder.baumaschineVertragDetailsOperatingHours.setText(current.getOperatingHours().toString());
        }

        if(current.getAmountOfGas() != null){
            holder.baumschineVertragDetailsAmountOfGas.setEnabled(true);
            holder.baumschineVertragDetailsAmountOfGas.setText(current.getAmountOfGas());
        }

        if(current.getDegreeOfWear() != null){
            holder.baumaschineVertragDetailsDegreeOfWear.setEnabled(true);
            holder.baumaschineVertragDetailsDegreeOfWear.setText(current.getDegreeOfWear());
        }


        boolean isExpanded = baumaschineVertragDetailsList.get(position).getExpanded();
        holder.expandableConstraintLayoutVertagDetails.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
    }


    @Override
    public int getItemCount() {
        if (baumaschineVertragDetailsList != null) {
            return baumaschineVertragDetailsList.size();
        } else {
            return 0;
        }
    }

    public void setBaumaschineVertragDetailsList(List<Stuecklisteneintrag> stuecklisteneintragVertragDetailsList, List<Baumaschine> baumaschineVertragDetailsList, List<Integer> baumaschineContractAmount) {
        this.stuecklisteneintragVertragDetailsList = stuecklisteneintragVertragDetailsList;
        this.baumaschineVertragDetailsList = baumaschineVertragDetailsList;
        this.baumaschineContractAmount = baumaschineContractAmount;
    }

    class VertragDetailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView vertragDetailsBaumaschineName;
        private final TextView textVertragDetailsBaumaschinenAnzahl;
        private final TextView vertragDetailsBaumaschineAnzahl;


        private final ImageButton deleteButtonVertragDetails;
        private final ImageButton modifyButtonVertragDetails;

        private final ConstraintLayout expandableConstraintLayoutVertagDetails;
        private final TextView textVertragDetailsOperatingHours;
        private final TextView baumaschineVertragDetailsOperatingHours;
        private final TextView textVertragDetailsAmountOfGas;
        private final TextView baumschineVertragDetailsAmountOfGas;
        private final TextView textVertragDetailsDegreeOfWear;
        private final TextView baumaschineVertragDetailsDegreeOfWear;

        private final WeakReference<VertragDetailsClickListener> listenerRef;


        public VertragDetailsViewHolder(@NonNull View itemView, VertragDetailsClickListener vertragDetailsClickListener) {
            super(itemView);
            listenerRef = new WeakReference<>(vertragDetailsClickListener);
            vertragDetailsBaumaschineName = itemView.findViewById(R.id.vertragDetailsBaumaschineName);
            textVertragDetailsBaumaschinenAnzahl = itemView.findViewById(R.id.textVertragDetailsBaumaschinenAnzahl);
            vertragDetailsBaumaschineAnzahl = itemView.findViewById(R.id.vertragDetailsBaumaschineAnzahl);


            deleteButtonVertragDetails = itemView.findViewById(R.id.deleteButton);
            modifyButtonVertragDetails = itemView.findViewById(R.id.modifyButton);

            expandableConstraintLayoutVertagDetails = itemView.findViewById(R.id.expandableConstraintLayoutVertagDetails);
            textVertragDetailsOperatingHours = itemView.findViewById(R.id.textVertragDetailsOperatingHours);
            baumaschineVertragDetailsOperatingHours = itemView.findViewById(R.id.baumaschineVertragDetailsOperatingHours);
            textVertragDetailsAmountOfGas = itemView.findViewById(R.id.textVertragDetailsAmountOfGas);
            baumschineVertragDetailsAmountOfGas = itemView.findViewById(R.id.baumschineVertragDetailsAmountOfGas);
            textVertragDetailsDegreeOfWear = itemView.findViewById(R.id.textVertragDetailsDegreeOfWear);
            baumaschineVertragDetailsDegreeOfWear = itemView.findViewById(R.id.baumaschineVertragDetailsDegreeOfWear);

            itemView.setOnClickListener(this);
            deleteButtonVertragDetails.setOnClickListener(this);
            if (((VertragDetailsActivity) context).hideButtonStatus()) {
                deleteButtonVertragDetails.setVisibility(View.GONE);
            } else {
                deleteButtonVertragDetails.setVisibility(View.VISIBLE);
            }
            modifyButtonVertragDetails.setVisibility(View.GONE);


        }

        @Override
        public void onClick(View v) {
            Baumaschine baumaschine = baumaschineVertragDetailsList.get(getAdapterPosition());
            //TODO
            if (v.getId() == deleteButtonVertragDetails.getId()) {
                if (!((VertragDetailsActivity) context).hideButtonStatus()) {
                    deleteButtonVertragDetails.setVisibility(View.VISIBLE);
                }
                if (stuecklisteneintragVertragDetailsList.size() > 1) {
                    //TODO: error message throws ArrayOutOfBounds error and I have no clue why
                /*new AlertDialog.Builder(context)
                        .setTitle("Delete machine")
                        .setMessage("Sure?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                          }
                        })
                                .setNegativeButton("No",null)
                                        .show();*/
                    ((VertragDetailsActivity) context).archiveStuecklisteneintragFromVertrag(stuecklisteneintragVertragDetailsList.get(getAdapterPosition()).getIdStueckList());
                    stuecklisteneintragVertragDetailsList.remove(getAdapterPosition());
                    baumaschineVertragDetailsList.remove(baumaschine);
                    notifyItemRemoved(getAdapterPosition());
                }
                else{
                    Toast.makeText(context, R.string.removeStuecklisteneintrag, Toast.LENGTH_SHORT).show();
                    return;
                }

            } else {
                baumaschine.setExpanded(!baumaschine.getExpanded());
                notifyItemChanged(getAdapterPosition());
            }
            listenerRef.get().onPositionClicked(getAdapterPosition());

        }
    }
}
