package com.example.rentalApplication.ui.Kunde;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.rentalApplication.models.Kunde;
import com.example.rentalApplication.persistence.KundenRepository;

//ViewModel to get the opportunity to change Kunden in the Database

public class ModifyKundenViewModel extends AndroidViewModel {
    private KundenRepository kundenRepository;
    private Kunde kundeById;
    private int rowid;
    private static final String TAG = "ModifyKundenViewModel";

    public ModifyKundenViewModel(@NonNull Application application, int rowid) {
        super(application);
        kundenRepository = new KundenRepository(application);
        this.rowid = rowid;
        kundeById = kundenRepository.loadKundeById(rowid);
    }

    public Kunde loadKundeById (int id){return kundeById;}

    public void update(Kunde kunde){
        //setting RowID that correct database entry is updatet; because of creation of a new Kunden Object in update Method the RowID from the called Kunden Object is needed
        kunde.setRowid(rowid);
        kundenRepository.update(kunde);
    }
}
