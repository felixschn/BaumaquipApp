package com.example.rentalApplication.ui.Baumaschine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rentalApplication.R;
import com.example.rentalApplication.models.Baumaschine;

public class AddBaumaschinenActivity extends AppCompatActivity {
    private EditText addBaumaschinenNameEditText, addBaumaschinenAnzahlEditText, addBaumaschinenPricePerDayEditText, addBaumaschinenPricePerWeekendEditText, addBaumaschinenPricePerMonthEditText, addBaumaschinenOperatingHours, addBaumaschinenDegreeOfWear, addBaumaschinenAmountOfGas;
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
        addBaumaschinenOperatingHours = findViewById(R.id.addBaumaschinenOperatingHours);
        addBaumaschinenDegreeOfWear =findViewById(R.id.addBaumaschinenDegreeOfWear);
        addBaumaschinenAmountOfGas = findViewById(R.id.addBaumaschinenAmountOfGas);

        addBaumaschinenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertNewBaumaschine();
            }


        });




    }
    private void insertNewBaumaschine(){
        String baumaschinenName = addBaumaschinenNameEditText.getText().toString();
        String baumaschinenAnzahl = addBaumaschinenAnzahlEditText.getText().toString();
        String baumaschinenPricePerDay = addBaumaschinenPricePerDayEditText.getText().toString();
        String baumaschinenPricePerWeekend = addBaumaschinenPricePerWeekendEditText.getText().toString();
        String baumaschinenPricePerMonth = addBaumaschinenPricePerMonthEditText.getText().toString();
        String baumaschinenOperatingHours = addBaumaschinenOperatingHours.getText().toString();
        String baumaschinenDegreeOfWear = addBaumaschinenDegreeOfWear.getText().toString();
        String baumaschinenAmountOfGas = addBaumaschinenAmountOfGas.getText().toString();


        addBaumaschinenViewModel = new ViewModelProvider(this).get(AddBaumaschinenViewModel.class);
        if(baumaschinenName.trim().isEmpty() || baumaschinenAnzahl.trim().isEmpty() || baumaschinenPricePerDay.trim().isEmpty() || baumaschinenPricePerWeekend.trim().isEmpty() || baumaschinenPricePerMonth.trim().isEmpty()){
            Toast.makeText(this, "Bitte alle Felder ausfüllen!", Toast.LENGTH_LONG).show();
            return;
        }

        Integer anzahl = Integer.parseInt(baumaschinenAnzahl);
        Double pricePerDay = Double.parseDouble(baumaschinenPricePerDay);
        Double pricePerWeekend = Double.parseDouble(baumaschinenPricePerWeekend);
        Double pricePerMonth = Double.parseDouble(baumaschinenPricePerMonth);
        Double operatingHours = Double.parseDouble(baumaschinenOperatingHours);

        addBaumaschinenViewModel.insert(new Baumaschine(baumaschinenName,anzahl,pricePerDay,pricePerWeekend,pricePerMonth, operatingHours,baumaschinenDegreeOfWear,baumaschinenAmountOfGas));
        finish();
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