package com.example.rentalApplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalApplication.R;
import com.example.rentalApplication.models.Converters;
import com.example.rentalApplication.models.Vertrag;
import com.example.rentalApplication.ui.Vertraege.VertragClickListener;
import com.example.rentalApplication.ui.Vertraege.VertragFragment;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VertragListAdapter extends RecyclerView.Adapter<VertragListAdapter.VertragViewHolder> {
    private LayoutInflater mInflater;
    private List<Vertrag> vertragList = new ArrayList<>();
    private final VertragClickListener listener;
    private VertragFragment vertragFragment;
    private TextView vertragBaumaschinenTextView;

    public VertragListAdapter(VertragClickListener listener, VertragFragment vertragFragment) {
        this.listener = listener;
        this.vertragFragment = vertragFragment;
    }

    @NonNull
    @Override
    public VertragViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_vertrag_item, parent, false);
        return new VertragViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull VertragViewHolder holder, int position) {
        if (vertragList != null) {
            Vertrag current = vertragList.get(position);
            String intToString = String.valueOf(current.getIdVertrag());
            holder.vertragId.setText(intToString);
            holder.vertragKunde.setText(current.getVertragKunde());
            holder.vertragStartLeihe.setText(setDate(current.getBeginnLeihe()));
            holder.vertragEndeLeihe.setText(setDate(current.getEndeLeihe()));

            boolean isExpanded = vertragList.get(position).getExpanded();
            holder.expandableConstraintLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

            /*List<Stuecklisteneintrag> stuecklisteneintragList = new ArrayList<>();
            for(int i = 0; i < stuecklisteneintragList.size(); i++){
                stuecklisteneintragList.get(i).getIdStuecklist;
            }*/
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
        Converters converters = new Converters();
        converters.dateToString(localDate);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy", Locale.GERMAN);
        return dateFormat.format(localDate);
    }

    class VertragViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView vertragId;
        private final TextView vertragKunde;
        private final TextView vertragStartLeihe;
        private final TextView vertragEndeLeihe;
        private final ImageButton deleteButton;
        private final ImageButton modifyButton;
        private final ConstraintLayout expandableConstraintLayout;
        private WeakReference<VertragClickListener> listenerRef;
        private RecyclerView vertragBaumaschinenRecyclerView;

        public VertragViewHolder(@NonNull View itemView, VertragClickListener vertragClickListener) {
            super(itemView);
            listenerRef = new WeakReference<>(vertragClickListener);
            vertragId = itemView.findViewById(R.id.vertragIdTextView);
            vertragKunde = itemView.findViewById(R.id.vertragKundeTextView);
            vertragStartLeihe = itemView.findViewById(R.id.vertragStartLeihe);
            vertragEndeLeihe = itemView.findViewById(R.id.vertragEndLeihe);
            expandableConstraintLayout = itemView.findViewById(R.id.expandableConstraintLayoutKunde);
            vertragBaumaschinenRecyclerView = itemView.findViewById(R.id.vertragBaumaschinenRecyclerView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            modifyButton = itemView.findViewById(R.id.modifyButton);

            itemView.setOnClickListener(this);
            deleteButton.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            Vertrag vertrag = vertragList.get(getAdapterPosition());

            if (v.getId() == deleteButton.getId()) {

            }
            if (v.getId() == modifyButton.getId()) {
            } else {
                vertragList.get(getAdapterPosition()).setExpanded(!vertrag.getExpanded());
                notifyItemChanged(getAdapterPosition());
            }

            listenerRef.get().onPositionClicked(getAdapterPosition());
        }
    }
}
