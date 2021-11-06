package com.example.rentalApplication.ui.Kunde;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.rentalApplication.models.Kunde;
import com.example.rentalApplication.persistence.KundenRepository;

import java.util.List;

public class KundenViewModel extends AndroidViewModel {
    private KundenRepository kundenRepository;
    private final LiveData<List<Kunde>> allKunden;
    private final LiveData<List<Kunde>> allArchivedKunden;

    public KundenViewModel(@NonNull Application application) {
        super(application);
        kundenRepository = KundenRepository.getInstance(application);
        allKunden = kundenRepository.getAllKunden();
        allArchivedKunden = kundenRepository.getAllArchivedKunden();

    }

    public void insert(Kunde kunde){kundenRepository.insert(kunde);}

    public LiveData<List<Kunde>> getAllKunden() {
        return allKunden;
    }

    public void delete(Kunde kunde) {
        kundenRepository.delete(kunde);
    }

    public LiveData<List<Kunde>> getAllArchivedKunden() {
        return allArchivedKunden;
    }

}
