package com.example.rentalApplication.ui.Vertraege;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalApplication.R;
import com.example.rentalApplication.adapter.AddVertragBaumaschineListAdapter;
import com.example.rentalApplication.adapter.AddVertragKundeListAdapter;
import com.example.rentalApplication.models.Baumaschine;
import com.example.rentalApplication.models.Kunde;
import com.example.rentalApplication.models.Vertrag;
import com.example.rentalApplication.persistence.BaumaschinenRepository;
import com.example.rentalApplication.ui.Baumaschine.BaumaschinenViewModel;
import com.example.rentalApplication.ui.Kunde.KundenViewModel;
import com.example.rentalApplication.ui.Vertraege.Spinner.CustomBaumaschinenAdapter;
import com.example.rentalApplication.ui.Vertraege.Spinner.CustomKundeAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class AddVertragActivity extends AppCompatActivity implements AddVertragBaumaschineListAdapter.IGetBaumaschinenFromAdapter, AddVertragKundeListAdapter.IGetKundeFromAdapter, AdapterView.OnItemSelectedListener {

    private BaumaschinenViewModel baumaschinenViewModel;
    private KundenViewModel kundenViewModel;
    private KundenViewModel kViewModel;
    private HashMap<Integer, Baumaschine> baumaschineHashMap;
    private HashMap<Integer, Kunde> kundeHashMap;
    private EditText beginnLeihe, endeLeihe;
    private final Calendar rentCalendar = Calendar.getInstance();
    private Button addVertragButton;
    private AddVertragBaumaschineListAdapter addVertragBaumaschineListAdapter;
    private RecyclerView recyclerViewBaumaschine, recyclerViewKunde;
    private AddVertragViewModel addVertragViewModel;
    private static final String sendedList = "receiving list";
    private static final String TAG = "AddVertragActivity";
    private String text = "";
    private Button increaseButton, decreaseButton;
    private TextView amountTextView;
    Integer amountInt = 3;
    String amount;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vertrag);


        //create Spinner objects and link them to the xml objects --> tutorial: https://abhiandroid.com/ui/custom-spinner-examples.html
        Spinner baumaschinenSpinner = (Spinner) findViewById(R.id.spinnerBaumaschinen);
        Spinner kundenSpinner = (Spinner) findViewById(R.id.spinnerKunden);
        baumaschinenSpinner.setOnItemSelectedListener(this);
        baumaschinenSpinner.setOnItemSelectedListener(this);

        //create new customAdapter object and link Spinner with adapter
        CustomBaumaschinenAdapter customBaumaschinenAdapter = new CustomBaumaschinenAdapter(getApplicationContext());
        baumaschinenSpinner.setAdapter(customBaumaschinenAdapter);
        baumaschinenSpinner.

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
        increaseButton = findViewById(R.id.baumschinenAmountIncreaseButton);
        amountTextView = findViewById(R.id.baumaschinenAmountTextView);
        amountTextView.setText(amountInt.toString());

        decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG,"decreasing Baumaschinen amount");
                if(amountInt > 1){
                    amountInt = amountInt - 1;
                    amount = String.valueOf(amountInt);
                    amountTextView.setText(amount);
                }
                else{
                    decreaseButton.setVisibility(View.INVISIBLE);
                    //Toast.makeText(this, "Bitte alle Felder ausfüllen!", Toast.LENGTH_LONG).show();
                }
            }
        });

        increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"increasing Baumaschinen amount");
                //if(amountInt < kundenSpinner.getSelectedItem().)

            }
        });

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
        addVertragButton = findViewById(R.id.addVertragButton);
        addVertragButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertNewVertag();

            }
        });


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

    private void insertNewVertag() {
        List<Baumaschine> list = new ArrayList<Baumaschine>(baumaschineHashMap.values());
        List<Kunde> kundenList = new ArrayList<Kunde>(kundeHashMap.values());
        if(list.size() != 0){

        }
        addVertragViewModel = new ViewModelProvider(this).get(AddVertragViewModel.class);

        addVertragViewModel.insert(new Vertrag(list, kundenList.get(0).getName(),beginnLeihe.toString(), endeLeihe.toString()));

    }


    //created interface to intercept data from the recyclerview adapter
    @Override
    public void getBaumaschinenFromAdapter(HashMap<Integer, Baumaschine> baumaschineHashMap) {
        this.baumaschineHashMap = baumaschineHashMap;
        Log.d(sendedList, "Activity " + baumaschineHashMap.size());

    }


    @Override
    public void getKundeFromAdapter(HashMap<Integer, Kunde> kundeList) {
        this.kundeHashMap = kundeList;
        Log.d(sendedList, "Activity " + kundeHashMap.size());

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
