package com.example.rentalApplication.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.rentalApplication.models.Baumaschine;
import com.example.rentalApplication.persistence.BaumaschinenRepository;

import java.util.List;

public class AddBaumaschinenViewModel extends AndroidViewModel {
    private BaumaschinenRepository baumaschinenRepository;
    //private final LiveData<List<Baumaschine>> allBaumaschinen;
    public AddBaumaschinenViewModel(@NonNull Application application) {
        super(application);
        baumaschinenRepository = new BaumaschinenRepository(application);
    }
    public void insert(Baumaschine baumaschine){
        baumaschinenRepository.insert(baumaschine);
    }
}
