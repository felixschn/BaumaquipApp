package com.example.rentalApplication.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.rentalApplication.ui.Kunde.AddKundenActivity;
import com.example.rentalApplication.ui.Kunde.KundenClickListener;
import com.example.rentalApplication.ui.Kunde.KundenFragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class KundenListAdapter extends RecyclerView.Adapter<KundenListAdapter.KundenViewHolder> {
    private List<Kunde> kundeList = new ArrayList<>();
    private Context context;
    private KundenFragment kundenFragment;
    private final KundenClickListener listener;

    public KundenListAdapter(KundenFragment kundenFragment, KundenClickListener clickListener) {
        this.kundenFragment = kundenFragment;
        this.listener = clickListener;
    }

    @NonNull
    @Override
    public KundenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_kunden_item, parent, false);
        context = parent.getContext();
        return new KundenViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull KundenViewHolder holder, int position) {
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

    private void insertNewVertrag() {

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

    class KundenViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
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
        private WeakReference<KundenClickListener> listenerRef;

        public KundenViewHolder(@NonNull View itemView, KundenClickListener kundenClickListener) {
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
            deleteButtonKunde = itemView.findViewById(R.id.deleteButton);

            itemView.setOnClickListener(this);
            kundenName.setOnClickListener(this);
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
                    //create Intent to start new AddKundenActivity for modifying a Kunde object
                    Intent modifyKundeIntent = new Intent(context, AddKundenActivity.class);
                    //putting extra info to intent for differentiating in AddKundenActivity which activity has called and giving the RowId from the clicked object in the recyclerview
                    modifyKundeIntent.putExtra("kundeRowId", kunde.getRowid());
                    modifyKundeIntent.putExtra("Class", "KundenListAdapter");
                    context.startActivity(modifyKundeIntent);
                }
            });

            deleteButtonKunde.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    kundenFragment.archiveKunde(kundeList.get(getAdapterPosition()).getRowid());
                }
            });*/
        }

        @Override
        public void onClick(View v) {
            Kunde kunde = kundeList.get(getAdapterPosition());

            if (v.getId() == modifyButtonKunde.getId()) {
                //create Intent to start new AddKundenActivity for modifying a Kunde object
                Intent modifyKundeIntent = new Intent(context, AddKundenActivity.class);
                //putting extra info to intent for differentiating in AddKundenActivity which activity has called and giving the RowId from the clicked object in the recyclerview
                modifyKundeIntent.putExtra("kundeRowId", kunde.getIdKunde());
                modifyKundeIntent.putExtra("Class", "KundenListAdapter");
                context.startActivity(modifyKundeIntent);

            }
            if (v.getId() == deleteButtonKunde.getId()) {
                kundenFragment.archiveKunde(kundeList.get(getAdapterPosition()).getIdKunde());
            } else {
                kunde.setExpanded(!kunde.getExpanded());
                notifyItemChanged(getAdapterPosition());
            }
            listenerRef.get().onPositionClicked(getAdapterPosition());

        }
    }
}
