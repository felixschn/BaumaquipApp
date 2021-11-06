package com.example.rentalApplication.ui.Kunde;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.rentalApplication.models.Kunde;
import com.example.rentalApplication.persistence.KundenRepository;

public class AddKundenViewModel extends AndroidViewModel {
    private KundenRepository kundenRepository;

    public AddKundenViewModel(@NonNull Application application) {
        super(application);
        kundenRepository = KundenRepository.getInstance(application);
    }

    public void insert(Kunde kunde) {
        kundenRepository.insert(kunde);
    }
}
