package com.example.rentalApplication.ui.Vertraege;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.rentalApplication.R;
import com.example.rentalApplication.adapter.BaumaschinenListAdapter;
import com.example.rentalApplication.models.Baumaschine;
import com.example.rentalApplication.models.Vertrag;
import com.example.rentalApplication.persistence.BaumaschinenDao;
import com.example.rentalApplication.persistence.BaumaschinenRepository;
import com.example.rentalApplication.ui.Baumaschine.BaumaschinenViewModel;
import com.example.rentalApplication.ui.Kunde.AddKundenViewModel;
import com.example.rentalApplication.ui.Kunde.KundenViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddVertragActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinnerBaumaschine, spinnerKunde;
    private EditText selectBeginnLeihe, selectEndeLeihe;
    private Button addVertragButton;
    private AddKundenViewModel addKundenViewModel;
    private AddVertragViewModel addVertragViewModel;
    private BaumaschinenViewModel baumaschinenViewModel;
    private BaumaschinenRepository baumaschinenRepository;
    private List<String> baumaschineList = new ArrayList<String>();
    private String spinnerBaumaschinenSelected;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vertrag);
        spinnerBaumaschine = findViewById(R.id.spinnerBaumaschine);
        spinnerKunde = findViewById(R.id.spinnerKunde);
        selectBeginnLeihe = findViewById(R.id.dateBeginnLeihe);
        selectEndeLeihe = findViewById(R.id.dateEndeLeihe);

        baumaschinenViewModel = new ViewModelProvider(this).get(BaumaschinenViewModel.class);


        baumaschinenViewModel.getAllBaumaschinen().observe(this, baumaschines -> {
            for (int i = 0; i < baumaschines.size(); i++) {
                baumaschineList.add(baumaschines.get(i).getMachineName());
                System.out.println(baumaschines.get(i).getRowid());
            }
        });
        /*List<Baumaschine> vertragBaumaschineList = baumaschinenViewModel.getAllBaumaschinen().getValue();
        vertragBaumaschineList.addAll(baumaschinenViewModel.getAllBaumaschinen().getValue());*/
        ArrayAdapter<String> spinnerBaumaschinenAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, baumaschineList);
        spinnerBaumaschinenAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBaumaschinenAdapter.notifyDataSetChanged();

        spinnerBaumaschinenAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBaumaschine.setAdapter(spinnerBaumaschinenAdapter);




       /* addVertragButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertNewVertrag();
            }
        });*/

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getItemAtPosition(position).equals("Baumaschine auswählen")) {
        } else {
            String item = parent.getItemAtPosition(position).toString();
            Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
