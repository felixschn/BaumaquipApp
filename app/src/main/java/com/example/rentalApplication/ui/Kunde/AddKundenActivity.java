package com.example.rentalApplication.ui.Kunde;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.rentalApplication.R;
import com.example.rentalApplication.models.Kunde;

import java.util.concurrent.ExecutionException;

public class AddKundenActivity extends AppCompatActivity {
    private EditText addKundenName, addKundenTelefonNumber, addKundenStreetName, addKundenStreetNumber, addKundenEmail, addKundenZip, addKundenLocation, addKundenConstructionSide, addKundenContactPerson;
    private Button addKundenButton;
    private AddKundenViewModel addKundenViewModel;
    private ModifyKundenViewModel modifyKundenViewModel;
    private Intent intent;
    private Kunde loadKundeById;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_kunden);
        addKundenButton = findViewById(R.id.addKundenButton);
        addKundenName = findViewById(R.id.addKundenName);
        addKundenTelefonNumber = findViewById(R.id.addKundenTelefonNumber);
        addKundenEmail = findViewById(R.id.addKundenEmail);
        addKundenStreetName = findViewById(R.id.addKundenStreetName);
        addKundenStreetNumber = findViewById(R.id.addKundenStreetNumber);
        addKundenZip = findViewById(R.id.addKundenZip);
        addKundenLocation = findViewById(R.id.addKundenLocation);
        addKundenConstructionSide = findViewById(R.id.addKundenConstructionSide);
        addKundenContactPerson = findViewById(R.id.addKundenContactPerson);

        addKundenButton.setText(R.string.button_add);

        intent = this.getIntent();
        if (intent != null) {
            String activityString = intent.getStringExtra("Class");
            if (activityString.equals("KundenListAdapter")) {
                modifyKundenViewModel = new ViewModelProvider(this).get(ModifyKundenViewModel.class);
                loadKundeById = modifyKundenViewModel.loadKundeById(intent.getExtras().getInt("kundeRowId"));
                addKundenName.setText(loadKundeById.getName());
                addKundenTelefonNumber.setText(loadKundeById.getTelefonNumber());
                addKundenEmail.setText(loadKundeById.getEmail());
                addKundenStreetName.setText(loadKundeById.getStreetName());
                addKundenStreetNumber.setText(loadKundeById.getStreetNumber());
                addKundenZip.setText(loadKundeById.getZip());
                addKundenLocation.setText(loadKundeById.getLocation());
                addKundenConstructionSide.setText(loadKundeById.getConstructionSide());
                addKundenContactPerson.setText(loadKundeById.getContactPerson());
                addKundenButton.setText(R.string.button_save);
            }
        }


        addKundenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intent.getStringExtra("Class").equals("MainPageActivity")) {
                    insertNewKunde();
                }
                if (intent.getStringExtra("Class").equals("KundenListAdapter")) {
                    updateKunde();
                }
            }
        });

    }

    private void insertNewKunde() {
        String kundenName = addKundenName.getText().toString();
        String kundenTelefonNumber = addKundenTelefonNumber.getText().toString();
        String kundenEmail = addKundenEmail.getText().toString();
        String kundenStreetName = addKundenStreetName.getText().toString();
        String kundenStreetNumber = addKundenStreetNumber.getText().toString();
        String kundenZip = addKundenZip.getText().toString();
        String kundenLocation = addKundenLocation.getText().toString();
        String kundenConstructionSide = addKundenConstructionSide.getText().toString();
        String kundenContactPerson = addKundenContactPerson.getText().toString();

        addKundenViewModel = new ViewModelProvider(this).get(AddKundenViewModel.class);
        if (kundenName.trim().isEmpty() || kundenTelefonNumber.trim().isEmpty() || kundenEmail.trim().isEmpty() || kundenStreetName.trim().isEmpty() || kundenStreetNumber.trim().isEmpty() || kundenZip.trim().isEmpty() || kundenLocation.trim().isEmpty() || kundenConstructionSide.trim().isEmpty() || kundenContactPerson.trim().isEmpty()) {
            Toast.makeText(this, R.string.fill_all_fields, Toast.LENGTH_LONG).show();
            return;
        }
        addKundenViewModel.insert(new Kunde(kundenName, kundenTelefonNumber, kundenEmail, kundenStreetName, kundenStreetNumber, kundenZip, kundenLocation, kundenConstructionSide, kundenContactPerson));
        finish();
    }

    private void updateKunde() {
        String kundenName = addKundenName.getText().toString();
        String kundenTelefonNumber = addKundenTelefonNumber.getText().toString();
        String kundenEmail = addKundenEmail.getText().toString();
        String kundenStreetName = addKundenStreetName.getText().toString();
        String kundenStreetNumber = addKundenStreetNumber.getText().toString();
        String kundenZip = addKundenZip.getText().toString();
        String kundenLocation = addKundenLocation.getText().toString();
        String kundenConstructionSide = addKundenConstructionSide.getText().toString();
        String kundenContactPerson = addKundenContactPerson.getText().toString();

        //make sure, that every field is filled
        if (kundenName.trim().isEmpty() || kundenTelefonNumber.trim().isEmpty() || kundenEmail.trim().isEmpty() || kundenStreetName.trim().isEmpty() || kundenStreetNumber.trim().isEmpty() || kundenZip.trim().isEmpty() || kundenLocation.trim().isEmpty() || kundenConstructionSide.trim().isEmpty() || kundenContactPerson.trim().isEmpty()) {
            Toast.makeText(this, R.string.fill_all_fields, Toast.LENGTH_LONG).show();
            return;
        }
        Kunde kunde = modifyKundenViewModel.loadKundeById(intent.getExtras().getInt("kundeRowId"));
        kunde.setName(kundenName);
        kunde.setTelefonNumber(kundenTelefonNumber);
        kunde.setEmail(kundenEmail);
        kunde.setStreetName(kundenStreetName);
        kunde.setStreetNumber(kundenStreetNumber);
        kunde.setZip(kundenZip);
        kunde.setLocation(kundenLocation);
        kunde.setConstructionSide(kundenConstructionSide);
        kunde.setConstructionSide(kundenConstructionSide);

        //creating new Kunde for updating the old one
        modifyKundenViewModel.update(kunde);
        finish();
    }
}