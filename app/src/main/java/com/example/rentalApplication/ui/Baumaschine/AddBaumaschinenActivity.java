package com.example.rentalApplication.ui.Baumaschine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rentalApplication.R;
import com.example.rentalApplication.models.Baumaschine;

import java.math.BigDecimal;

public class AddBaumaschinenActivity extends AppCompatActivity {
    private EditText addBaumaschinenNameEditText, addBaumaschinenAnzahlEditText, addBaumaschinenPricePerDayEditText, addBaumaschinenPricePerWeekendEditText, addBaumaschinenPricePerMonthEditText, addBaumaschinenOperatingHours, addBaumaschinenDegreeOfWear, addBaumaschinenAmountOfGas;
    private Button addBaumaschinenButton;
    private AddBaumaschinenViewModel addBaumaschinenViewModel;
    private ModifyBaumaschineViewModel modifyBaumaschineViewModel;
    private Baumaschine loadBaumaschineById;
    private final static String TAG = "AddBaumaschinenActivity.java";



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

        //retrieve Intent from starting activity
        Intent intent = this.getIntent();
        //check if intent contain values, if so, then modifing button was clicked, if not the adding new baumaschinen button was clicked
        if(intent != null){
            String activityString = intent.getStringExtra("Class");
            if(activityString.equals("BaumaschinenListAdapter")) {
                Log.d(TAG, "Intent Ergebnis RowID" + intent.getExtras().getInt("baumaschineneRowId"));

                //create new modifyBaumaschinenViewModel, to pass the additional parameter id, a ModifyBaumaschineViewModelFactory was created and is used
                //retrieve the id from the intent
                modifyBaumaschineViewModel = new ViewModelProvider(this, new ModifyBaumaschineViewModelFactory(this.getApplication(), intent.getExtras().getInt("baumaschineneRowId"))).get(ModifyBaumaschineViewModel.class);
                loadBaumaschineById = modifyBaumaschineViewModel.loadBaumaschineById(intent.getExtras().getInt("baumaschineneRowId"));
                addBaumaschinenNameEditText.setText(loadBaumaschineById.getMachineName());
            }


        }
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
        BigDecimal pricePerDay = new BigDecimal(baumaschinenPricePerDay);
        BigDecimal pricePerWeekend = new BigDecimal(baumaschinenPricePerWeekend);
        BigDecimal pricePerMonth = new BigDecimal(baumaschinenPricePerMonth);
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