package com.example.rentalApplication.adapter;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalApplication.R;
import com.example.rentalApplication.models.Baumaschine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddVertragBaumaschineListAdapter extends RecyclerView.Adapter<AddVertragBaumaschineListAdapter.AddVertragViewHolder> {
    private List<Baumaschine> baumaschineList = new ArrayList<>();
    private HashMap<Integer, Baumaschine> baumaschineHashMap = new HashMap<>();
    IGetBaumaschinenFromAdapter mBaumaschinenListListener;
    private static final String TAG = "no machine left";
    private static final String selectedAdded = "added machine";
    private static final String selectedRemoved = "removed machine";
    private int selectedPos = RecyclerView.NO_POSITION;


    @NonNull
    @Override
    public AddVertragViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_addvertragbaumaschine_item, parent, false);
        return new AddVertragViewHolder(itemView, mBaumaschinenListListener);
    }

    public AddVertragBaumaschineListAdapter(IGetBaumaschinenFromAdapter mBaumaschinenListListener) {
        this.mBaumaschinenListListener = mBaumaschinenListListener;
    }

    @Override
    public void onBindViewHolder(@NonNull AddVertragBaumaschineListAdapter.AddVertragViewHolder holder, int position) {
        if (baumaschineList != null) {
            Baumaschine current = baumaschineList.get(position);
            if (current.getAmount() < 1) {
                baumaschineList.remove(current);
            } else {
                holder.baumaschineName.setText(current.getMachineName().toString());
                holder.amountBaumaschine.setText(current.getAmount().toString());
                Log.d(TAG, current.getMachineName() + " schon verliehen");
            }
        }
        holder.itemView.setSelected(selectedPos == position);
    }

    //method to get the object Baumaschine when an selection is set
    public Baumaschine getBaumaschine(int position) {
        return baumaschineList.get(position);
    }

    public HashMap<Integer, Baumaschine> sendBaumaschineList() {
        return sendBaumaschineList();
    }

    @Override
    public int getItemCount() {
        if (baumaschineList != null) {
            return baumaschineList.size();
        } else {
            return 0;
        }
    }

    public void setAddVertragBaumaschinen(List<Baumaschine> baumaschineList) {
        this.baumaschineList = baumaschineList;
        notifyDataSetChanged();
    }


    class AddVertragViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView baumaschineName;
        private final TextView amountBaumaschine;
        private SparseBooleanArray selectedItems = new SparseBooleanArray();
        IGetBaumaschinenFromAdapter mBaumaschinenListListener;

        public AddVertragViewHolder(@NonNull View itemView, IGetBaumaschinenFromAdapter mBaumaschinenListListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            baumaschineName = itemView.findViewById(R.id.addVertragBaumaschineName);
            amountBaumaschine = itemView.findViewById(R.id.addVertragAmountBaumaschine);
            this.mBaumaschinenListListener = mBaumaschinenListListener;

        }


        //click on an item in recyclerview and highlighting it
        @Override
        public void onClick(View v) {

            //is called when selection is removed
            if (selectedItems.get(getAdapterPosition(), false)) {
                selectedItems.delete(getAdapterPosition());
                itemView.setSelected(false);
                System.out.println("Zweiter Klick");
                baumaschineHashMap.remove(getAdapterPosition());
                Log.d(selectedRemoved, "" + baumaschineHashMap.get(getAdapterPosition()));
                mBaumaschinenListListener.getBaumaschinenFromAdapter(baumaschineHashMap);


                //is called when selection is set
            } else {

                selectedItems.put(getAdapterPosition(), true);
                itemView.setSelected(true);
                System.out.println(baumaschineName.getText());
                baumaschineHashMap.put(getAdapterPosition(), baumaschineList.get(getAdapterPosition()));
                Log.d(selectedAdded, "" + baumaschineHashMap.size());
                mBaumaschinenListListener.getBaumaschinenFromAdapter(baumaschineHashMap);


            }

        }


    }

    /*created interface to intercept data from the recyclerview adapter
        - adding an interface variable to the adapter class (in this case: mBaumaschinenListListener)
        - also expand the constructor of the adapter class with an parameter of this interface (in this case:     public AddVertragBaumaschineListAdapter(IGetBaumaschinenFromAdapter mBaumaschinenListListener) {
        - also create an Instance of the interface in the ViewHolder and expand the constructor as well
    }

    * */
    public interface IGetBaumaschinenFromAdapter {
        void getBaumaschinenFromAdapter(HashMap<Integer, Baumaschine> baumaschineList);
    }

}
