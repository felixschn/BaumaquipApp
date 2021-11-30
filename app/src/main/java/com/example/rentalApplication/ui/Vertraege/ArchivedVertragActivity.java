package com.example.rentalApplication.ui.Vertraege;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalApplication.R;
import com.example.rentalApplication.adapter.ArchivedVertragListAdapter;
import com.example.rentalApplication.models.Vertrag;

import java.util.List;

public class ArchivedVertragActivity extends AddVertragActivity implements VertragClickListener {
    private RecyclerView recyclerView;
    private VertragViewModel vertragViewModel;
    private ModifyVertragViewModel modifyVertragViewModel;
    private Vertrag restoreVertrag;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.archived_vertrag);
        recyclerView = findViewById(R.id.archivedVertragRecyclerView);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final ArchivedVertragListAdapter archivedVertragListAdapter = new ArchivedVertragListAdapter(this, this);
        recyclerView.setAdapter(archivedVertragListAdapter);

        vertragViewModel = new ViewModelProvider(this).get(VertragViewModel.class);
        vertragViewModel.getAllArchivedVertrag().observe(this, new Observer<List<Vertrag>>() {
            @Override
            public void onChanged(List<Vertrag> vertrags) {
                archivedVertragListAdapter.setVertrag(vertrags);
            }
        });
        }

    //TODO: if vertrag is deleted, Stuecklisteneintrag should also be deleted!
    public void deleteArchivedVertrag(Vertrag vertrag){ vertragViewModel.delete(vertrag);}

    @Override
    public void onPositionClicked(int position) {

    }
}
