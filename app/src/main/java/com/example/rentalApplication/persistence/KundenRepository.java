package com.example.rentalApplication.persistence;

import android.app.Application;
import android.os.AsyncTask;

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
        new InsertAsyncTask(kundenDao).execute(kunde);

    }
    private static class InsertAsyncTask extends AsyncTask<Kunde, Void, Void>{
        private KundenDao mAsyncTaskDao;
        InsertAsyncTask(KundenDao dao){
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Kunde... kundes) {
            mAsyncTaskDao.insert(kundes[0]);
            return null;
        }
    }
}
