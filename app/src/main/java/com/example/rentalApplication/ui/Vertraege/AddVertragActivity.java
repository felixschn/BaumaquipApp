package com.example.rentalApplication.ui.Vertraege;

import static java.lang.Math.toIntExact;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.format.DateUtils;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


public class AddVertragActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, VertragBaumaschinenListClickListener, View.OnClickListener {

    private BaumaschinenViewModel baumaschinenViewModel;
    private KundenViewModel kundenViewModel;
    private AddStuecklisteneintragViewModel addStuecklisteneintragViewModel;
    private AddVertragViewModel addVertragViewModel;
    private EditText beginnVertrag, endeVertrag;
    private LocalDate begin, end;
    private final Calendar rentCalendar = Calendar.getInstance();
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


        //create Spinner objects and link them to the xml objects --> tutorial: https://abhiandroid.com/ui/custom-spinner-examples.html
        baumaschinenSpinner = (Spinner) findViewById(R.id.spinnerBaumaschinen);
        baumaschinenSpinner.setOnItemSelectedListener(this);

        kundenSpinner = (Spinner) findViewById(R.id.spinnerKunden);
        kundenSpinner.setOnItemSelectedListener(this);

        //create new customAdapter object and link Spinner with adapter
        customBaumaschinenAdapter = new CustomBaumaschinenAdapter(getApplicationContext());
        baumaschinenSpinner.setAdapter(customBaumaschinenAdapter);


        //create new ViewModel and get all instances of the database entry
        baumaschinenViewModel = new ViewModelProvider(this).get(BaumaschinenViewModel.class);
        baumaschinenViewModel.getAllBaumaschinen().observe(this, new Observer<List<Baumaschine>>() {
            @Override
            public void onChanged(List<Baumaschine> baumaschines) {
                //create in CustomAdapter a method called setBaumaschines to retrieve a list of all Database entries
                customBaumaschinenAdapter.setBaumaschinen(baumaschines);
            }
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
        CustomKundeAdapter customKundeAdapter = new CustomKundeAdapter(getApplicationContext());
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

        beginnVertrag = (EditText) findViewById(R.id.dateBeginnLeihe);
        endeVertrag = (EditText) findViewById(R.id.dateEndeLeihe);

        DatePickerDialog.OnDateSetListener dateBeginnLeihe = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                rentCalendar.set(year, month, dayOfMonth);
                begin = LocalDate.of(year, month, dayOfMonth);
                //check if date < now() --> warning / error message
                if(begin.isBefore(LocalDate.now())){
                    Log.d(TAG, "Startdatum sollte nicht vor heute liegen");
                    //TODO:Taost ?
                    Toast.makeText(getApplicationContext(), "Start liegt vor heute!?!", Toast.LENGTH_SHORT).show();
                };
                beginnVertrag.setText(Converters.dateToString(rentCalendar.getTime()));


            }
        };
        DatePickerDialog.OnDateSetListener dateEndeLeihe = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                rentCalendar.set(year, month, dayOfMonth);
                end = LocalDate.of(year, month, dayOfMonth);
                //TODO: check if date <= begin --> warning / error message
                if(end.isBefore(begin)){
                    Log.d(TAG, "Ende sollte nicht vor Anfang liegen");
                    //TODO:Taost ?
                    Toast.makeText(getApplicationContext(), "Ende vor Anfang !?!", Toast.LENGTH_SHORT).show();
                };
                endeVertrag.setText(Converters.dateToString(rentCalendar.getTime()));
            }
        };


        beginnVertrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddVertragActivity.this, dateBeginnLeihe, rentCalendar.get(Calendar.YEAR), rentCalendar.get(Calendar.MONTH), rentCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        endeVertrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddVertragActivity.this, dateEndeLeihe, rentCalendar.get(Calendar.YEAR), rentCalendar.get(Calendar.MONTH), rentCalendar.get(Calendar.DAY_OF_MONTH)).show();
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
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinnerBaumaschinen:
                selectedBaumaschineFromSpinner = (Baumaschine) parent.getAdapter().getItem(position);
                //TODO: calculate maxAmount based on selected date
                maxAmount = selectedBaumaschineFromSpinner.getAmount();
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
        }

        /*decrease the amount of the chosen Baumaschine which should be rented*/
        if (v.getId() == decreaseButton.getId()) {
            Log.d(TAG, "decreasing Baumaschinen amount");
            amountInt--;
            amountTextView.setText(String.valueOf(amountInt));
            buttonVisibility();
        }

        if (v.getId() == addBaumaschinenListButton.getId()) {

            if (selectedBaumaschineFromSpinner != null) {
                addVertragBaumaschineListAdapter.addBaumaschinenToVertrag(selectedBaumaschineFromSpinner);
                recyclerViewVisibility();
            }
        }

        if (v.getId() == addVertragButton.getId()) {
            int currentStuecklisteneintragId;
            addStuecklisteneintragViewModel = new ViewModelProvider(this).get(AddStuecklisteneintragViewModel.class);
            addVertragViewModel = new ViewModelProvider(this).get(AddVertragViewModel.class);
            List<Stuecklisteneintrag> stuecklisteInsert = addVertragBaumaschineListAdapter.getStueckliste();
            List<Integer> stuecklisteIds = new ArrayList<>();

            for (int i = 0; i < stuecklisteInsert.size(); i++) {
                long id = 0;
                try {
                    id = addStuecklisteneintragViewModel.insert(stuecklisteInsert.get(i));
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (id != 0) {
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
                    addVertragViewModel.insert(new Vertrag(stuecklisteIds, selectedKundeFromSpinner.getIdKunde(), getBeginnVertrag(), getEndeVertrag()));
                } else {
                    Toast.makeText(this, "Bitte Baumaschine(n) auswählen!", Toast.LENGTH_SHORT).show();

                    return;
                }

            } else {
                Toast.makeText(this, "Bitte Start und Enddatum wählen!", Toast.LENGTH_SHORT).show();
                return;
            }



            //addVertragViewModel.insert(new Vertrag(stuecklisteIds,selectedKundeFromSpinner.getIdKunde(),getBeginnVertrag(),getEndeVertrag()));
            //TODO: save to DB
            //insertNewVertrag();
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

    public LocalDate getBeginnVertrag() {
        return Converters.editTextToLocalDate(beginnVertrag);
    }

    public LocalDate getEndeVertrag() {
        return Converters.editTextToLocalDate(endeVertrag);
    }


}
