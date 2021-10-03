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

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.archived_baumaschinen);
        recyclerView = findViewById(R.id.archivedBaumaschineRecyclerView);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final ArchivedBaumaschineListAdapter archivedBaumaschineListAdapter = new ArchivedBaumaschineListAdapter();
        recyclerView.setAdapter(archivedBaumaschineListAdapter);

        baumaschinenViewModel = new ViewModelProvider(this).get(BaumaschinenViewModel.class);
        baumaschinenViewModel.getAllArchivedBaumaschine().observe(this, new Observer<List<Baumaschine>>() {
            @Override
            public void onChanged(List<Baumaschine> baumaschines) {
                archivedBaumaschineListAdapter.setBaumaschinen(baumaschines);
            }
        });

    }
}
