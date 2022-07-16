package com.example.rentalApplication.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalApplication.R;
import com.example.rentalApplication.models.Vertrag;
import com.example.rentalApplication.ui.Kunde.ModifyKundenViewModel;
import com.example.rentalApplication.ui.Vertraege.ArchivedVertragActivity;
import com.example.rentalApplication.ui.Vertraege.VertragClickListener;
import com.example.rentalApplication.ui.Vertraege.VertragDetailsActivity;

import java.lang.ref.WeakReference;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ArchivedVertragListAdapter extends RecyclerView.Adapter<ArchivedVertragListAdapter.ArchivedVertragViewHolder> {
    private LayoutInflater mInflater;
    private List<Vertrag> vertragList = new ArrayList<>();
    private final VertragClickListener vertragClickListener;
    private Context context;
    private ArchivedVertragActivity archivedVertragActivity;
    private ModifyKundenViewModel modifyKundenViewModel;



    public ArchivedVertragListAdapter(VertragClickListener listener, ArchivedVertragActivity archivedVertragActivity) {
        this.vertragClickListener = listener;
        this.archivedVertragActivity = archivedVertragActivity;
        this.modifyKundenViewModel = archivedVertragActivity.modifyKundenViewModel;
    }

    @NonNull
    @Override
    public ArchivedVertragListAdapter.ArchivedVertragViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_vertrag_item, parent, false);
        context = parent.getContext();
        return new ArchivedVertragListAdapter.ArchivedVertragViewHolder(itemView, vertragClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ArchivedVertragListAdapter.ArchivedVertragViewHolder holder, int position) {
        //kundenViewModel = new ViewModelProvider().get(BaumaschinenViewModel.class);
        if (vertragList != null) {
            Vertrag current = vertragList.get(position);
            holder.archiveVertragId.setText(String.valueOf(current.getIdVertrag()));
            archivedVertragActivity.
            modifyKundenViewModel = new ViewModelProvider((ArchivedVertragActivity)context).get(ModifyKundenViewModel.class);
            String kundenName = modifyKundenViewModel.loadKundeById(current.getIdKunde()).getName();
            holder.archivedVertragKunde.setText(kundenName);
            holder.archivedVertragStartLeihe.setText(setDate(current.getBeginnVertrag()));
            holder.archivedVertragEndeLeihe.setText(setDate(current.getEndeVertrag()));

        }
    }

    @Override
    public int getItemCount() {
        if (vertragList != null) {
            return vertragList.size();
        } else {
            return 0;
        }
    }

    public void setVertrag(List<Vertrag> vertragList) {
        this.vertragList = vertragList;
        notifyDataSetChanged();
    }

    //method to format LocalDate from StartLeihe and EndLeihe to String for TextView
    public String setDate(LocalDate localDate) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yy", Locale.GERMAN);
        return localDate.format(dateFormat);
    }

    public void deleteVertrag(Vertrag vertrag) {
        archivedVertragActivity.deleteArchivedVertrag(vertrag);
    }
    //public void restoreVertrag(int id){archivedVertragActivity.restoreVertrag(id);}

    class ArchivedVertragViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView archiveVertragId;
        private final TextView archivedVertragKunde;
        private final TextView archivedVertragStartLeihe;
        private final TextView archivedVertragEndeLeihe;
        private final ImageButton deleteButton;
        private final ImageButton modifyButton;
        private WeakReference<VertragClickListener> listenerRef;


        public ArchivedVertragViewHolder(@NonNull View itemView, VertragClickListener vertragClickListener) {
            super(itemView);
            listenerRef = new WeakReference<>(vertragClickListener);
            archiveVertragId = itemView.findViewById(R.id.vertragIdTextView);
            archivedVertragKunde = itemView.findViewById(R.id.vertragKundeTextView);
            archivedVertragStartLeihe = itemView.findViewById(R.id.vertragStartLeihe);
            archivedVertragEndeLeihe = itemView.findViewById(R.id.vertragEndLeihe);
            //expandableConstraintLayout = itemView.findViewById(R.id.expandableConstraintLayoutKunde);

            deleteButton = itemView.findViewById(R.id.deleteButton);
            modifyButton = itemView.findViewById(R.id.modifyButton);

            itemView.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
            modifyButton.setOnClickListener(this);
            modifyButton.setImageResource(R.drawable.ic_baseline_search_24);


        }

        @Override
        public void onClick(View v) {
            Vertrag vertrag = vertragList.get(getAdapterPosition());

            if (v.getId() == modifyButton.getId()) {
                Intent modifyVertragIntent = new Intent(context, VertragDetailsActivity.class);
                modifyVertragIntent.putExtra("vertragRowId", vertrag.getIdVertrag());
                modifyVertragIntent.putExtra("Class", "ArchivedVertragListAdapter");
                context.startActivity(modifyVertragIntent);
            }

            if (v.getId() == deleteButton.getId()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(context.getResources().getString(R.string.alertDialog));
                builder.setCancelable(true);
                builder.setPositiveButton(context.getResources().getString(R.string.okDialog), (dialog, which) -> deleteVertrag(vertrag));
                builder.setNegativeButton(context.getResources().getString(R.string.cancelDialog), (dialog, which) -> dialog.cancel());
                AlertDialog deleteAlert = builder.create();
                deleteAlert.show();
            }
            listenerRef.get().onPositionClicked(getAdapterPosition());
        }
    }
}
