package com.example.rentalApplication.ui.Vertraege;

import android.os.Bundle;

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
    private AddStuecklisteneintragViewModel stuecklisteneintragViewModel;
    private ModifyVertragViewModel modifyVertragViewModel;
    private Vertrag restoreVertrag;
    private Stuecklisteneintrag stuecklisteneintrag;

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

        stuecklisteneintragViewModel = new ViewModelProvider(this).get(AddStuecklisteneintragViewModel.class);
    }

    //TODO: if vertrag is deleted, Stuecklisteneintrag should also be deleted!
    public void deleteArchivedVertrag(Vertrag vertrag) {
        List<Integer> deleteStuecklisteneintragList = vertrag.getStuecklisteIds();

        for (int i = 0; i < deleteStuecklisteneintragList.size(); i++) {
            stuecklisteneintragViewModel.delete(stuecklisteneintragViewModel.stuecklisteneintragById(deleteStuecklisteneintragList.get(i)));
        }
        vertragViewModel.delete(vertrag);
    }

    public void restoreVertrag(int id){
        modifyVertragViewModel = new ViewModelProvider(this).get(ModifyVertragViewModel.class);
        restoreVertrag = modifyVertragViewModel.loadVertragById(id);
        restoreVertrag.setArchived(false);
        modifyVertragViewModel.update(restoreVertrag);
    }

    @Override
    public void onPositionClicked(int position) {

    }
}
