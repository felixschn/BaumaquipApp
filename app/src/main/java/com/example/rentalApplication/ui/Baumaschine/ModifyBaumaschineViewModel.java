package com.example.rentalApplication.ui.Baumaschine;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.rentalApplication.models.Baumaschine;
import com.example.rentalApplication.persistence.BaumaschinenRepository;

public class ModifyBaumaschineViewModel extends AndroidViewModel {
    private BaumaschinenRepository baumaschinenRepository;
    private static final String TAG = "ModifyBaumaschineViewModel";

    public ModifyBaumaschineViewModel(@NonNull Application application) {
        super(application);
        baumaschinenRepository = new BaumaschinenRepository(application);
    }

    public Baumaschine loadBaumaschineById (int id){
        return baumaschinenRepository.loadBaumaschineById(id);
    }

    public void update(Baumaschine baumaschine){
        Log.d(TAG, "ROW_ID in: " + TAG + baumaschine.getRowid());
        baumaschinenRepository.update(baumaschine);}

    public Baumaschine getAnyBaumaschine(){return baumaschinenRepository.getAnyBaumaschine();}
}
