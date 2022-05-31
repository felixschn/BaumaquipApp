package com.example.rentalApplication.ui.Baumaschine;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.archived_baumaschinen);
        recyclerView = findViewById(R.id.archivedBaumaschineRecyclerView);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //creating new Adapter for the recyclerview and within this adapter send an instance of this activity, that the adapter can call this activities delete method
        final ArchivedBaumaschineListAdapter archivedBaumaschineListAdapter = new ArchivedBaumaschineListAdapter(this, this);
        recyclerView.setAdapter(archivedBaumaschineListAdapter);

        baumaschinenViewModel = new ViewModelProvider(this).get(BaumaschinenViewModel.class);
        baumaschinenViewModel.getAllArchivedBaumaschine().observe(this, baumaschines -> archivedBaumaschineListAdapter.setBaumaschinen(baumaschines));
    }

    public void deleteBaumaschine(Baumaschine baumaschine) {
        Boolean isArchived = true;
        addStuecklisteneintragViewModel = new ViewModelProvider(this).get(AddStuecklisteneintragViewModel.class);
        List<Stuecklisteneintrag> getAllStuecklisteneintrag = addStuecklisteneintragViewModel.getAllStuecklisteneintrag(isArchived);
        for (int i = 0; i < getAllStuecklisteneintrag.size(); i++) {
            if (baumaschine.getIdBaumaschine() == getAllStuecklisteneintrag.get(i).getIdBaumaschine()) {
                Toast.makeText(this, R.string.not_removable, Toast.LENGTH_SHORT).show();
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
        if (modifyBaumaschineViewModel.getAnyBaumaschine() == null) {
            finish();
        }

    }

    @Override
    public void onPositionClicked(int position) {

    }
}
