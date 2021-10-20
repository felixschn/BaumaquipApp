
package com.example.rentalApplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalApplication.R;
import com.example.rentalApplication.models.Baumaschine;
import com.example.rentalApplication.ui.Vertraege.AddVertragActivity;
import com.example.rentalApplication.ui.Vertraege.VertragBaumaschinenListClickListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class AddVertragBaumaschineListAdapter extends RecyclerView.Adapter<AddVertragBaumaschineListAdapter.AddVertragViewHolder> {
    private static final String TAG = "no machine left";
    private static final String selectedAdded = "added machine";
    private static final String selectedRemoved = "removed machine";
    private int selectedPos = RecyclerView.NO_POSITION;
    private List<Baumaschine> baumaschineList = new ArrayList<>();
    private final VertragBaumaschinenListClickListener listener;
    private Context context;

    public AddVertragBaumaschineListAdapter(VertragBaumaschinenListClickListener listener, Context context) {
        this.listener = listener;
        this.context = context;

    }

    @NonNull
    @Override
    public AddVertragViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_addvertragbaumaschinenlist_item, parent, false);
        return new AddVertragViewHolder(itemView, listener);
    }


    @Override
    public void onBindViewHolder(@NonNull AddVertragBaumaschineListAdapter.AddVertragViewHolder holder, int position) {
        if (baumaschineList != null) {
            Baumaschine current = baumaschineList.get(position);
            holder.baumaschineName.setText(current.getMachineName());

            /*get amount which the user has determined in the vertragBaumaschinenRecyclerView,
            therefor call the method getSelectedBaumaschinenAmount() from AddVertragActivity
            which is accessible through ((AddVertragActivity)context)*/
            int selectedAmount = ((AddVertragActivity)context).getSelectedBaumaschinenAmount();
            String selectedAmountToString = String.valueOf(selectedAmount);
            holder.amountBaumaschine.setText(selectedAmountToString);
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

    public void setAddVertragBaumaschinen(Baumaschine baumaschine) {

        /*check if the chosen Baumaschine is already in the list, if so then prohibit a duplicate*/
        if(baumaschineList.contains(baumaschine)){
            Log.d(TAG,"Maschine bereits vorhanden");
            Toast.makeText(context.getApplicationContext(), "Maschine bereits in Liste vorhanden!", Toast.LENGTH_SHORT).show();
            return;
        }
        baumaschineList.add(baumaschine);
        notifyDataSetChanged();

    }

    public void removeAddVertragBaumaschine(int position){
        baumaschineList.remove(position);
        notifyItemRemoved(position);
        /*calling recyclerViewVisibility method from AddVertrag Activity (check the context if its an instance of the desired activity),
        to show up the emptyRecyclerViewTextView String, when RecyclerView is empty*/
        if(context instanceof AddVertragActivity){
            ((AddVertragActivity)context).recyclerViewVisibility();
        }
    }

    public List<Baumaschine> getBaumaschineList(){
        return baumaschineList;
    }


    class AddVertragViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView baumaschineName;
        private final TextView amountBaumaschine;
        private final ImageButton deleteButton;
        private final ImageButton modifyButton;
        private WeakReference<VertragBaumaschinenListClickListener> listenerRef;


        public AddVertragViewHolder(@NonNull View itemView, VertragBaumaschinenListClickListener vertragBaumaschinenListClickListener) {
            super(itemView);
            listenerRef = new WeakReference<>(vertragBaumaschinenListClickListener);
            itemView.setOnClickListener(this);
            baumaschineName = itemView.findViewById(R.id.addVertragBaumaschinenListName);
            amountBaumaschine = itemView.findViewById(R.id.addVertragAmountBaumaschineList);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            modifyButton = itemView.findViewById(R.id.modifyButton);
            itemView.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
            modifyButton.setVisibility(View.GONE);


        }

        @Override
        public void onClick(View v) {
            listenerRef.get().onPositionClicked(getAdapterPosition());

            if(v.getId() == deleteButton.getId()){
                removeAddVertragBaumaschine(getAdapterPosition());
            }

        }
    }
}
/*created interface to intercept data from the recyclerview adapter
        - adding an interface variable to the adapter class (in this case: mBaumaschinenListListener)
        - also expand the constructor of the adapter class with an parameter of this interface (in this case:     public AddVertragBaumaschineListAdapter(IGetBaumaschinenFromAdapter mBaumaschinenListListener) {
        - also create an Instance of the interface in the ViewHolder and expand the constructor as well
    }

    */




