package com.example.rentalApplication.ui.Kunde;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalApplication.R;
import com.example.rentalApplication.adapter.ArchivedKundenListAdapter;
import com.example.rentalApplication.models.Kunde;
import com.example.rentalApplication.models.Vertrag;
import com.example.rentalApplication.ui.Vertraege.VertragViewModel;

import java.util.List;

public class ArchivedKundenActivity extends AppCompatActivity implements KundenClickListener {
    private RecyclerView recyclerView;
    private KundenViewModel kundenViewModel;
    private ModifyKundenViewModel modifyKundenViewModel;
    private VertragViewModel vertragViewModel;
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

    public void deleteKunde(Kunde kunde) {
        vertragViewModel = new ViewModelProvider(this).get(VertragViewModel.class);
        List<Vertrag> getAllExistingVertrag = vertragViewModel.getAllExistingVertrag();
        for (int i = 0; i < getAllExistingVertrag.size(); i++) {
            if (kunde.getIdKunde() == getAllExistingVertrag.get(i).getIdKunde()) {
                Toast.makeText(this, R.string.not_removable, Toast.LENGTH_LONG).show();
                return;
            }
            kundenViewModel.delete(kunde);
        }
    }

    public void restoreKunde(int id) {
        modifyKundenViewModel = new ViewModelProvider(this).get(ModifyKundenViewModel.class);
        restoreKunde = modifyKundenViewModel.loadKundeById(id);
        restoreKunde.setArchived(false);
        modifyKundenViewModel.update(restoreKunde);
        finish();

    }

    @Override
    public void onPositionClicked(int position) {

    }
}
