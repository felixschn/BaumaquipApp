package com.example.rentalApplication.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalApplication.R;
import com.example.rentalApplication.models.Converters;
import com.example.rentalApplication.models.Vertrag;
import com.example.rentalApplication.ui.Baumaschine.BaumaschinenViewModel;
import com.example.rentalApplication.ui.Kunde.KundenViewModel;
import com.example.rentalApplication.ui.Kunde.ModifyKundenViewModel;
import com.example.rentalApplication.ui.Vertraege.AddVertragActivity;
import com.example.rentalApplication.ui.Vertraege.VertragClickListener;
import com.example.rentalApplication.ui.Vertraege.VertragDetailsActivity;
import com.example.rentalApplication.ui.Vertraege.VertragFragment;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VertragListAdapter extends RecyclerView.Adapter<VertragListAdapter.VertragViewHolder> {
    private LayoutInflater mInflater;
    private List<Vertrag> vertragList = new ArrayList<>();
    private final VertragClickListener listener;
    private VertragFragment vertragFragment;
    private Context context;
    private ModifyKundenViewModel modifyKundenViewModel;


    public VertragListAdapter(VertragClickListener listener, VertragFragment vertragFragment) {
        this.listener = listener;
        this.vertragFragment = vertragFragment;
    }

    @NonNull
    @Override
    public VertragViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_vertrag_item, parent, false);
        context = parent.getContext();
        return new VertragViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull VertragViewHolder holder, int position) {
        if (vertragList != null) {
            Vertrag current = vertragList.get(position);
            holder.vertragId.setText(String.valueOf(current.getIdVertrag()));

            //database call to retrieve kunden name from kunden id
            modifyKundenViewModel = new ViewModelProvider(vertragFragment.requireActivity()).get(ModifyKundenViewModel.class);
            String kundenName = modifyKundenViewModel.loadKundeById(current.getIdKunde()).getName();
            holder.vertragKunde.setText(kundenName);

            holder.vertragStartLeihe.setText(setDate(current.getBeginnVertrag()));
            holder.vertragEndeLeihe.setText(setDate(current.getEndeVertrag()));

            if(LocalDate.now().equals(current.getEndeVertrag().minusDays(2))){
                holder.vertragEndeLeihe.setBackgroundColor(ContextCompat.getColor(context, R.color.baumaquip_main_color));
            }
            if(current.getEndeVertrag().isBefore(LocalDate.now())){
                holder.vertragEndeLeihe.setBackgroundColor(ContextCompat.getColor(context, R.color.rent_expire_color));
            }


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

    class VertragViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView vertragId;
        private final TextView vertragKunde;
        private final TextView vertragStartLeihe;
        private final TextView vertragEndeLeihe;
        private final ImageButton deleteButton;
        private final ImageButton modifyButton;
        private WeakReference<VertragClickListener> listenerRef;


        public VertragViewHolder(@NonNull View itemView, VertragClickListener vertragClickListener) {
            super(itemView);
            listenerRef = new WeakReference<>(vertragClickListener);
            vertragId = itemView.findViewById(R.id.vertragIdTextView);
            vertragKunde = itemView.findViewById(R.id.vertragKundeTextView);
            vertragStartLeihe = itemView.findViewById(R.id.vertragStartLeihe);
            vertragEndeLeihe = itemView.findViewById(R.id.vertragEndLeihe);
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
                modifyVertragIntent.putExtra("Class", "VertragListAdapter");
                context.startActivity(modifyVertragIntent);

            }
            if (v.getId() == deleteButton.getId()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(context.getResources().getString(R.string.alertDialog));
                builder.setCancelable(true);
                builder.setPositiveButton(context.getResources().getString(R.string.okDialog), (dialog, which) -> vertragFragment.archiveVertrag(vertragList.get(getAdapterPosition()).getIdVertrag()));
                builder.setNegativeButton(context.getResources().getString(R.string.cancelDialog), (dialog, which) -> dialog.cancel());
                AlertDialog deleteAlert = builder.create();
                deleteAlert.show();

            } else {
                notifyItemChanged(getAdapterPosition());
            }
            listenerRef.get().onPositionClicked(getAdapterPosition());
        }
    }
}
