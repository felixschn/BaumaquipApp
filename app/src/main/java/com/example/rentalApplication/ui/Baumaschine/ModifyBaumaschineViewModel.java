package com.example.rentalApplication.ui.Baumaschine;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.rentalApplication.models.Baumaschine;
import com.example.rentalApplication.persistence.BaumaschinenRepository;

public class ModifyBaumaschineViewModel extends AndroidViewModel {
    private BaumaschinenRepository baumaschinenRepository;
    private Baumaschine baumaschineById;
    private int rowid;
    private static final String TAG = "ModifyBaumaschineViewModel";

    public ModifyBaumaschineViewModel(@NonNull Application application, int id) {
        super(application);
        baumaschinenRepository = new BaumaschinenRepository(application);
        this.rowid = id;
        baumaschineById = baumaschinenRepository.loadBaumaschineById(rowid);
    }

    public Baumaschine loadBaumaschineById (int id){
        return baumaschineById;
    }

    public void update(Baumaschine baumaschine){
        Log.d(TAG, "ROW_ID in: " + TAG + baumaschine.getRowid());
        baumaschine.setRowid(rowid);
        baumaschinenRepository.update(baumaschine);}
}
