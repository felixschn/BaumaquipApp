package com.example.rentalApplication.persistence;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.rentalApplication.models.Kunde;

import java.util.List;

public class KundenRepository {
    private KundenDao kundenDao;
    private LiveData<List<Kunde>> allKunden;

    public KundenRepository(Application application) {
        RentDatabase db = RentDatabase.getDatabase(application);
        kundenDao = db.kundenDao();
        allKunden = kundenDao.getAllKunden();
    }
    public LiveData<List<Kunde>> getAllKunden() {return allKunden;}

    public void insert(Kunde kunde){
        RentDatabase.databaseWriteWxecutor.execute(() -> {kundenDao.insert(kunde);});
    }
}
