package com.example.rentalApplication.persistence;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.rentalApplication.models.Baumaschine;

import java.util.List;

public class BaumaschinenRepository {
    private BaumaschinenDao baumaschinenDao;
    private LiveData<List<Baumaschine>> allBaumaschinen;
    private String DB_NAME = "rent_db";


    public BaumaschinenRepository(Application application){
        RentDatabase db = RentDatabase.getDatabase(application);
        baumaschinenDao = db.baumaschinenDao();
        allBaumaschinen = baumaschinenDao.getAllBaumaschinen();
    }

    public LiveData<List<Baumaschine>> getAllBaumaschinen() {return allBaumaschinen;}

    public void insert (Baumaschine baumaschine){
        //RentDatabase.databaseWriteWxecutor.execute(() -> {baumaschinenDao.insert(baumaschine);});
        new InsertAsyncTask(baumaschinenDao).execute(baumaschine);
    }
    private static class InsertAsyncTask extends AsyncTask<Baumaschine, Void, Void>{
        private BaumaschinenDao mAsyncTaskDao;
        InsertAsyncTask(BaumaschinenDao dao){
            this.mAsyncTaskDao = dao;
        }
        @Override
        protected  Void doInBackground(Baumaschine... baumaschines){
            mAsyncTaskDao.insert(baumaschines[0]);
            return null;
        }
    }
}
