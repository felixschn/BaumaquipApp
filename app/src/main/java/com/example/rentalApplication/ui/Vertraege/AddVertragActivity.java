package com.example.rentalApplication.ui.Vertraege;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import com.example.rentalApplication.ui.Baumaschine.BaumaschinenViewModel;
import com.example.rentalApplication.ui.Kunde.KundenViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class AddVertragActivity extends AppCompatActivity implements AddVertragBaumaschineListAdapter.IGetBaumaschinenFromAdapter, AddVertragKundeListAdapter.IGetKundeFromAdapter {

    private BaumaschinenViewModel bViewModel;
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


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vertrag);


        recyclerViewKunde = findViewById(R.id.addVertragKundeRecyclerView);
        recyclerViewKunde.addItemDecoration(new SimpleItemDecoration(this));
        recyclerViewKunde.setLayoutManager(new LinearLayoutManager(this));
        final AddVertragKundeListAdapter addVertragKundeListAdapter = new AddVertragKundeListAdapter(this);
        recyclerViewKunde.setAdapter(addVertragKundeListAdapter);
        kViewModel = new ViewModelProvider(this).get(KundenViewModel.class);
        kViewModel.getAllKunden().observe(this, new Observer<List<Kunde>>() {
            @Override
            public void onChanged(List<Kunde> kundes) {
                addVertragKundeListAdapter.setAddVertragKunden(kundes);
            }
        });

        /*-----------------------------------------------------------------
        RecyclerView für Baumaschine*/

        recyclerViewBaumaschine = findViewById(R.id.addVertragBaumaschineRecyclerView);
        //recyclerViewBaumaschine.hasFixedSize();
        recyclerViewBaumaschine.addItemDecoration(new SimpleItemDecoration(this));
        recyclerViewBaumaschine.setLayoutManager(new LinearLayoutManager(this));
        final AddVertragBaumaschineListAdapter addVertragBaumaschineListAdapter = new AddVertragBaumaschineListAdapter(this);
        recyclerViewBaumaschine.setAdapter(addVertragBaumaschineListAdapter);
        bViewModel = new ViewModelProvider(this).get(BaumaschinenViewModel.class);
        bViewModel.getAllBaumaschinen().observe(this, new Observer<List<Baumaschine>>() {
            @Override
            public void onChanged(List<Baumaschine> baumaschines) {
                addVertragBaumaschineListAdapter.setAddVertragBaumaschinen(baumaschines);
            }
        });
        //Log.d(sendedList, "" + addVertragBaumaschineListAdapter.sendBaumaschineList());


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
}
