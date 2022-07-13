package com.example.rentalApplication.ui.Vertraege;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalApplication.R;
import com.example.rentalApplication.adapter.ArchivedVertragListAdapter;
import com.example.rentalApplication.models.Stuecklisteneintrag;
import com.example.rentalApplication.models.Vertrag;
import com.example.rentalApplication.ui.Vertraege.Stuecklisteneintrag.AddStuecklisteneintragViewModel;

import java.util.List;

public class ArchivedVertragActivity extends AddVertragActivity implements VertragClickListener {
    private RecyclerView recyclerView;
    private VertragViewModel vertragViewModel;

    private ModifyVertragViewModel modifyVertragViewModel;
    private Vertrag restoreVertrag;
    private Stuecklisteneintrag stuecklisteneintrag;

    private TextView emptyRecyclerView;
    public ArchivedVertragListAdapter archivedVertragListAdapter;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.archived_vertrag);
        recyclerView = findViewById(R.id.archivedVertragRecyclerView);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        emptyRecyclerView = findViewById(R.id.emptyArchivedVertraegeRecyclerviewTextView);
        archivedVertragListAdapter = new ArchivedVertragListAdapter(this, this);
        recyclerView.setAdapter(archivedVertragListAdapter);

        vertragViewModel = new ViewModelProvider(this).get(VertragViewModel.class);
        vertragViewModel.getAllArchivedVertrag().observe(this, new Observer<List<Vertrag>>() {
            @Override
            public void onChanged(List<Vertrag> vertrags) {
                archivedVertragListAdapter.setVertrag(vertrags);
                recyclerViewVisibilityArchived();
            }
        });


    }


    public void deleteArchivedVertrag(Vertrag vertrag) {

        vertragViewModel.delete(vertrag);
    }

    public void restoreVertrag(int id){
        modifyVertragViewModel = new ViewModelProvider(this).get(ModifyVertragViewModel.class);
        restoreVertrag = modifyVertragViewModel.loadVertragById(id);
        restoreVertrag.setArchived(false);
        modifyVertragViewModel.update(restoreVertrag);
    }

    public void recyclerViewVisibilityArchived() {
        if (archivedVertragListAdapter.getItemCount() == 0) {
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
