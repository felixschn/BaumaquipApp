package com.example.rentalApplication.persistence;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.rentalApplication.models.Baumaschine;

import java.util.List;

public class BaumaschinenRepository {
    private BaumaschinenDao baumaschinenDao;
    private LiveData<List<Baumaschine>> allBaumaschinen;

    public BaumaschinenRepository(Application application){
        RentDatabase db = RentDatabase.getDatabase(application);
        baumaschinenDao = db.baumaschinenDao();
        allBaumaschinen = baumaschinenDao.getAllBaumaschinen();
    }

    public LiveData<List<Baumaschine>> getAllBaumaschinen() {return allBaumaschinen;}

    public void insert (Baumaschine baumaschine){
        RentDatabase.databaseWriteWxecutor.execute(() -> {baumaschinenDao.insert(baumaschine);});
    }
}
