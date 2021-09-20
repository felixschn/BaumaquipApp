package com.example.rentalApplication.ui.Baumaschine;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.rentalApplication.models.Baumaschine;
import com.example.rentalApplication.persistence.BaumaschinenRepository;

public class ModifyBaumaschineViewModel extends AndroidViewModel {
    private BaumaschinenRepository baumaschinenRepository;
    private Baumaschine baumaschineById;
    private int rowid;

    public ModifyBaumaschineViewModel(@NonNull Application application, int id) {
        super(application);
        baumaschinenRepository = new BaumaschinenRepository(application);
        this.rowid = id;
        baumaschineById = baumaschinenRepository.loadBaumaschineById(rowid);
    }

    public Baumaschine loadBaumaschineById (int id){
        return baumaschineById;
    }
}
