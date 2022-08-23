package com.example.rentalApplication.ui.Vertraege;

import static java.lang.Math.max;
import static java.lang.Math.toIntExact;
import static java.time.temporal.ChronoUnit.DAYS;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
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
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class AddVertragActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, VertragBaumaschinenListClickListener, View.OnClickListener {

    private BaumaschinenViewModel baumaschinenViewModel;
    private KundenViewModel kundenViewModel;
    private AddStuecklisteneintragViewModel addStuecklisteneintragViewModel;
    private AddVertragViewModel addVertragViewModel;
    private EditText beginnVertrag, endeVertrag, discountOfRent;
    private LocalDate begin, end;
    private Button addVertragButton;
    private static final String TAG = "AddVertragActivity";
    private FloatingActionButton increaseButton, decreaseButton, addBaumaschinenListButton;
    private TextView amountTextView, emptyRecyclerViewTextView, announceRecyclerView, sumOfRent, discountTextView;
    int amountInt, maxAmount;
    private CustomBaumaschinenAdapter customBaumaschinenAdapter;
    private RecyclerView recyclerView;
    private AddVertragBaumaschineListAdapter addVertragBaumaschineListAdapter;
    private Spinner baumaschinenSpinner, kundenSpinner;
    private Baumaschine selectedBaumaschineFromSpinner;
    private Kunde selectedKundeFromSpinner;
    private Switch switchDiscountMode;
    private boolean switchOn = false;
    private BigDecimal discount, sumWithoutDiscount, discountFromSum;


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
                    Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.endBeforeBegin), Toast.LENGTH_SHORT).show();
                    end = begin;
                }
                customBaumaschinenAdapter.notifyDataSetChanged();
                endeVertrag.setText(Converters.localDateToString(end));
            }
        };


        beginnVertrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddVertragActivity.this, dateBeginnLeihe, begin.getYear(), begin.getMonthValue() - 1, begin.getDayOfMonth()).show();
                addVertragBaumaschineListAdapter.clearAddVertragBaumaschinenRecyclerView();
                sumOfRent.setVisibility(View.INVISIBLE);
                discountOfRent.setVisibility(View.INVISIBLE);
                switchDiscountMode.setVisibility(View.INVISIBLE);
                recyclerViewVisibility();
            }
        });

        endeVertrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddVertragActivity.this, dateEndeLeihe, end.getYear(), end.getMonthValue() - 1, end.getDayOfMonth()).show();
                addVertragBaumaschineListAdapter.clearAddVertragBaumaschinenRecyclerView();
                sumOfRent.setVisibility(View.INVISIBLE);
                discountOfRent.setVisibility(View.INVISIBLE);
                switchDiscountMode.setVisibility(View.INVISIBLE);
                recyclerViewVisibility();
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

        discountTextView = findViewById(R.id.textViewDiscount);
        sumOfRent = findViewById(R.id.textSumOfRent);
        discountOfRent = findViewById(R.id.editTextDiscountofRent);
        switchDiscountMode = findViewById(R.id.switchDiscountMode);
        discountTextView.setVisibility(View.INVISIBLE);
        sumOfRent.setVisibility(View.INVISIBLE);
        discountOfRent.setVisibility(View.INVISIBLE);
        switchDiscountMode.setVisibility(View.INVISIBLE);


        discountOfRent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    Log.d(TAG, "Char: " + s);


                    if (new BigDecimal(s.toString()).compareTo(calcSumOfRent()) > 0) {
                        Log.d(TAG, "Discount zu groß!");

                    }
                }


            }

            @Override
            public void afterTextChanged(Editable s) {
                sumOfRent.setText(calcSumOfRent().toString());
            }
        });

        switchDiscountMode.setText("€");
        switchDiscountMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switchOn = isChecked;
                if (isChecked) {
                    switchDiscountMode.setText("%");
                    clearDiscount();
                } else {
                    switchDiscountMode.setText("€");
                    clearDiscount();
                }

            }
        });
    }


    /*method to in- or decrease the amount of the chosen Baumaschine through the spinner:
     * -if the the amount */
    private void buttonVisibility() {
        amountTextView.setVisibility(View.VISIBLE);
        addBaumaschinenListButton.setVisibility(View.VISIBLE);
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
        if (amountInt > maxAmount) {
            addBaumaschinenListButton.setVisibility(View.INVISIBLE);
            amountTextView.setVisibility(View.INVISIBLE);
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
                if (baumaschinenSpinner.getSelectedItem() == null) {
                    Toast.makeText(this, R.string.empty_baumaschine_spinner, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (kundenSpinner.getSelectedItem() == null) {
                    Toast.makeText(this, R.string.empty_kunde_spinner, Toast.LENGTH_SHORT).show();
                    return;
                }
                /*check if list of Stuecklisteintrag-Id is not empty, to avoid Verträge without stuecklisteintrag-Ids*/
                if (!stuecklisteIds.isEmpty() && sumWithoutDiscount.compareTo(BigDecimal.ZERO) > 0) {
                    addVertragViewModel.insert(new Vertrag(stuecklisteIds, selectedKundeFromSpinner.getIdKunde(), begin, end, sumWithoutDiscount, discountFromSum));
                    finish();
                } else {
                    Toast.makeText(this, R.string.select_machines, Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, R.string.select_date, Toast.LENGTH_SHORT).show();
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
            sumOfRent.setText(String.format("%s€", calcSumOfRent().toString()));
            sumOfRent.setVisibility(View.VISIBLE);
            discountTextView.setVisibility(View.VISIBLE);
            discountOfRent.setText("0");
            discountOfRent.setVisibility(View.VISIBLE);
            switchDiscountMode.setVisibility(View.VISIBLE);

        }

    }

    public int getSelectedBaumaschinenAmount() {
        return amountInt;
    }


    //calculating price for the machine renting with different scenarios for example a renting during the weekend and therefore applying the weekend price
    public BigDecimal calcPriceForRent() {
        BigDecimal price;
        long rental_period = ChronoUnit.DAYS.between(begin, end) + 1;  // +1 to include last day

        if ((4 == rental_period) &&
                (DayOfWeek.FRIDAY == begin.getDayOfWeek()) &&
                (DayOfWeek.MONDAY == end.getDayOfWeek())) {

            price = selectedBaumaschineFromSpinner.getPricePerWeekend();
        } else if (30 < rental_period) {
            price = selectedBaumaschineFromSpinner.getPricePerMonth().multiply(BigDecimal.valueOf(rental_period / 30));
        } else {
            price = selectedBaumaschineFromSpinner.getPricePerDay().multiply(BigDecimal.valueOf(rental_period));

        }
        return price.multiply(BigDecimal.valueOf(getSelectedBaumaschinenAmount()));
    }

    public BigDecimal calcSumOfRent() {
        List<Stuecklisteneintrag> calcList = addVertragBaumaschineListAdapter.getStueckliste();
        BigDecimal bigDeciamlsumOfRent = new BigDecimal(0);

        for (int i = 0; i < calcList.size(); i++) {
            BigDecimal stuecklisteneintragSum = new BigDecimal(String.valueOf(calcList.get(i).getPrice()));
            bigDeciamlsumOfRent = bigDeciamlsumOfRent.add(stuecklisteneintragSum);
        }
        sumWithoutDiscount = new BigDecimal(String.valueOf(bigDeciamlsumOfRent));

        try {
            discount = new BigDecimal(discountOfRent.getText().toString().replace(",", ""));

        } catch (NumberFormatException e) {
            // catch NumberFormatException if user has not altered the edittext discount
            // if edittext was not edited than use 0 as discount value
            discount = new BigDecimal(0);
        }
        if (!switchOn) {
            discountOfRent.setFilters(new InputFilter[]{
                    new InputFilterMaxSum(0, bigDeciamlsumOfRent.intValue()) {
                    }
            });
            bigDeciamlsumOfRent = bigDeciamlsumOfRent.subtract(new BigDecimal(String.valueOf(discount)));
            discountFromSum = new BigDecimal(String.valueOf(sumWithoutDiscount.subtract(bigDeciamlsumOfRent)));
        } else {
            discountOfRent.setFilters(new InputFilter[]{
                    new InputFilterMaxSum(0, 100) {
                    }
            });
            bigDeciamlsumOfRent = bigDeciamlsumOfRent.subtract(bigDeciamlsumOfRent.multiply(discount).divide(new BigDecimal(100)));
            discountFromSum = new BigDecimal(String.valueOf(sumWithoutDiscount.subtract(bigDeciamlsumOfRent)));
        }

        sumOfRent.setText(String.format("%s€", bigDeciamlsumOfRent.toString()));


        return bigDeciamlsumOfRent;
    }

    public void clearDiscount() {
        discountOfRent.setText("0");
    }

    public int getAvailableBaumaschinenAmount(AddStuecklisteneintragViewModel addStuecklisteneintragViewModel, Baumaschine selectedBaumaschineFromSpinner) {
        List<Stuecklisteneintrag> existingStuecklisteneintrageForBaumaschine = addStuecklisteneintragViewModel.getStuecklisteneintragForDate(begin, end, selectedBaumaschineFromSpinner.getIdBaumaschine());
        int maxRentingAmountPerDay = 0;
        int maxRentingAmount = 0;

        if (DAYS.between(begin, end) == 0) {
            for (int i = 0; i < existingStuecklisteneintrageForBaumaschine.size(); i++) {
                if ((existingStuecklisteneintrageForBaumaschine.get(i).getBeginDate().isBefore(begin.plusDays(0)) ||
                        existingStuecklisteneintrageForBaumaschine.get(i).getBeginDate().isEqual(begin.plusDays(0))) &&
                        (existingStuecklisteneintrageForBaumaschine.get(i).getEndDate().isAfter(begin.plusDays(0)) ||
                                existingStuecklisteneintrageForBaumaschine.get(i).getEndDate().isEqual(begin.plusDays(0)))) {

                    maxRentingAmountPerDay = maxRentingAmountPerDay + existingStuecklisteneintrageForBaumaschine.get(i).getAmount();
                }

            }
            maxRentingAmount = max(maxRentingAmountPerDay, maxRentingAmount);
            maxRentingAmountPerDay = 0;

        }

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

    public class InputFilterMaxSum implements InputFilter {
        private int minValue, maxValue;

        public InputFilterMaxSum(int minValue, int maxValue) {
            this.minValue = minValue;
            this.maxValue = maxValue;
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                int inputValue = Integer.parseInt(dest.subSequence(0, dstart).toString() + source + dest.subSequence(dend, dest.length()));
                if (inRange(minValue, maxValue, inputValue)) {
                    return null;
                }

            } catch (NumberFormatException nfe) {
            }
            return "";
        }

        private boolean inRange(int min, int max, int input) {
            return max > min ? input >= min && input <= max : input >= max && input <= min;
        }
    }


}
