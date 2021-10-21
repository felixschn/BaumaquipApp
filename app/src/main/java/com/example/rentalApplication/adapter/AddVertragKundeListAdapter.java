package com.example.rentalApplication.adapter;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalApplication.R;
import com.example.rentalApplication.models.Kunde;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddVertragKundeListAdapter extends RecyclerView.Adapter<AddVertragKundeListAdapter.AddVertragKundeViewHolder> {
    private List<Kunde> kundeList = new ArrayList<>();
    private HashMap<Integer, Kunde> kundeHashMap = new HashMap<>();
    IGetKundeFromAdapter mKundeListListener;
    private int selectedPos = RecyclerView.NO_POSITION;
    private int maxKunde = 0;


    @NonNull
    @Override

    public AddVertragKundeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_addvertragkunde_item, parent, false);
        return new AddVertragKundeViewHolder(itemView, mKundeListListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AddVertragKundeListAdapter.AddVertragKundeViewHolder holder, int position) {
        if (kundeList != null) {
            Kunde current = kundeList.get(position);
            holder.kundeName.setText(current.getName());
            holder.kundeLocation.setText(current.getLocation());


        }
        holder.itemView.setSelected(selectedPos == position);
    }


    @Override
    public int getItemCount() {
        if (kundeList != null) {
            return kundeList.size();
        } else {
            return 0;
        }
    }

    public AddVertragKundeListAdapter(IGetKundeFromAdapter mKundeListListener) {
        this.mKundeListListener = mKundeListListener;
    }


    /*method to set all Kunden to the recyclerview
     *   called in AddVertragActivity*/
    public void setAddVertragKunden(List<Kunde> kundeList) {
        this.kundeList = kundeList;
        notifyDataSetChanged();

    }

    public Kunde getKunde(int postion) {
        return kundeList.get(postion);
    }

    class AddVertragKundeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView kundeName, kundeLocation;
        private SparseBooleanArray selectedItems = new SparseBooleanArray();
        IGetKundeFromAdapter mKundenListListener;
        //private int maxKunde = 0;

        public AddVertragKundeViewHolder(@NonNull View itemView, IGetKundeFromAdapter mKundenListListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            kundeName = itemView.findViewById(R.id.addVertragNameKunde);
            kundeLocation = itemView.findViewById(R.id.addVertragLocationKunde);
            this.mKundenListListener = mKundenListListener;
        }

        @Override
        public void onClick(View v) {

            if (selectedItems.get(getAdapterPosition(), false)) {
                selectedItems.delete(getAdapterPosition());
                itemView.setSelected(false);
                kundeHashMap.remove(getAdapterPosition());
                mKundeListListener.getKundeFromAdapter(kundeHashMap);
                maxKunde = maxKunde - 1;
                System.out.println("maxKunde decrementiert: " + maxKunde);
            } else {
                if (maxKunde == 0) {
                    selectedItems.put(getAdapterPosition(), true);
                    itemView.setSelected(true);
                    kundeHashMap.put(getAdapterPosition(), kundeList.get(getAdapterPosition()));
                    mKundeListListener.getKundeFromAdapter(kundeHashMap);
                    maxKunde = maxKunde + 1;
                    System.out.println("maxKunde incrementiert: " + maxKunde);
                }
                Toast.makeText(v.getContext(), "Ein Kunde wurde bereits gewählt", Toast.LENGTH_LONG).show();

            }
        }
    }


    public interface IGetKundeFromAdapter {
        void getKundeFromAdapter(HashMap<Integer, Kunde> kundeList);
    }
}
