package com.example.rentalApplication.ui.Baumaschine;

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
    private final LiveData<List<Baumaschine>> allArchivedBaumaschinen;


    public BaumaschinenViewModel(@NonNull Application application) {
        super(application);
        baumaschinenRepository = BaumaschinenRepository.getInstance(application);
        allBaumaschinen = baumaschinenRepository.getAllBaumaschinen();
        allArchivedBaumaschinen = baumaschinenRepository.getAllArchivedBaumaschinen();


    }

    public LiveData<List<Baumaschine>> getAllBaumaschinen() {
        return allBaumaschinen;
    }

    public LiveData<List<Baumaschine>> getAllArchivedBaumaschine() {
        return allArchivedBaumaschinen;
    }

    public void delete(Baumaschine baumaschine) {
        baumaschinenRepository.delete(baumaschine);
    }


}   
