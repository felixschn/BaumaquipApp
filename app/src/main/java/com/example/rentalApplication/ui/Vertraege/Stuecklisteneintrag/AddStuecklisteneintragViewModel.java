package com.example.rentalApplication.ui.Vertraege.Stuecklisteneintrag;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.rentalApplication.models.Stuecklisteneintrag;
import com.example.rentalApplication.persistence.StuecklisteneintragRepository;

import java.time.LocalDate;
import java.util.List;
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

    public List<Stuecklisteneintrag> getAllStuecklisteneintragForId(int id) {
        return stuecklisteneintragRepository.getAllStuecklisteneintragForId(id);
    }

    public Stuecklisteneintrag stuecklisteneintragById(int id) {
        return stuecklisteneintragRepository.getStuecklisteneintragById(id);
    }

    public void delete(Stuecklisteneintrag stuecklisteneintrag) {
        stuecklisteneintragRepository.delete(stuecklisteneintrag);
    }

    public void update(Stuecklisteneintrag stuecklisteneintrag){
        stuecklisteneintragRepository.update(stuecklisteneintrag);
    }

    public List<Stuecklisteneintrag> getStuecklisteneintragForDate(LocalDate start, LocalDate end, int id) {
        return stuecklisteneintragRepository.getStuecklisteneintragForDate(start, end, id);
    }

}