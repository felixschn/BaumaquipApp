package com.example.rentalApplication.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.rentalApplication.models.Baumaschine;
import com.example.rentalApplication.persistence.BaumaschinenRepository;

import java.util.List;

public class BaumaschinenViewModel extends AndroidViewModel {
    private BaumaschinenRepository baumaschinenRepository;
    private final LiveData<List<Baumaschine>> allBaumaschinen;

    public BaumaschinenViewModel(@NonNull Application application) {
        super(application);
        baumaschinenRepository = new BaumaschinenRepository(application);
        allBaumaschinen = baumaschinenRepository.getAllBaumaschinen();

    }
    public LiveData<List<Baumaschine>> getAllBaumaschinen() {return allBaumaschinen;}

    public void insert(Baumaschine baumaschine){baumaschinenRepository.insert(baumaschine);}

}
