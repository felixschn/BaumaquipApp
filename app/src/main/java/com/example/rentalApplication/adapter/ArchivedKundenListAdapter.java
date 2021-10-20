package com.example.rentalApplication.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalApplication.R;
import com.example.rentalApplication.models.Kunde;
import com.example.rentalApplication.ui.Kunde.ArchivedKundenActivity;
import com.example.rentalApplication.ui.Kunde.KundenClickListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class ArchivedKundenListAdapter extends RecyclerView.Adapter<ArchivedKundenListAdapter.ArchivedKundeViewHolder> {
    private List<Kunde> kundeList = new ArrayList<>();
    private Context context;
    private static final String TAG = "ArchivedKundenListAdapter.java";
    private final ArchivedKundenActivity archivedKundenActivity;
    private final KundenClickListener kundenClickListener;

    public ArchivedKundenListAdapter(ArchivedKundenActivity archivedKundenActivity, KundenClickListener listener) {
        this.archivedKundenActivity = archivedKundenActivity;
        this.kundenClickListener = listener;
    }

    @NonNull
    @Override
    public ArchivedKundenListAdapter.ArchivedKundeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.recyclerview_kunden_item, parent, false);
        return new ArchivedKundenListAdapter.ArchivedKundeViewHolder(itemView, kundenClickListener);

    }

    @Override
    public void onBindViewHolder(@NonNull ArchivedKundeViewHolder holder, int position) {
        if (kundeList != null) {
            Kunde current = kundeList.get(position);
            holder.kundenName.setText(current.getName());
            holder.kundenStrasse.setText(current.getStreetName());
            holder.kundenStrassenNummer.setText(current.getStreetNumber());
            holder.kundenZip.setText(current.getZip());
            holder.kundenOrt.setText(current.getLocation());
            holder.kundenTelefonnummer.setText(current.getTelefonNumber());
            holder.kundenBaustelle.setText(current.getConstructionSide());
            holder.kundenAnsprechpartner.setText(current.getContactPerson());
            holder.kundenEmail.setText(current.getEmail());

            //retrieve information if kunden object is expanded in the view
            boolean isExpanded = kundeList.get(position).getExpanded();
            //set Visibility to visible when isExpanded = true and to invisible when isExpanded is false
            holder.expandableConstraintLayoutKunde.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (kundeList != null) {
            return kundeList.size();
        } else {
            return 0;
        }
    }

    public void setKunden(List<Kunde> kundenList) {
        this.kundeList = kundenList;
        notifyItemInserted(kundenList.size()-1);
    }

    public void deleteKunde(Kunde kunde) {
        archivedKundenActivity.deleteKunde(kunde);
    }


    class ArchivedKundeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView kundenName;
        private final TextView kundenStrasse;
        private final TextView kundenStrassenNummer;
        private final TextView kundenZip;
        private final TextView kundenOrt;
        private final TextView kundenTelefonnummer;
        private final TextView kundenEmail;
        private final TextView kundenBaustelle;
        private final TextView kundenAnsprechpartner;
        private final ConstraintLayout expandableConstraintLayoutKunde;
        private final ImageButton modifyButtonKunde;
        private final ImageButton deleteButtonKunde;
        private final WeakReference<KundenClickListener> listenerRef;

        public ArchivedKundeViewHolder(@NonNull View itemView, KundenClickListener kundenClickListener) {
            super(itemView);
            listenerRef = new WeakReference<>(kundenClickListener);
            kundenName = itemView.findViewById(R.id.textKundeName);
            kundenStrasse = itemView.findViewById(R.id.textKundeStreetName);
            kundenStrassenNummer = itemView.findViewById(R.id.textKundeStreetNumber);
            kundenZip = itemView.findViewById(R.id.textKundenZip);
            kundenOrt = itemView.findViewById(R.id.textKundeLocation);
            kundenTelefonnummer = itemView.findViewById(R.id.textKundeTelefonNumber);
            kundenEmail = itemView.findViewById(R.id.textKundeEmail);
            kundenBaustelle = itemView.findViewById(R.id.textKundeConstructionSide);
            kundenAnsprechpartner = itemView.findViewById(R.id.textKundeContactPerson);
            expandableConstraintLayoutKunde = itemView.findViewById(R.id.expandableConstraintLayoutKunde);
            modifyButtonKunde = itemView.findViewById(R.id.modifyButton);
            modifyButtonKunde.setImageResource(R.drawable.ic_baseline_add_24);
            deleteButtonKunde = itemView.findViewById(R.id.deleteButton);

            itemView.setOnClickListener(this);
            modifyButtonKunde.setOnClickListener(this);
            deleteButtonKunde.setOnClickListener(this);

            /*kundenName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Kunde kunde = kundeList.get(getAdapterPosition());
                    kunde.setExpanded(!kunde.getExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });

            modifyButtonKunde.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Kunde kunde = kundeList.get(getAdapterPosition());
                    archivedKundenActivity.restoreKunde(kundeList.get(getAdapterPosition()).getRowid());
                }
            });

            deleteButtonKunde.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Kunde kunde = kundeList.get(getAdapterPosition());
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage(context.getResources().getString(R.string.alertDialog));
                    builder.setCancelable(true);
                    builder.setPositiveButton(context.getResources().getString(R.string.okDialog), (dialog, which) -> deleteKunde(kunde));
                    builder.setNegativeButton(context.getResources().getString(R.string.cancelDialog), (dialog, which) -> dialog.cancel());
                    AlertDialog deleteAlert = builder.create();
                    deleteAlert.show();


                }
            });*/
        }

        @Override
        public void onClick(View v) {
            Kunde kunde = kundeList.get(getAdapterPosition());

            if (v.getId() == modifyButtonKunde.getId()) {
                archivedKundenActivity.restoreKunde(kundeList.get(getAdapterPosition()).getIdKunde());
            }
            if (v.getId() == deleteButtonKunde.getId()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(context.getResources().getString(R.string.alertDialog));
                builder.setCancelable(true);
                builder.setPositiveButton(context.getResources().getString(R.string.okDialog), (dialog, which) -> deleteKunde(kunde));
                builder.setNegativeButton(context.getResources().getString(R.string.cancelDialog), (dialog, which) -> dialog.cancel());
                AlertDialog deleteAlert = builder.create();
                deleteAlert.show();
            } else {
                kunde.setExpanded(!kunde.getExpanded());
                notifyItemChanged(getAdapterPosition());
            }
            listenerRef.get().onPositionClicked(getAdapterPosition());
        }
    }
}
