package com.example.rentalApplication.ui.Baumaschine;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.rentalApplication.R;
import com.example.rentalApplication.models.Baumaschine;
import com.example.rentalApplication.ui.Vertraege.AddVertragActivity;

import java.math.BigDecimal;

public class AddBaumaschinenActivity extends AppCompatActivity {
    private EditText addBaumaschinenNameEditText, addBaumaschinenAnzahlEditText, addBaumaschinenPricePerDayEditText, addBaumaschinenPricePerWeekendEditText, addBaumaschinenPricePerMonthEditText, addBaumaschinenOperatingHours, addBaumaschinenDegreeOfWear, addBaumaschinenAmountOfGas;
    private Button addBaumaschinenButton;
    private AddBaumaschinenViewModel addBaumaschinenViewModel;
    private ModifyBaumaschineViewModel modifyBaumaschineViewModel;
    private Baumaschine loadBaumaschineById;
    private final static String TAG = "AddBaumaschinenActivity.java";
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_baumaschinen);
        addBaumaschinenButton = findViewById(R.id.addBaumaschinenButton);
        addBaumaschinenNameEditText = findViewById(R.id.addBaumaschinenName);
        addBaumaschinenAnzahlEditText = findViewById(R.id.addBaumaschinenAnzahl);
        addBaumaschinenAnzahlEditText.setFilters(new InputFilter[]{
                new InputFilterMinAmount(0){}
        });
        addBaumaschinenPricePerDayEditText = findViewById(R.id.addBaumaschinenPricePerDay);
        addBaumaschinenPricePerWeekendEditText = findViewById(R.id.addBaumaschinenPricePerWeekend);
        addBaumaschinenPricePerMonthEditText = findViewById(R.id.addBaumaschinenPricePerMonth);
        addBaumaschinenOperatingHours = findViewById(R.id.addBaumaschinenOperatingHours);
        addBaumaschinenDegreeOfWear = findViewById(R.id.addBaumaschinenDegreeOfWear);
        addBaumaschinenAmountOfGas = findViewById(R.id.addBaumaschinenAmountOfGas);

        addBaumaschinenOperatingHours.setEnabled(false);
        addBaumaschinenDegreeOfWear.setEnabled(false);
        addBaumaschinenAmountOfGas.setEnabled(false);

        addBaumaschinenAnzahlEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().matches("1")) {
                    addBaumaschinenOperatingHours.setEnabled(true);
                    addBaumaschinenDegreeOfWear.setEnabled(true);
                    addBaumaschinenAmountOfGas.setEnabled(true);

                } else {
                    addBaumaschinenOperatingHours.setEnabled(false);
                    addBaumaschinenDegreeOfWear.setEnabled(false);
                    addBaumaschinenAmountOfGas.setEnabled(false);

                }


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.equals("1")) {
                    Log.d(TAG, "TEST");
                }

            }
        });


        //retrieve Intent from starting activity
        intent = this.getIntent();
        //check if intent contain values, if so, then modifing button was clicked, if not the adding new baumaschinen button was clicked
        if (intent != null) {
            String activityString = intent.getStringExtra("Class");
            if (activityString.equals("BaumaschinenListAdapter")) {
                Log.d(TAG, "Intent Ergebnis RowID" + intent.getExtras().getInt("baumaschineneRowId"));

                //create new modifyBaumaschinenViewModel, to pass the additional parameter id, a ModifyBaumaschineViewModelFactory was created and is used
                //retrieve the id from the intent
                modifyBaumaschineViewModel = new ViewModelProvider(this).get(ModifyBaumaschineViewModel.class);
                loadBaumaschineById = modifyBaumaschineViewModel.getBaumaschineById(intent.getExtras().getInt("baumaschineneRowId"));
                int editableBaumaschineAmount = loadBaumaschineById.getAmount();
                addBaumaschinenNameEditText.setText(loadBaumaschineById.getMachineName());
                addBaumaschinenAnzahlEditText.setText(Integer.toString(editableBaumaschineAmount));
                addBaumaschinenPricePerDayEditText.setText(loadBaumaschineById.getPricePerDay().toString());
                addBaumaschinenPricePerWeekendEditText.setText(loadBaumaschineById.getPricePerWeekend().toString());
                addBaumaschinenPricePerMonthEditText.setText(loadBaumaschineById.getPricePerMonth().toString());

                //check if machine has entries for parameters
                if (loadBaumaschineById.getOperatingHours() != null) {
                    addBaumaschinenOperatingHours.setText(loadBaumaschineById.getOperatingHours().toString());
                }
                if (loadBaumaschineById.getAmountOfGas() != null) {
                    addBaumaschinenAmountOfGas.setText(loadBaumaschineById.getOperatingHours().toString());
                }
                if (loadBaumaschineById.getDegreeOfWear() != null) {
                    addBaumaschinenDegreeOfWear.setText(loadBaumaschineById.getDegreeOfWear());
                }

                //when there is only one machine, disable functions to alter amount and other params
                if (editableBaumaschineAmount == 1) {
                    addBaumaschinenAnzahlEditText.setFocusable(false);
                    addBaumaschinenAnzahlEditText.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                    addBaumaschinenAnzahlEditText.setEnabled(false);
                    addBaumaschinenOperatingHours.setEnabled(false);
                    addBaumaschinenAmountOfGas.setEnabled(false);
                    addBaumaschinenDegreeOfWear.setEnabled(false);
                }

            }


        }
        /*listen from which activity AddBaumaschinenActivity was called and adapt to context with different methods*/
        addBaumaschinenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intent.getStringExtra("Class").equals("MainPageActivity")) {
                    insertNewBaumaschine();
                }
                if (intent.getStringExtra("Class").equals("BaumaschinenListAdapter")) {
                    updateBaumaschine();
                }
            }


        });
    }

    private void insertNewBaumaschine() {
        String baumaschinenName = addBaumaschinenNameEditText.getText().toString();
        String baumaschinenAnzahl = addBaumaschinenAnzahlEditText.getText().toString();
        String baumaschinenPricePerDay = addBaumaschinenPricePerDayEditText.getText().toString();
        String baumaschinenPricePerWeekend = addBaumaschinenPricePerWeekendEditText.getText().toString();
        String baumaschinenPricePerMonth = addBaumaschinenPricePerMonthEditText.getText().toString();
        if (baumaschinenAnzahl.equals("1")) {
            addBaumaschinenOperatingHours.setEnabled(true);
            addBaumaschinenDegreeOfWear.setEnabled(true);
            addBaumaschinenAmountOfGas.setEnabled(true);
        }
        String baumaschinenOperatingHours = addBaumaschinenOperatingHours.getText().toString();
        String baumaschinenDegreeOfWear = addBaumaschinenDegreeOfWear.getText().toString();
        String baumaschinenAmountOfGas = addBaumaschinenAmountOfGas.getText().toString();


        addBaumaschinenViewModel = new ViewModelProvider(this).get(AddBaumaschinenViewModel.class);
        if (baumaschinenName.trim().isEmpty() || baumaschinenAnzahl.trim().isEmpty() || baumaschinenPricePerDay.trim().isEmpty() || baumaschinenPricePerWeekend.trim().isEmpty() || baumaschinenPricePerMonth.trim().isEmpty()) {
            Toast.makeText(this, "Bitte alle Felder ausfüllen!", Toast.LENGTH_LONG).show();
            return;
        }

        Integer anzahl = Integer.parseInt(baumaschinenAnzahl);
        BigDecimal pricePerDay = new BigDecimal(baumaschinenPricePerDay);
        BigDecimal pricePerWeekend = new BigDecimal(baumaschinenPricePerWeekend);
        BigDecimal pricePerMonth = new BigDecimal(baumaschinenPricePerMonth);
        Double operatingHours;
        if (baumaschinenOperatingHours.isEmpty()) {
            operatingHours = null;
        } else {
            operatingHours = Double.parseDouble(baumaschinenOperatingHours);
        }
        addBaumaschinenViewModel.insert(new Baumaschine(baumaschinenName, anzahl, pricePerDay, pricePerWeekend, pricePerMonth, operatingHours, baumaschinenDegreeOfWear, baumaschinenAmountOfGas));
        finish();
    }


    private void updateBaumaschine() {
        String baumaschinenName = addBaumaschinenNameEditText.getText().toString();
        String baumaschinenAnzahl = addBaumaschinenAnzahlEditText.getText().toString();
        String baumaschinenPricePerDay = addBaumaschinenPricePerDayEditText.getText().toString();
        String baumaschinenPricePerWeekend = addBaumaschinenPricePerWeekendEditText.getText().toString();
        String baumaschinenPricePerMonth = addBaumaschinenPricePerMonthEditText.getText().toString();
        String baumaschinenOperatingHours = addBaumaschinenOperatingHours.getText().toString();
        String baumaschinenDegreeOfWear = addBaumaschinenDegreeOfWear.getText().toString();
        String baumaschinenAmountOfGas = addBaumaschinenAmountOfGas.getText().toString();

        if (baumaschinenName.trim().isEmpty() || baumaschinenAnzahl.trim().isEmpty() || baumaschinenPricePerDay.trim().isEmpty() || baumaschinenPricePerWeekend.trim().isEmpty() || baumaschinenPricePerMonth.trim().isEmpty()) {
            Toast.makeText(this, "Bitte alle Felder ausfüllen!", Toast.LENGTH_LONG).show();
            return;
        }
        Integer anzahl = Integer.parseInt(baumaschinenAnzahl);
        BigDecimal pricePerDay = new BigDecimal(baumaschinenPricePerDay);
        BigDecimal pricePerWeekend = new BigDecimal(baumaschinenPricePerWeekend);
        BigDecimal pricePerMonth = new BigDecimal(baumaschinenPricePerMonth);
        Baumaschine baumaschine = modifyBaumaschineViewModel.getBaumaschineById(intent.getExtras().getInt("baumaschineneRowId"));
        baumaschine.setMachineName(baumaschinenName);

        baumaschine.setAmount(anzahl);
        baumaschine.setPricePerDay(pricePerDay);
        baumaschine.setPricePerWeekend(pricePerWeekend);
        baumaschine.setPricePerMonth(pricePerMonth);

        if (loadBaumaschineById.getOperatingHours() != null) {
            Double operatingHours = Double.parseDouble(baumaschinenOperatingHours);
            baumaschine.setOperatingHours(operatingHours);
        }

        if (loadBaumaschineById.getAmountOfGas() != null) {
            baumaschine.setAmountOfGas(baumaschinenAmountOfGas);
        }

        if (loadBaumaschineById.getDegreeOfWear() != null) {
            baumaschine.setDegreeOfWear(baumaschinenDegreeOfWear);
        }

        Log.d(TAG, "ROW_ID vor setzen: " + baumaschine.getIdBaumaschine());
        modifyBaumaschineViewModel.update(baumaschine);
        finish();
    }


    public class InputFilterMinAmount implements InputFilter {
        private int minValue;

        public InputFilterMinAmount(int minValue) {
            this.minValue = minValue;
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                int inputValue = Integer.parseInt(dest.subSequence(0, dstart).toString() + source + dest.subSequence(dend, dest.length()));
                if (inRange(minValue, inputValue)) {
                    return null;
                }
            } catch (NumberFormatException nfe) {
            }
            return "";
        }
        private boolean inRange(int min, int input){
            return input > min;
        }
    }

}