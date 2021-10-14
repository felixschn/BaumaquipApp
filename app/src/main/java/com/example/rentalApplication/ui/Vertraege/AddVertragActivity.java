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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
import com.example.rentalApplication.models.Converters;
import com.example.rentalApplication.models.Kunde;
import com.example.rentalApplication.models.Vertrag;
import com.example.rentalApplication.persistence.BaumaschinenRepository;
import com.example.rentalApplication.ui.Baumaschine.BaumaschinenViewModel;
import com.example.rentalApplication.ui.Kunde.KundenViewModel;
import com.example.rentalApplication.ui.Vertraege.Spinner.CustomBaumaschinenAdapter;
import com.example.rentalApplication.ui.Vertraege.Spinner.CustomKundeAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
    int amountInt;
    int maxAmount;
    String amount;
    private Boolean isUserAction = false;
    private CustomBaumaschinenAdapter customBaumaschinenAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vertrag);


        //create Spinner objects and link them to the xml objects --> tutorial: https://abhiandroid.com/ui/custom-spinner-examples.html
        Spinner baumaschinenSpinner = (Spinner) findViewById(R.id.spinnerBaumaschinen);
        Spinner kundenSpinner = (Spinner) findViewById(R.id.spinnerKunden);
        baumaschinenSpinner.setOnItemSelectedListener(this);
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
        increaseButton = findViewById(R.id.baumschinenAmountIncreaseButton);
        amountTextView = findViewById(R.id.baumaschinenAmountTextView);
        amountTextView.setText(String.valueOf(1));

        decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "decreasing Baumaschinen amount");
                amountInt--;
                amountTextView.setText(String.valueOf(amountInt));

                buttonVisibility();
            }
        });

        increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "increasing Baumaschinen amount");
                amountInt++;
                amountTextView.setText(String.valueOf(amountInt));

                buttonVisibility();


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
            @RequiresApi(api = Build.VERSION_CODES.O)
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void insertNewVertag() {
        List<Baumaschine> list = new ArrayList<Baumaschine>(baumaschineHashMap.values());
        List<Kunde> kundenList = new ArrayList<Kunde>(kundeHashMap.values());

        addVertragViewModel = new ViewModelProvider(this).get(AddVertragViewModel.class);

        addVertragViewModel.insert(new Vertrag(list, kundenList.get(0).getName(), Converters.stringToDate(beginnLeihe.toString()), Converters.stringToDate(endeLeihe.toString())));

    }

    private void buttonVisibility(){
        if(amountInt >= maxAmount) {
            increaseButton.setVisibility(View.INVISIBLE);
        }
        else {
            increaseButton.setVisibility(View.VISIBLE);
        }
        if(amountInt == 1) {
            decreaseButton.setVisibility(View.INVISIBLE);
        }
        else{
            decreaseButton.setVisibility(View.VISIBLE);
        }

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
        switch(parent.getId()){
            case R.id.spinnerBaumaschinen:
                String selected = parent.getItemAtPosition(position).toString();
                maxAmount = ((Baumaschine) parent.getAdapter().getItem(position)).getAmount();
                amountInt = 1;
                amountTextView.setText(String.valueOf(amountInt));
                buttonVisibility();



                Log.d(TAG,"Amount: " + maxAmount);
                break;
            case R.id.spinnerKunden:
                break;

        }



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
