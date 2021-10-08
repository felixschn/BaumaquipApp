package com.example.rentalApplication.ui.Baumaschine;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalApplication.R;
import com.example.rentalApplication.adapter.ArchivedBaumaschineListAdapter;
import com.example.rentalApplication.adapter.BaumaschinenListAdapter;
import com.example.rentalApplication.models.Baumaschine;

import java.util.List;

public class ArchivedBaumaschinenActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BaumaschinenViewModel baumaschinenViewModel;
    private ModifyBaumaschineViewModel modifyBaumaschineViewModel;
    private Baumaschine restoreBaumaschine;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.archived_baumaschinen);
        recyclerView = findViewById(R.id.archivedBaumaschineRecyclerView);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //creating new Adapter for the recyclerview and within this adapter send an instance of this activity, that the adapter can call this activities delete method
        final ArchivedBaumaschineListAdapter archivedBaumaschineListAdapter = new ArchivedBaumaschineListAdapter(this);
        recyclerView.setAdapter(archivedBaumaschineListAdapter);

        baumaschinenViewModel = new ViewModelProvider(this).get(BaumaschinenViewModel.class);
        baumaschinenViewModel.getAllArchivedBaumaschine().observe(this, baumaschines -> archivedBaumaschineListAdapter.setBaumaschinen(baumaschines));
    }

    public void deleteBaumaschine(Baumaschine baumaschine) {
        baumaschinenViewModel.delete(baumaschine);
    }

    public void restoreBaumaschine(int id) {
        modifyBaumaschineViewModel = new ViewModelProvider(this).get(ModifyBaumaschineViewModel.class);
        restoreBaumaschine = modifyBaumaschineViewModel.loadBaumaschineById(id);
        restoreBaumaschine.setArchived(false);
        modifyBaumaschineViewModel.update(restoreBaumaschine);
        if (modifyBaumaschineViewModel.getAnyBaumaschine() == null) {
            finish();
        }

    }
}
