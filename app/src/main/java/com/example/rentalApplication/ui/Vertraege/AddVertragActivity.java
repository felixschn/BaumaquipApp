package com.example.rentalApplication.ui.Vertraege;

import static java.lang.Math.max;
import static java.lang.Math.toIntExact;
import static java.time.temporal.ChronoUnit.DAYS;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalApplication.R;
import com.example.rentalApplication.adapter.AddVertragBaumaschineListAdapter;
import com.example.rentalApplication.models.Baumaschine;
import com.example.rentalApplication.models.Converters;
import com.example.rentalApplication.models.Kunde;
import com.example.rentalApplication.models.Stuecklisteneintrag;
import com.example.rentalApplication.models.Vertrag;
import com.example.rentalApplication.ui.Baumaschine.BaumaschinenViewModel;
import com.example.rentalApplication.ui.Kunde.KundenViewModel;
import com.example.rentalApplication.ui.Vertraege.Spinner.CustomBaumaschinenAdapter;
import com.example.rentalApplication.ui.Vertraege.Spinner.CustomKundeAdapter;
import com.example.rentalApplication.ui.Vertraege.Stuecklisteneintrag.AddStuecklisteneintragViewModel;
import com.example.rentalApplication.ui.Vertraege.Stuecklisteneintrag.AsyncTaskStuecklisteneintragIdResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class AddVertragActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, VertragBaumaschinenListClickListener, View.OnClickListener {

    private BaumaschinenViewModel baumaschinenViewModel;
    private KundenViewModel kundenViewModel;
    private AddStuecklisteneintragViewModel addStuecklisteneintragViewModel;
    private AddVertragViewModel addVertragViewModel;
    private EditText beginnVertrag, endeVertrag;
    private LocalDate begin, end;
    private Button addVertragButton;
    private static final String TAG = "AddVertragActivity";
    private ImageButton increaseButton, decreaseButton, addBaumaschinenListButton;
    private TextView amountTextView, emptyRecyclerViewTextView, announceRecyclerView;
    int amountInt, maxAmount;
    private CustomBaumaschinenAdapter customBaumaschinenAdapter;
    private RecyclerView recyclerView;
    private AddVertragBaumaschineListAdapter addVertragBaumaschineListAdapter;
    private Spinner baumaschinenSpinner, kundenSpinner;
    private Baumaschine selectedBaumaschineFromSpinner;
    private Kunde selectedKundeFromSpinner;
    private long stuecklisteneintragId;
    private AsyncTaskStuecklisteneintragIdResponse asyncTaskStuecklisteneintragIdResponse;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vertrag);


        addStuecklisteneintragViewModel = new ViewModelProvider(this).get(AddStuecklisteneintragViewModel.class);
        addVertragViewModel = new ViewModelProvider(this).get(AddVertragViewModel.class);

        //create Spinner objects and link them to the xml objects --> tutorial: https://abhiandroid.com/ui/custom-spinner-examples.html
        baumaschinenSpinner = findViewById(R.id.spinnerBaumaschinen);
        baumaschinenSpinner.setOnItemSelectedListener(this);

        kundenSpinner = findViewById(R.id.spinnerKunden);
        kundenSpinner.setOnItemSelectedListener(this);

        //create new customAdapter object and link Spinner with adapter
        customBaumaschinenAdapter = new CustomBaumaschinenAdapter(this);
        baumaschinenSpinner.setAdapter(customBaumaschinenAdapter);


        //create new ViewModel and get all instances of the database entry
        baumaschinenViewModel = new ViewModelProvider(this).get(BaumaschinenViewModel.class);
        baumaschinenViewModel.getAllBaumaschinenForSpinner().observe(this, baumaschines -> {
            //check for machines who are currently not available and remove them from the list so that the spinner can't show them
            for(int i = 0; i <= baumaschines.size(); i++){
                if(getAvailableBaumaschinenAmount(addStuecklisteneintragViewModel, baumaschines.get(i)) < 1){
                    baumaschines.remove(baumaschines.get(i));
                }
            }
            //send a list of all Database entries to the set method in the custom adapter
            customBaumaschinenAdapter.setBaumaschinen(baumaschines);
        });

        decreaseButton = findViewById(R.id.baumaschinenAmountDecreaseButton);
        decreaseButton.setOnClickListener(this);

        increaseButton = findViewById(R.id.baumschinenAmountIncreaseButton);
        increaseButton.setOnClickListener(this);

        addBaumaschinenListButton = findViewById(R.id.addVertragBaumaschineListButton);
        addBaumaschinenListButton.setOnClickListener(this);

        amountTextView = findViewById(R.id.baumaschinenAmountTextView);
        amountTextView.setText(String.valueOf(1));

        addVertragButton = findViewById(R.id.addVertragButton);
        addVertragButton.setOnClickListener(this);


        //create new customAdapter object and link Spinner with adapter
        CustomKundeAdapter customKundeAdapter = new CustomKundeAdapter(this);
        kundenSpinner.setAdapter(customKundeAdapter);

        //create new ViewModel and get all instances of the database entry
        kundenViewModel = new ViewModelProvider(this).get(KundenViewModel.class);
        kundenViewModel.getAllKunden().observe(this, new Observer<List<Kunde>>() {
            @Override
            public void onChanged(List<Kunde> kundes) {
                //create in CustomAdapter a method called setKunden to retrieve a list of all Database entries
                customKundeAdapter.setKunden(kundes);
            }
        });

        beginnVertrag = findViewById(R.id.dateBeginnLeihe);
        endeVertrag = findViewById(R.id.dateEndeLeihe);

        begin = LocalDate.now();
        beginnVertrag.setText(Converters.localDateToString(begin));

        //TODO: How long should the default renting time be?

        end = begin.plusDays(7);
        endeVertrag.setText(Converters.localDateToString(end));

        DatePickerDialog.OnDateSetListener dateBeginnLeihe = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                begin = LocalDate.of(year, month + 1, dayOfMonth);
                //check if date < now() --> warning / error message
                if (begin.isBefore(LocalDate.now())) {
                    //TODO: if begin <= today --> warning / error message
                    Log.d(TAG, "Startdatum sollte nicht vor heute liegen");
                    begin = LocalDate.now();
                    Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.startDateBeforCurrentDate), Toast.LENGTH_SHORT).show();
                }
                ;
                if (begin.isAfter(end)) {
                    Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.startDateAfterEndDate), Toast.LENGTH_SHORT).show();
                    end = begin.plusDays(1);
                    customBaumaschinenAdapter.notifyDataSetChanged();
                    endeVertrag.setText(Converters.localDateToString(end));
                }
                beginnVertrag.setText(Converters.localDateToString(begin));
                customBaumaschinenAdapter.notifyDataSetChanged();
            }
        };

        DatePickerDialog.OnDateSetListener dateEndeLeihe = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                end = LocalDate.of(year, month + 1, dayOfMonth);
                if (end.isBefore(begin)) {
                    //TODO: if end <= begin --> warning / error message
                    Log.d(TAG, "Ende sollte nicht vor Anfang liegen");
                    Toast.makeText(getApplicationContext(), "Ende vor Anfang !?!", Toast.LENGTH_SHORT).show();
                }
                customBaumaschinenAdapter.notifyDataSetChanged();
                endeVertrag.setText(Converters.localDateToString(end));
            }
        };


        beginnVertrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddVertragActivity.this, dateBeginnLeihe, begin.getYear(), begin.getMonthValue() - 1, begin.getDayOfMonth()).show();
            }
        });

        endeVertrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddVertragActivity.this, dateEndeLeihe, end.getYear(), end.getMonthValue() - 1, end.getDayOfMonth()).show();
            }
        });

        addVertragBaumaschineListAdapter = new AddVertragBaumaschineListAdapter(getApplication(), this, this);
        recyclerView = findViewById(R.id.addVertragBaumaschinenListRecyclerView);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(addVertragBaumaschineListAdapter);

        emptyRecyclerViewTextView = findViewById(R.id.emptyRecyclerviewTextView);
        announceRecyclerView = findViewById(R.id.announceRecyclerViewTextView);

        recyclerViewVisibility();


    }

    /*method to in- or decrease the amount of the chosen Baumaschine through the spinner:
     * -if the the amount */
    private void buttonVisibility() {
        if (amountInt >= maxAmount) {
            increaseButton.setVisibility(View.INVISIBLE);
        } else {
            increaseButton.setVisibility(View.VISIBLE);
        }
        if (amountInt == 1) {
            decreaseButton.setVisibility(View.INVISIBLE);
        } else {
            decreaseButton.setVisibility(View.VISIBLE);
        }

    }


    /*
     * method to receive chosen spinner object (Baumaschine or Kunde)
     * in case of Baumaschine: intercept the amount of the chosen Baumaschine
     * and set the initial amount which should be rented to 1
     * if the maxAmount is higher than 1, show increase button
     * if amountInt is higher than 1 show decrease button
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinnerBaumaschinen:
                selectedBaumaschineFromSpinner = (Baumaschine) parent.getAdapter().getItem(position);
                Log.d(TAG, "Spinner aktualisiert!");

                maxAmount = getAvailableBaumaschinenAmount(addStuecklisteneintragViewModel, selectedBaumaschineFromSpinner);

                Log.d(TAG, "Anzahl der verfügbaren Maschinen im Zeitraum: " + maxAmount);
                amountInt = 1;
                amountTextView.setText(String.valueOf(amountInt));
                buttonVisibility();
                Log.d(TAG, "Amount: " + maxAmount);
                break;
            case R.id.spinnerKunden:
                selectedKundeFromSpinner = (Kunde) parent.getAdapter().getItem(position);
                break;

        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    /*override method for VertragBaumaschinenListClickListener*/
    @Override
    public void onPositionClicked(int position) {

    }


    /*override method for View.OnClickListener*/
    @Override
    public void onClick(View v) {

        /*increase the amount of the chosen Baumaschine which should be rented*/
        if (v.getId() == increaseButton.getId()) {
            Log.d(TAG, "increasing Baumaschinen amount");
            amountInt++;
            amountTextView.setText(String.valueOf(amountInt));
            buttonVisibility();
            return;
        }

        /*decrease the amount of the chosen Baumaschine which should be rented*/
        if (v.getId() == decreaseButton.getId()) {
            Log.d(TAG, "decreasing Baumaschinen amount");
            amountInt--;
            amountTextView.setText(String.valueOf(amountInt));
            buttonVisibility();
            return;
        }

        if (v.getId() == addBaumaschinenListButton.getId()) {

            if (selectedBaumaschineFromSpinner != null) {
                addVertragBaumaschineListAdapter.addBaumaschinenToVertrag(selectedBaumaschineFromSpinner);
                recyclerViewVisibility();

            }
            return;
        }

        if (v.getId() == addVertragButton.getId()) {
            addStuecklisteneintragViewModel = new ViewModelProvider(this).get(AddStuecklisteneintragViewModel.class);
            List<Stuecklisteneintrag> stuecklisteInsert = addVertragBaumaschineListAdapter.getStueckliste();
            List<Integer> stuecklisteIds = new ArrayList<>();

            for (int i = 0; i < stuecklisteInsert.size(); i++) {
                long id = 0;
                try {
                    /*retrieve id from inserted Stuecklisteneintrag and save it in variable id*/
                    id = addStuecklisteneintragViewModel.insert(stuecklisteInsert.get(i));
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (id != 0) {
                    //TODO: change list type to long --> stuecklisteIds
                    stuecklisteIds.add(toIntExact(id));
                    Log.d(TAG, "Nächster Versuch: " + id);
                } else {
                    return;
                }


            }

            Log.d(TAG, "Größe der ID-Liste: " + stuecklisteIds.size());

            /* check if the beginnVertrag & endeVertrag field is filled*/
            if (!beginnVertrag.getText().toString().matches("") && !endeVertrag.getText().toString().matches("")) {
                /*check if list of Stuecklisteintrag-Id is not empty, to avoid Verträge without stuecklisteintrag-Ids*/
                if (!stuecklisteIds.isEmpty()) {
                    addVertragViewModel.insert(new Vertrag(stuecklisteIds, selectedKundeFromSpinner.getIdKunde(), begin, end));
                    finish();
                } else {
                    Toast.makeText(this, "Bitte Baumaschine(n) auswählen!", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "Bitte Start und Enddatum wählen!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /* set visibility of the recyclerview to true, if an machine is added, else let the recyclerview disappear*/
    public void recyclerViewVisibility() {
        if (addVertragBaumaschineListAdapter.getItemCount() == 0) {
            recyclerView.setVisibility(View.GONE);
            announceRecyclerView.setVisibility(View.GONE);
            emptyRecyclerViewTextView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            announceRecyclerView.setVisibility(View.VISIBLE);
            emptyRecyclerViewTextView.setVisibility(View.GONE);
        }

    }

    public int getSelectedBaumaschinenAmount() {
        return amountInt;
    }
    public BigDecimal calcPriceForRent(){
        return new BigDecimal(selectedBaumaschineFromSpinner.getPricePerDay().longValue() * DAYS.between(begin,end) * getSelectedBaumaschinenAmount());
    }

    public int getAvailableBaumaschinenAmount(AddStuecklisteneintragViewModel addStuecklisteneintragViewModel, Baumaschine selectedBaumaschineFromSpinner) {
        List<Stuecklisteneintrag> bufferList = addStuecklisteneintragViewModel.getAllStuecklisteneintragForId(selectedBaumaschineFromSpinner.getIdBaumaschine());
        //maxAmount = selectedBaumaschineFromSpinner.getAmount();
        List<Stuecklisteneintrag> existingStuecklisteneintrageForBaumaschine = addStuecklisteneintragViewModel.getStuecklisteneintragForDate(begin, end, selectedBaumaschineFromSpinner.getIdBaumaschine());
        int maxRentingAmountPerDay = 0;
        int maxRentingAmount = 0;

        for (int j = 0; j < (DAYS.between(begin, end)); j++) {
            for (int i = 0; i < existingStuecklisteneintrageForBaumaschine.size(); i++) {
                if ((existingStuecklisteneintrageForBaumaschine.get(i).getBeginDate().isBefore(begin.plusDays(j)) ||
                        existingStuecklisteneintrageForBaumaschine.get(i).getBeginDate().isEqual(begin.plusDays(j))) &&
                        (existingStuecklisteneintrageForBaumaschine.get(i).getEndDate().isAfter(begin.plusDays(j)) ||
                                existingStuecklisteneintrageForBaumaschine.get(i).getEndDate().isEqual(begin.plusDays(j)))) {

                    maxRentingAmountPerDay = maxRentingAmountPerDay + existingStuecklisteneintrageForBaumaschine.get(i).getAmount();
                }

            }
            maxRentingAmount = max(maxRentingAmountPerDay, maxRentingAmount);
            maxRentingAmountPerDay = 0;
        }
        return selectedBaumaschineFromSpinner.getAmount() - maxRentingAmount;




        /*for (int i = 0; i < bufferList.size(); i++){
            //logic to restrict the amount of the chosen machine in case that an contract during the desired time for that machine is already existing
            if(!bufferList.get(i).getBeginDate().isAfter(begin) && !bufferList.get(i).getEndDate().isBefore(end)){
                maxAmount = maxAmount - bufferList.get(i).getAmount();
                continue;

            }
            if (bufferList.get(i).getBeginDate().isBefore(end)  && !bufferList.get(i).getEndDate().isBefore(end) || begin.isBefore(bufferList.get(i).getEndDate())&& ! end.isBefore(bufferList.get(i).getEndDate()) ){
                maxAmount = maxAmount - bufferList.get(i).getAmount();

            }
            else if(bufferList.get(i).getBeginDate().isEqual(end) || bufferList.get(i).getEndDate().isEqual(begin)){
                maxAmount = maxAmount -bufferList.get(i).getAmount();
            }
            addVertragViewModel.getAllVertragDuringDesiredTime(begin,end);

        }*/

    }

    public LocalDate getBeginnVertrag() {
        return Converters.editTextToLocalDate(beginnVertrag);
    }

    //method to check in AddVertragBaumaschinenListAdapter if the start and end date of the rental is already set or not
    public boolean checkIfDateIsSet() {
        return beginnVertrag.getText().toString().matches("") && endeVertrag.getText().toString().matches("");
    }

    public LocalDate getEndeVertrag() {
        return Converters.editTextToLocalDate(endeVertrag);
    }


}
