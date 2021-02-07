package com.example.rentalApplication.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rentalApplication.MainPageActivity;
import com.example.rentalApplication.R;
import com.example.rentalApplication.models.Baumaschine;
import com.example.rentalApplication.persistence.BaumaschinenDao;
import com.example.rentalApplication.persistence.BaumaschinenRepository;

public class AddBaumaschinenActivity extends AppCompatActivity {
    private EditText addBaumaschinenNameEditText, addBaumaschinenAnzahlEditText, addBaumaschinenPricePerDayEditText, addBaumaschinenPricePerWeekendEditText, addBaumaschinenPricePerMonthEditText;
    private Button addBaumaschinenButton;
    private AddBaumaschinenViewModel addBaumaschinenViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_baumaschinen);
        addBaumaschinenButton = findViewById(R.id.addBaumaschinenButton);
        addBaumaschinenNameEditText = findViewById(R.id.addBaumaschinenName);
        addBaumaschinenAnzahlEditText = findViewById(R.id.addBaumaschinenAnzahl);
        addBaumaschinenPricePerDayEditText = findViewById(R.id.addBaumaschinenPricePerDay);
        addBaumaschinenPricePerWeekendEditText = findViewById(R.id.addBaumaschinenPricePerWeekend);
        addBaumaschinenPricePerMonthEditText = findViewById(R.id.addBaumaschinenPricePerMonth);

        addBaumaschinenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertNewBaumaschine();
            }
        });




    }
    private void insertNewBaumaschine(){
        String baumaschinenName = addBaumaschinenNameEditText.getText().toString();
        Integer baumaschinenAnzahl = Integer.parseInt(addBaumaschinenAnzahlEditText.getText().toString());
        Double baumaschinenPricePerDay = Double.parseDouble(addBaumaschinenPricePerDayEditText.getText().toString());
        Double baumaschinenPricePerWeekend = Double.parseDouble(addBaumaschinenPricePerWeekendEditText.getText().toString());
        Double baumaschinenPricePerMonth = Double.parseDouble(addBaumaschinenPricePerMonthEditText.getText().toString());
        addBaumaschinenViewModel = new ViewModelProvider(this).get(AddBaumaschinenViewModel.class);
        if(baumaschinenName.trim().isEmpty() || baumaschinenAnzahl == null || baumaschinenPricePerDay == null || baumaschinenPricePerWeekend == null || baumaschinenPricePerMonth==null){
            Toast.makeText(this, "Bitte alle Felder ausfüllen!", Toast.LENGTH_LONG).show();
            return;
        }
        addBaumaschinenViewModel.insert(new Baumaschine(baumaschinenName,baumaschinenAnzahl,baumaschinenPricePerDay,baumaschinenPricePerWeekend,baumaschinenPricePerMonth, null,null,null));

        /*Integer addBaumaschinenAnzahlInteger = Integer.parseInt(addBaumaschinenAnzahlEditText.getText().toString());
        Double addBaumaschinenPricePerDayDouble = Double.parseDouble(addBaumaschinenPricePerDayEditText.getText().toString());
        Double addBaumaschinenPricePerWeekendDouble = Double.parseDouble(addBaumaschinenPricePerWeekendEditText.getText().toString());
        Double addBaumaschinenPricePerMonthDouble = Double.parseDouble(addBaumaschinenPricePerMonthEditText.getText().toString());
        MainPageActivity.rentDatabase.baumaschinenDao().insert(new Baumaschine(addBaumaschinenNameEditText.getText().toString(), addBaumaschinenAnzahlInteger, addBaumaschinenPricePerDayDouble, addBaumaschinenPricePerWeekendDouble, addBaumaschinenPricePerMonthDouble, null,null,null));
        runOnUiThread(() -> {
            finish();
        });*/
    }

}