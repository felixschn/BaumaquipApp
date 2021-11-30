package com.example.rentalApplication.ui.Vertraege;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.rentalApplication.models.Vertrag;
import com.example.rentalApplication.persistence.VertragRepository;

import java.util.concurrent.ExecutionException;

public class ModifyVertragViewModel extends AndroidViewModel {
    private VertragRepository vertragRepository;
    private static final String TAG = "ModifyVertragViewModel";

    public ModifyVertragViewModel(@NonNull Application application){
        super(application);
        vertragRepository = VertragRepository.getInstance(application);
    }

    public Vertrag loadVertragById (int id){return vertragRepository.loadVertragById(id);}

    public void update(Vertrag vertrag){
        vertragRepository.update(vertrag);
    }
}
