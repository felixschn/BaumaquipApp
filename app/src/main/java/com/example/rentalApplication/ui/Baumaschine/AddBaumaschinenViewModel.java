package com.example.rentalApplication.ui.Baumaschine;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.rentalApplication.models.Baumaschine;
import com.example.rentalApplication.persistence.BaumaschinenRepository;

public class AddBaumaschinenViewModel extends AndroidViewModel {
    private final BaumaschinenRepository baumaschinenRepository;

    public AddBaumaschinenViewModel(@NonNull Application application) {
        super(application);
        baumaschinenRepository = BaumaschinenRepository.getInstance(application);
    }
    public void insert(Baumaschine baumaschine){
        baumaschinenRepository.insert(baumaschine);
    }
}
