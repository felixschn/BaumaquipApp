package com.example.rentalApplication.ui.Vertraege;

import android.app.DatePickerDialog;
import android.os.Build;
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
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
import com.example.rentalApplication.models.Vertrag;
import com.example.rentalApplication.ui.Baumaschine.BaumaschinenViewModel;
import com.example.rentalApplication.ui.Kunde.KundenViewModel;
import com.example.rentalApplication.ui.Vertraege.Spinner.CustomBaumaschinenAdapter;
import com.example.rentalApplication.ui.Vertraege.Spinner.CustomKundeAdapter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class AddVertragActivity extends AppCompatActivity implements  AdapterView.OnItemSelectedListener, VertragBaumaschinenListClickListener, View.OnClickListener {

    private BaumaschinenViewModel baumaschinenViewModel;
    private KundenViewModel kundenViewModel;
    private EditText beginnLeihe, endeLeihe;
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

        beginnLeihe = (EditText) findViewById(R.id.dateBeginnLeihe);
        endeLeihe = (EditText) findViewById(R.id.dateEndeLeihe);

        DatePickerDialog.OnDateSetListener dateBeginnLeihe = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                rentCalendar.set(Calendar.YEAR, year);
                rentCalendar.set(Calendar.MONTH, month);
                rentCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateCalendarDateBeginnLeihe();
            }
        };
        DatePickerDialog.OnDateSetListener dateEndeLeihe = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                rentCalendar.set(Calendar.YEAR, year);
                rentCalendar.set(Calendar.MONTH, month);
                rentCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateCalendarDateEndeLeihe();
            }
        };


        beginnLeihe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddVertragActivity.this, dateBeginnLeihe, rentCalendar.get(Calendar.YEAR), rentCalendar.get(Calendar.MONTH), rentCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        endeLeihe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddVertragActivity.this, dateEndeLeihe, rentCalendar.get(Calendar.YEAR), rentCalendar.get(Calendar.MONTH), rentCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        addVertragBaumaschineListAdapter = new AddVertragBaumaschineListAdapter(this, this);
        recyclerView = findViewById(R.id.addVertragBaumaschinenListRecyclerView);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(addVertragBaumaschineListAdapter);

        emptyRecyclerViewTextView = findViewById(R.id.emptyRecyclerviewTextView);
        announceRecyclerView = findViewById(R.id.announceRecyclerViewTextView);

        recyclerViewVisibility();
    }


    private void updateCalendarDateBeginnLeihe() {
        String format = "dd/MM/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.GERMAN);
        beginnLeihe.setText(simpleDateFormat.format(rentCalendar.getTime()));
    }

    private void updateCalendarDateEndeLeihe() {
        String format = "dd/MM/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.GERMAN);
        endeLeihe.setText(simpleDateFormat.format(rentCalendar.getTime()));
    }


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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinnerBaumaschinen:
                selectedBaumaschineFromSpinner = (Baumaschine) parent.getAdapter().getItem(position);
                maxAmount = selectedBaumaschineFromSpinner.getAmount();
                amountInt = 1;
                amountTextView.setText(String.valueOf(amountInt));
                buttonVisibility();


                Log.d(TAG, "Amount: " + maxAmount);
                break;
            case R.id.spinnerKunden:
                break;

        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    //override method for VertragBaumaschinenListClickListener
    @Override
    public void onPositionClicked(int position) {

    }


    //override method for View.OnClickListener
    @Override
    public void onClick(View v) {
        if (v.getId() == increaseButton.getId()){
            Log.d(TAG, "increasing Baumaschinen amount");
            amountInt++;
            amountTextView.setText(String.valueOf(amountInt));
            buttonVisibility();
        }
        if(v.getId() == decreaseButton.getId()){
            Log.d(TAG, "decreasing Baumaschinen amount");
            amountInt--;
            amountTextView.setText(String.valueOf(amountInt));
            buttonVisibility();
        }
        if(v.getId() == addBaumaschinenListButton.getId()){
            if(selectedBaumaschineFromSpinner != null){
                addVertragBaumaschineListAdapter.setAddVertragBaumaschinen(selectedBaumaschineFromSpinner);
                addVertragBaumaschineListAdapter.getBaumaschineList();
                recyclerViewVisibility();
            }



        }
        if(v.getId() == addVertragButton.getId()){
            //insertNewVertrag();
        }

    }

    public void recyclerViewVisibility(){
        if(addVertragBaumaschineListAdapter.getItemCount() == 0){
            recyclerView.setVisibility(View.GONE);
            announceRecyclerView.setVisibility(View.GONE);
            emptyRecyclerViewTextView.setVisibility(View.VISIBLE);
        }
        else{
            recyclerView.setVisibility(View.VISIBLE);
            announceRecyclerView.setVisibility(View.VISIBLE);
            emptyRecyclerViewTextView.setVisibility(View.GONE);
        }

    }

    public int getSelectedBaumaschinenAmount(){
        return amountInt;
    }
}
