package com.example.rentalApplication.ui.Vertraege.Stuecklisteneintrag;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.rentalApplication.models.Stuecklisteneintrag;
import com.example.rentalApplication.persistence.StuecklisteneintragRepository;

public class AddStuecklisteneintragViewModel extends AndroidViewModel {
    private final StuecklisteneintragRepository stuecklisteneintragRepository;

    public AddStuecklisteneintragViewModel(@NonNull Application application){
        super(application);
        stuecklisteneintragRepository = StuecklisteneintragRepository.getInstance(application);
    }

    public void insert(Stuecklisteneintrag stuecklisteneintrag){stuecklisteneintragRepository.insert(stuecklisteneintrag);}
}
