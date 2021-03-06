package com.example.rentalApplication.ui.Vertraege;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.rentalApplication.models.Vertrag;
import com.example.rentalApplication.persistence.VertragRepository;

import java.util.List;

public class VertragViewModel extends AndroidViewModel {
    private VertragRepository vertragRepository;
    private final LiveData<List<Vertrag>> allVertrag;
    public VertragViewModel(@NonNull  Application application) {
        super(application);
        vertragRepository = new VertragRepository(application);
        allVertrag = vertragRepository.getAllVertrag();
    }
    public LiveData<List<Vertrag>> getAllVertrag() {return allVertrag;}

    public void insert (Vertrag vertrag){vertragRepository.insert(vertrag);}
}
