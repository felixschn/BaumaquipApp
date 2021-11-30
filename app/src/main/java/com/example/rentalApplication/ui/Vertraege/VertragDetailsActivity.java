package com.example.rentalApplication.ui.Vertraege;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalApplication.R;
import com.example.rentalApplication.adapter.VertragDetailsListAdapter;
import com.example.rentalApplication.models.Baumaschine;
import com.example.rentalApplication.models.Kunde;
import com.example.rentalApplication.models.Stuecklisteneintrag;
import com.example.rentalApplication.models.Vertrag;
import com.example.rentalApplication.ui.Baumaschine.ModifyBaumaschineViewModel;
import com.example.rentalApplication.ui.Kunde.ModifyKundenViewModel;

import java.util.ArrayList;
import java.util.List;

public class VertragDetailsActivity extends AppCompatActivity implements VertragDetailsClickListener {
    private static final String TAG = "VertragDetailsActivity.java";
    private RecyclerView recyclerView;
    private Intent intent;
    private Vertrag vertrag;
    private Kunde kunde;
    private List<Baumaschine> baumaschineVertragDetailsList;
    private Stuecklisteneintrag stuecklisteneintrag;
    private TextView vertragDetailsTextView, vertragDetailsIdTextView, vertragDetailsKundeTextView, vertragDetailsKundeNameTextView, vertragDetailsStartDateTextTextView, vertragDetailsStartDateTextView, vertragDetailsEndDateTextTextView, vertragDetailsEndDateTextView;
    private ModifyVertragViewModel modifyVertragViewModel;
    private ModifyKundenViewModel modifyKundenViewModel;
    private ModifyBaumaschineViewModel modifyBaumaschineViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertrag_details);
        vertragDetailsTextView = findViewById(R.id.vertragDetails);
        vertragDetailsIdTextView = findViewById(R.id.vertragDetailsId);
        vertragDetailsKundeTextView = findViewById(R.id.vertragDetailsKunde);
        vertragDetailsKundeNameTextView = findViewById(R.id. vertragDetailsKundeId);
        vertragDetailsStartDateTextTextView = findViewById(R.id.vertragDetailsStartDateText);
        vertragDetailsStartDateTextView = findViewById(R.id.vertragDetailsStartDate);
        vertragDetailsEndDateTextTextView = findViewById(R.id.vertragDetailsEndDateText);
        vertragDetailsEndDateTextView = findViewById(R.id.vertragDetailsEndDate);

        recyclerView = findViewById(R.id.vertragDetailsRecyclerview);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final VertragDetailsListAdapter vertragDetailsListAdapter = new VertragDetailsListAdapter(this, this);
        recyclerView.setAdapter(vertragDetailsListAdapter);

        intent = this.getIntent();
        modifyVertragViewModel = new ViewModelProvider(this).get(ModifyVertragViewModel.class);
        modifyKundenViewModel = new ViewModelProvider(this).get(ModifyKundenViewModel.class);
        modifyBaumaschineViewModel = new ViewModelProvider(this).get(ModifyBaumaschineViewModel.class);
        baumaschineVertragDetailsList = new ArrayList<>();
        if (intent != null){
            vertrag = modifyVertragViewModel.loadVertragById(intent.getIntExtra("vertragRowId",0));
            kunde = modifyKundenViewModel.loadKundeById(vertrag.getIdKunde());
            for(int i = 0; i < vertrag.getStuecklisteIds().size(); i++){
                baumaschineVertragDetailsList.add(modifyBaumaschineViewModel.getBaumaschineById(vertrag.getStuecklisteIds().get(i)));
            }
                    }
        Log.d(TAG, "Vertrag Id: " + vertrag.getIdVertrag());
        vertragDetailsIdTextView.setText(String.valueOf(vertrag.getIdVertrag()));
        vertragDetailsKundeNameTextView.setText(kunde.getName());
        vertragDetailsStartDateTextView.setText(vertrag.getBeginnVertrag().toString());
        vertragDetailsEndDateTextView.setText(vertrag.getEndeVertrag().toString());

        vertragDetailsListAdapter.setBaumaschineVertragDetailsList(baumaschineVertragDetailsList);


    }

    @Override
    public void onPositionClicked(int position) {

    }
}
