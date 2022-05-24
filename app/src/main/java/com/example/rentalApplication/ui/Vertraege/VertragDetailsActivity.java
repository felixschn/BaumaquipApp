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
import com.example.rentalApplication.ui.Vertraege.Stuecklisteneintrag.AddStuecklisteneintragViewModel;

import java.util.ArrayList;
import java.util.List;

public class VertragDetailsActivity extends AppCompatActivity implements VertragDetailsClickListener {
    private static final String TAG = "VertragDetailsActivity.java";
    private RecyclerView recyclerView;
    private boolean hideButton;
    private Intent intent;
    private Vertrag vertrag;
    private Kunde kunde;
    private List<Baumaschine> baumaschineVertragDetailsList;
    private List<Integer> baumaschineContractAmount;
    private TextView vertragDetailsTextView, vertragDetailsIdTextView, vertragDetailsKundeTextView, vertragDetailsKundeNameTextView, vertragDetailsStartDateTextTextView, vertragDetailsStartDateTextView, vertragDetailsEndDateTextTextView, vertragDetailsEndDateTextView, vertragDetailsSumTextView, vertragDetailsSum, vertragDetailsDiscountText, vertragDetailsDiscount;
    private ModifyVertragViewModel modifyVertragViewModel;
    private ModifyKundenViewModel modifyKundenViewModel;
    private ModifyBaumaschineViewModel modifyBaumaschineViewModel;
    private AddStuecklisteneintragViewModel addStuecklisteneintragViewModel;
    private List<Stuecklisteneintrag> stuecklisteneintragListFromVertrag = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertrag_details);
        vertragDetailsTextView = findViewById(R.id.vertragDetails);
        vertragDetailsIdTextView = findViewById(R.id.vertragDetailsId);
        vertragDetailsKundeTextView = findViewById(R.id.vertragDetailsKunde);
        vertragDetailsKundeNameTextView = findViewById(R.id.vertragDetailsKundeId);
        vertragDetailsStartDateTextTextView = findViewById(R.id.vertragDetailsStartDateText);
        vertragDetailsStartDateTextView = findViewById(R.id.vertragDetailsStartDate);
        vertragDetailsEndDateTextTextView = findViewById(R.id.vertragDetailsEndDateText);
        vertragDetailsEndDateTextView = findViewById(R.id.vertragDetailsEndDate);
        vertragDetailsSumTextView = findViewById(R.id.vertragDetailsSumText);
        vertragDetailsSum = findViewById(R.id.vertragDetailsSum);
        vertragDetailsDiscountText = findViewById(R.id.vertragDetailsDiscountText);
        vertragDetailsDiscount = findViewById(R.id.vertragDetailsDiscount);

        recyclerView = findViewById(R.id.vertragDetailsRecyclerview);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final VertragDetailsListAdapter vertragDetailsListAdapter = new VertragDetailsListAdapter(this, this);
        recyclerView.setAdapter(vertragDetailsListAdapter);


        intent = this.getIntent();
        modifyVertragViewModel = new ViewModelProvider(this).get(ModifyVertragViewModel.class);

        modifyKundenViewModel = new ViewModelProvider(this).get(ModifyKundenViewModel.class);
        addStuecklisteneintragViewModel = new ViewModelProvider(this).get(AddStuecklisteneintragViewModel.class);
        modifyBaumaschineViewModel = new ViewModelProvider(this).get(ModifyBaumaschineViewModel.class);
        baumaschineVertragDetailsList = new ArrayList<>();
        baumaschineContractAmount = new ArrayList<>();
        if (intent != null) {
            vertrag = modifyVertragViewModel.loadVertragById(intent.getIntExtra("vertragRowId", 0));
            kunde = modifyKundenViewModel.loadKundeById(vertrag.getIdKunde());
            for (int i = 0; i < vertrag.getStuecklisteIds().size(); i++) {
                int stuecklisteneintragId = vertrag.getStuecklisteIds().get(i);
                Stuecklisteneintrag current = addStuecklisteneintragViewModel.stuecklisteneintragById(stuecklisteneintragId);
                //TODO somethings wrong with stuecklisteneintraege which are archived but shown like they are not
                if (!current.isArchived()) {
                    stuecklisteneintragListFromVertrag.add(addStuecklisteneintragViewModel.stuecklisteneintragById(stuecklisteneintragId));

                    baumaschineVertragDetailsList.add(modifyBaumaschineViewModel.getBaumaschineById(addStuecklisteneintragViewModel.stuecklisteneintragById(stuecklisteneintragId).getIdBaumaschine()));
                    try {
                        baumaschineContractAmount.add(addStuecklisteneintragViewModel.stuecklisteneintragById(stuecklisteneintragId).getAmount());
                    } catch (NullPointerException npe) {
                        npe.printStackTrace();
                        return;
                    }
                }
            }
            String activityString = intent.getStringExtra("Class");
            if (activityString.equals("ArchivedVertragListAdapter")) {
                hideButton = true;
            }
        } else {
            //TODO: catch if intent is null, e.g. close the activity
            return;
        }
        Log.d(TAG, "Vertrag Id: " + vertrag.getIdVertrag());
        vertragDetailsIdTextView.setText(String.valueOf(vertrag.getIdVertrag()));
        vertragDetailsKundeNameTextView.setText(kunde.getName());
        vertragDetailsStartDateTextView.setText(vertrag.getBeginnVertrag().toString());
        vertragDetailsEndDateTextView.setText(vertrag.getEndeVertrag().toString());
        vertragDetailsSum.setText(String.format("%s€", vertrag.getSumOfRent()));
        vertragDetailsDiscount.setText(String.format("%s€", vertrag.getDiscountOfRent()));

        vertragDetailsListAdapter.setBaumaschineVertragDetailsList(stuecklisteneintragListFromVertrag, baumaschineVertragDetailsList, baumaschineContractAmount);


    }

    public Boolean hideButtonStatus() {
        return hideButton;
    }

    public void archiveStuecklisteneintragFromVertrag(int id) {
        Stuecklisteneintrag current = addStuecklisteneintragViewModel.stuecklisteneintragById(id);
        current.setArchived(true);
        addStuecklisteneintragViewModel.update(current);
    }

    public Baumaschine getBaumaschineFromStuecklisteneintrag(int id) {
        return modifyBaumaschineViewModel.getBaumaschineById(id);
    }

    @Override
    public void onPositionClicked(int position) {

    }
}
