package com.example.rentalApplication.ui.Vertraege.Stuecklisteneintrag;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.rentalApplication.models.Stuecklisteneintrag;
import com.example.rentalApplication.persistence.StuecklisteneintragRepository;

import java.util.concurrent.ExecutionException;

public class AddStuecklisteneintragViewModel extends AndroidViewModel {
    private final StuecklisteneintragRepository stuecklisteneintragRepository;


    public AddStuecklisteneintragViewModel(@NonNull Application application) {
        super(application);
        stuecklisteneintragRepository = StuecklisteneintragRepository.getInstance(application);
    }

    /* method to insert Stuecklisteneintrag and automatically retrieve rowid value*/
    public long insert(Stuecklisteneintrag stuecklisteneintrag) throws ExecutionException, InterruptedException {
        return stuecklisteneintragRepository.insert(stuecklisteneintrag);
    }

}