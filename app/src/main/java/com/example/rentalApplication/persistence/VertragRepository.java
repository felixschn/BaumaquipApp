package com.example.rentalApplication.persistence;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.rentalApplication.models.Vertrag;

import java.util.List;

public class VertragRepository {
    private VertragDao vertragDao;
    private LiveData<List<Vertrag>> allVertrag;

    public VertragRepository(Application application) {
        RentDatabase db = RentDatabase.getDatabase(application);
        vertragDao = db.vertragDao();
        allVertrag = vertragDao.getAllVertrag();
    }

    public LiveData<List<Vertrag>> getAllVertrag() {return  allVertrag;}
    public void insert(Vertrag vertrag){
        new InsertAsyncTask(vertragDao).execute(vertrag);
    }
    private static class InsertAsyncTask extends AsyncTask<Vertrag, Void, Void>{
        private VertragDao mAsyncTaskDao;
        InsertAsyncTask(VertragDao dao){ this.mAsyncTaskDao = dao;}

        @Override
        protected Void doInBackground(Vertrag... vertrags){
            mAsyncTaskDao.insert(vertrags[0]);
            return null;
        }
    }
}

