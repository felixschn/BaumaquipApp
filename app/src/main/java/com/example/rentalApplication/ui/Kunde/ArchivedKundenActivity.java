package com.example.rentalApplication.ui.Kunde;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalApplication.R;
import com.example.rentalApplication.adapter.ArchivedKundenListAdapter;
import com.example.rentalApplication.models.Kunde;

import java.util.List;

public class ArchivedKundenActivity extends AppCompatActivity implements KundenClickListener {
    private RecyclerView recyclerView;
    private KundenViewModel kundenViewModel;
    private ModifyKundenViewModel modifyKundenViewModel;
    private Kunde restoreKunde;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.archived_kunden);
        recyclerView = findViewById(R.id.archivedKundenRecyclerView);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final ArchivedKundenListAdapter archivedKundenListAdapter = new ArchivedKundenListAdapter(this, this);
        recyclerView.setAdapter(archivedKundenListAdapter);

        kundenViewModel = new ViewModelProvider(this).get(KundenViewModel.class);
        kundenViewModel.getAllArchivedKunden().observe(this, new Observer<List<Kunde>>() {

            @Override
            public void onChanged(List<Kunde> kundes) {
                archivedKundenListAdapter.setKunden(kundes);
            }

        });
    }
    public void deleteKunde(Kunde kunde){
        kundenViewModel.delete(kunde);
    }
    public void restoreKunde(int id) {
        modifyKundenViewModel = new ViewModelProvider(this).get(ModifyKundenViewModel.class);
        restoreKunde = modifyKundenViewModel.loadKundeById(id);
        restoreKunde.setArchived(false);
        modifyKundenViewModel.update(restoreKunde);
       /* if(kundenViewModel.() == null){
            finish();
        }*/
    }

    @Override
    public void onPositionClicked(int position) {

    }
}
