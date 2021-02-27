package com.example.rentalApplication.ui.Kunde;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rentalApplication.R;
import com.example.rentalApplication.models.Kunde;

public class AddKundenActivity extends AppCompatActivity {
    private EditText addKundenName, addKundenTelefonNumber, addKundenStreetName, addKundenStreetNumber, addKundenZip, addKundenLocation, addKundenConstructionSide, addKundenContactPerson;
    private Button addKundenButton;
    private AddKundenViewModel addKundenViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_kunden);
        addKundenButton = findViewById(R.id.addKundenButton);
        addKundenName = findViewById(R.id.addKundenName);
        addKundenTelefonNumber = findViewById(R.id.addKundenTelefonNumber);
        addKundenStreetName = findViewById(R.id.addKundenStreetName);
        addKundenStreetNumber = findViewById(R.id.addKundenStreetNumber);
        addKundenZip = findViewById(R.id.addKundenZip);
        addKundenLocation = findViewById(R.id.addKundenLocation);
        addKundenConstructionSide = findViewById(R.id.addKundenConstructionSide);
        addKundenContactPerson = findViewById(R.id.addKundenContactPerson);

        addKundenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertNewKunde();
            }
        });

    }
    private void insertNewKunde() {
        String kundenName = addKundenName.getText().toString();
        String kundenTelefonNumber = addKundenTelefonNumber.getText().toString();
        String kundenStreetName = addKundenStreetName.getText().toString();
        String kundenStreetNumber = addKundenStreetNumber.getText().toString();
        String kundenZip = addKundenZip.getText().toString();
        String kundenLocation = addKundenLocation.getText().toString();
        String kundenConstructionSide = addKundenConstructionSide.getText().toString();
        String kundenContactPerson = addKundenContactPerson.getText().toString();

        addKundenViewModel = new ViewModelProvider(this).get(AddKundenViewModel.class);
        if (kundenName.trim().isEmpty() || kundenTelefonNumber.trim().isEmpty() || kundenStreetName.trim().isEmpty() || kundenStreetNumber.trim().isEmpty() || kundenZip.trim().isEmpty() || kundenLocation.trim().isEmpty() || kundenConstructionSide.trim().isEmpty() || kundenContactPerson.trim().isEmpty()) {
            Toast.makeText(this, "Bitte alle Felder ausfüllen!", Toast.LENGTH_LONG).show();
            return;
        }
        addKundenViewModel.insert(new Kunde(kundenName, kundenTelefonNumber, kundenStreetName, kundenStreetNumber, kundenZip, kundenLocation, kundenConstructionSide, kundenContactPerson));
        finish();
    }
}