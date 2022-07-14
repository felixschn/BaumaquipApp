package com.example.rentalApplication.ui.Baumaschine;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalApplication.R;
import com.example.rentalApplication.adapter.ArchivedBaumaschineListAdapter;
import com.example.rentalApplication.models.Baumaschine;
import com.example.rentalApplication.models.Stuecklisteneintrag;
import com.example.rentalApplication.models.Vertrag;
import com.example.rentalApplication.ui.Vertraege.Stuecklisteneintrag.AddStuecklisteneintragViewModel;
import com.example.rentalApplication.ui.Vertraege.VertragViewModel;

import java.util.ArrayList;
import java.util.List;

public class ArchivedBaumaschinenActivity extends AppCompatActivity implements BaumaschinenClickListener {
    private RecyclerView recyclerView;
    private BaumaschinenViewModel baumaschinenViewModel;
    private ModifyBaumaschineViewModel modifyBaumaschineViewModel;
    private AddStuecklisteneintragViewModel addStuecklisteneintragViewModel;
    private Baumaschine restoreBaumaschine;
    private ArchivedBaumaschineListAdapter archivedBaumaschineListAdapter;
    private TextView emptyRecyclerView;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.archived_baumaschinen);
        recyclerView = findViewById(R.id.archivedBaumaschineRecyclerView);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        emptyRecyclerView = findViewById(R.id.emptyArchivedBaumaschinenRecyclerviewTextView);
        //creating new Adapter for the recyclerview and within this adapter send an instance of this activity, that the adapter can call this activities delete method
        archivedBaumaschineListAdapter = new ArchivedBaumaschineListAdapter(this, this);
        recyclerView.setAdapter(archivedBaumaschineListAdapter);

        baumaschinenViewModel = new ViewModelProvider(this).get(BaumaschinenViewModel.class);
        baumaschinenViewModel.getAllArchivedBaumaschine().observe(this, new Observer<List<Baumaschine>>() {
            @Override
            public void onChanged(List<Baumaschine> baumaschines) {
                archivedBaumaschineListAdapter.setBaumaschinen(baumaschines);
                recyclerViewVisibility();
            }
        });
    }


    public void deleteBaumaschine(Baumaschine baumaschine) {
        Boolean isArchived = true;
        addStuecklisteneintragViewModel = new ViewModelProvider(this).get(AddStuecklisteneintragViewModel.class);
        List<Stuecklisteneintrag> getAllStuecklisteneintrag = addStuecklisteneintragViewModel.getAllStuecklisteneintrag(isArchived);
        for (int i = 0; i < getAllStuecklisteneintrag.size(); i++) {
            if (baumaschine.getIdBaumaschine() == getAllStuecklisteneintrag.get(i).getIdBaumaschine()) {
                Toast.makeText(this, R.string.not_removable_machine, Toast.LENGTH_SHORT).show();
                return;
            }
        }
        baumaschinenViewModel.delete(baumaschine);
    }

    public void restoreBaumaschine(int id) {
        modifyBaumaschineViewModel = new ViewModelProvider(this).get(ModifyBaumaschineViewModel.class);
        restoreBaumaschine = modifyBaumaschineViewModel.getBaumaschineById(id);
        restoreBaumaschine.setArchived(false);
        modifyBaumaschineViewModel.update(restoreBaumaschine);
        finish();
    }

    public void recyclerViewVisibility() {
        if (archivedBaumaschineListAdapter.getItemCount() == 0) {
            recyclerView.setVisibility(View.GONE);
            emptyRecyclerView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyRecyclerView.setVisibility(View.GONE);

        }
    }

    @Override
    public void onPositionClicked(int position) {

    }
}
