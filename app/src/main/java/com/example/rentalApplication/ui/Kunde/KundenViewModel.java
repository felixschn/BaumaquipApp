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

    public KundenViewModel(@NonNull Application application){
        super(application);
        kundenRepository = new KundenRepository(application);
        allKunden = kundenRepository.getAllKunden();
    }
    public LiveData<List<Kunde>> getAllKunden() {return allKunden;}

    public void insert(Kunde kunde){kundenRepository.insert(kunde);}
}
