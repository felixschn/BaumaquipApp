package com.example.rentalApplication.ui.Baumaschine;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.rentalApplication.models.Baumaschine;
import com.example.rentalApplication.persistence.BaumaschinenRepository;

public class UpdateBaumaschinenViewModel extends AndroidViewModel {
    private BaumaschinenRepository baumaschinenRepository;

    public UpdateBaumaschinenViewModel(@NonNull Application application) {
        super(application);
        baumaschinenRepository = new BaumaschinenRepository(application);
    }
    public void update(Baumaschine baumaschine){baumaschinenRepository.update(baumaschine);}
}
