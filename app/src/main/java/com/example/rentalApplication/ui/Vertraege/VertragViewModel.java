package com.example.rentalApplication.ui.Vertraege;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.rentalApplication.models.Vertrag;
import com.example.rentalApplication.persistence.VertragRepository;

import java.util.List;

public class VertragViewModel extends AndroidViewModel {
    private final VertragRepository vertragRepository;
    private final LiveData<List<Vertrag>> allVertrag;
    private final LiveData<List<Vertrag>> allArchivedVertrag;

    public VertragViewModel(@NonNull Application application) {
        super(application);
        vertragRepository = VertragRepository.getInstance(application);
        allVertrag = vertragRepository.getAllVertrag();
        allArchivedVertrag = vertragRepository.getAllArchivedVertrag();
    }

    public LiveData<List<Vertrag>> getAllVertrag() {
        return allVertrag;
    }

    public void insert(Vertrag vertrag) {
        vertragRepository.insert(vertrag);
    }

    public LiveData<List<Vertrag>> getAllArchivedVertrag() {
        return allArchivedVertrag;
    }

    public void delete(Vertrag vertrag) {
        vertragRepository.delete(vertrag);
    }

    public List<Vertrag> getAllExistingVertrag() {
        return vertragRepository.getAllExistingVertrag();
    }
}
