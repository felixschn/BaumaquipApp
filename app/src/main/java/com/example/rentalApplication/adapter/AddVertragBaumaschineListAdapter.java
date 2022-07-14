package com.example.rentalApplication.adapter;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalApplication.R;
import com.example.rentalApplication.models.Baumaschine;
import com.example.rentalApplication.models.Stuecklisteneintrag;
import com.example.rentalApplication.persistence.BaumaschinenRepository;
import com.example.rentalApplication.ui.Vertraege.AddVertragActivity;
import com.example.rentalApplication.ui.Vertraege.VertragBaumaschinenListClickListener;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AddVertragBaumaschineListAdapter extends RecyclerView.Adapter<AddVertragBaumaschineListAdapter.AddVertragViewHolder> {
    private static final String TAG = "no machine left";
    private static final String selectedAdded = "added machine";
    private static final String selectedRemoved = "removed machine";
    private int selectedPos = RecyclerView.NO_POSITION;
    private List<Stuecklisteneintrag> stueckliste = new ArrayList<>();
    private final VertragBaumaschinenListClickListener listener;
    private Context context;
    private Application application;
    private final BigDecimal sumRentPrice = new BigDecimal("0");
    private final BaumaschinenRepository baumaschinenRepository = BaumaschinenRepository.getInstance(application);

    public AddVertragBaumaschineListAdapter(Application application, VertragBaumaschinenListClickListener listener, Context context) {
        this.application = application;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public AddVertragViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_addvertragbaumaschinenlist_item, parent, false);
        return new AddVertragViewHolder(itemView, listener, new CustomEditTextListener());
    }


    @Override
    public void onBindViewHolder(@NonNull AddVertragBaumaschineListAdapter.AddVertragViewHolder holder, int position) {
        if (stueckliste != null) {
            int currentMaschineId = stueckliste.get(position).getIdBaumaschine();
            holder.baumaschineName.setText(baumaschinenRepository.getBaumaschineById(currentMaschineId).getMachineName());

            /*get amount which the user has determined in the vertragBaumaschinenRecyclerView,
            therefore call the method getSelectedBaumaschinenAmount() from AddVertragActivity
            which is accessible through ((AddVertragActivity)context)*/
            int selectedAmount = stueckliste.get(position).getAmount();
            holder.amountBaumaschine.setText(String.valueOf(selectedAmount));

            holder.customEditTextListener.updatePosition(holder.getAdapterPosition());
            holder.priceForRent.setText(stueckliste.get(holder.getAdapterPosition()).getPrice().toString().trim() + '€');

        }
    }


    @Override
    public int getItemCount() {
        if (stueckliste != null) {
            return stueckliste.size();
        } else {
            return 0;
        }
    }

    public void addBaumaschinenToVertrag(Baumaschine baumaschine) {
        /*check if the chosen Baumaschine is already in the list, if so then prohibit a duplicate*/
        boolean maschineAlreadyListed = false;
        for (Stuecklisteneintrag stuecklisteneintrag : stueckliste) {
            if (stuecklisteneintrag.getIdBaumaschine() == baumaschine.getIdBaumaschine()) {
                maschineAlreadyListed = true;
                break;
            }
        }
        if (maschineAlreadyListed) {
            Log.d(TAG, "Maschine bereits vorhanden");
            Toast.makeText(context.getApplicationContext(), R.string.machine_already_listed, Toast.LENGTH_SHORT).show();
            return;
        }

        if (((AddVertragActivity) context).checkIfDateIsSet()) {
            Toast.makeText(context, "Bitte zuerst Datum auswählen!", Toast.LENGTH_LONG).show();
            return;
        }

        stueckliste.add(new Stuecklisteneintrag(baumaschine.getIdBaumaschine(), ((AddVertragActivity) context).getSelectedBaumaschinenAmount(), ((AddVertragActivity) context).calcPriceForRent(), ((AddVertragActivity) context).getBeginnVertrag(), ((AddVertragActivity) context).getEndeVertrag(), application));
        notifyDataSetChanged();

    }

    public void removeAddVertragBaumaschine(int position) {
        stueckliste.remove(position);
        notifyItemRemoved(position);
        /*calling recyclerViewVisibility method from AddVertrag Activity (check the context if its an instance of the desired activity),
        to show up the emptyRecyclerViewTextView String, when RecyclerView is empty*/
        if (context instanceof AddVertragActivity) {
            ((AddVertragActivity) context).recyclerViewVisibility();
        }
    }

    //in case that the start or end date is changed after the machine is already in the recyclerview, delete all recyclerview items to prevent false price calculations
    public void clearAddVertragBaumaschinenRecyclerView() {
        if (stueckliste.size() > 0) {
            stueckliste.clear();
            notifyItemRangeRemoved(0, stueckliste.size());
        }


    }

    public List<Stuecklisteneintrag> getStueckliste() {
        return stueckliste;
    }


    class AddVertragViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView baumaschineName;
        private final TextView amountBaumaschine;
        private final EditText priceForRent;
        private final ImageButton deleteButton;
        private final ImageButton modifyButton;
        private final CheckBox insuranceCheckbox;
        private WeakReference<VertragBaumaschinenListClickListener> listenerRef;
        public CustomEditTextListener customEditTextListener;

        public AddVertragViewHolder(@NonNull View itemView, VertragBaumaschinenListClickListener vertragBaumaschinenListClickListener, CustomEditTextListener customEditTextListener) {
            super(itemView);
            listenerRef = new WeakReference<>(vertragBaumaschinenListClickListener);
            this.customEditTextListener = customEditTextListener;
            itemView.setOnClickListener(this);
            baumaschineName = itemView.findViewById(R.id.addVertragBaumaschinenListName);
            amountBaumaschine = itemView.findViewById(R.id.addVertragAmountBaumaschineList);
            priceForRent = itemView.findViewById(R.id.addVertragPriceForRent);
            priceForRent.addTextChangedListener(customEditTextListener);
            priceForRent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            deleteButton = itemView.findViewById(R.id.deleteButton);
            modifyButton = itemView.findViewById(R.id.modifyButton);
            insuranceCheckbox = itemView.findViewById(R.id.insuranceCheckBox);
            insuranceCheckbox.setChecked(false);
            itemView.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
            modifyButton.setVisibility(View.GONE);
            insuranceCheckbox.setOnClickListener(this);
            //sumRentPrice.add(((AddVertragActivity)context).calcPriceForRent());
            //Log.d(TAG, "Summe Preis des VertragssumRentPrice" + sumRentPrice.toString());

        }

        @Override
        public void onClick(View v) {
            listenerRef.get().onPositionClicked(getAdapterPosition());
            if (v.getId() == deleteButton.getId()) {
                removeAddVertragBaumaschine(getAdapterPosition());
                //clear the checkbox if an already deleted Baumaschine is set again in Stueckliste
                insuranceCheckbox.setChecked(false);


            }
            if (v.getId() == insuranceCheckbox.getId()) {
                stueckliste.get(getAdapterPosition()).setInsurance(true);
            }


        }
    }

    private class CustomEditTextListener implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //stueckliste.get(position).setPrice(((AddVertragActivity) context).calcPriceForRent());
            try {
                ((AddVertragActivity) context).clearDiscount();
            } catch (Resources.NotFoundException nf) {

            }


        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                stueckliste.get(position).setPrice(new BigDecimal(s.toString().replace("€", "")));
            } catch (NumberFormatException nf) {
            }
            ((AddVertragActivity) context).calcSumOfRent();


        }

        @Override
        public void afterTextChanged(Editable s) {


        }
    }
}
/*created interface to intercept data from the recyclerview adapter
        - adding an interface variable to the adapter class (in this case: mBaumaschinenListListener)
        - also expand the constructor of the adapter class with an parameter of this interface (in this case:     public AddVertragBaumaschineListAdapter(IGetBaumaschinenFromAdapter mBaumaschinenListListener) {
        - also create an Instance of the interface in the ViewHolder and expand the constructor as well
    }

    */




