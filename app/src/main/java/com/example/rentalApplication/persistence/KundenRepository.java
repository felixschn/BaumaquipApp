package com.example.rentalApplication.persistence;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.rentalApplication.models.Baumaschine;
import com.example.rentalApplication.models.Kunde;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class KundenRepository {
    private KundenDao kundenDao;
    private LiveData<List<Kunde>> allKunden;
    private LiveData<List<Kunde>> allArchivedKunden;

    public KundenRepository(Application application) {
        RentDatabase db = RentDatabase.getDatabase(application);
        kundenDao = db.kundenDao();
        allKunden = kundenDao.getAllKunden();
        allArchivedKunden = kundenDao.getAllArchivedKunden();
    }
    public LiveData<List<Kunde>> getAllKunden() {return allKunden;}
    public LiveData<List<Kunde>> getAllArchivedKunden() {return allArchivedKunden;}

    public void insert(Kunde kunde){
        new InsertAsyncTask(kundenDao).execute(kunde);
    }

    public void update(Kunde kunde){
        new UpdateAsyncTask(kundenDao).execute(kunde);
    }

    public void delete(Kunde kunde) {new DeleteAsyncTask(kundenDao).execute(kunde);}

    public Kunde loadKundeById(int id){
        Integer rowid = id;
        try {
            return new ModifyAsyncTask(kundenDao).execute(rowid).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
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

    private static class UpdateAsyncTask extends AsyncTask<Kunde, Void, Void>{
        private KundenDao mAsyncTaskDao;
        public UpdateAsyncTask(KundenDao kundenDao) {
            this.mAsyncTaskDao = kundenDao;
        }

        @Override
        protected Void doInBackground(Kunde... kundes) {
            mAsyncTaskDao.update(kundes[0]);
            return null;
        }
    }

    private static class ModifyAsyncTask extends AsyncTask<Integer, Void, Kunde>{
        private KundenDao mAsyncTaskDao;

        public ModifyAsyncTask(KundenDao mAsyncTaskDao) {
            this.mAsyncTaskDao = mAsyncTaskDao;
        }

        @Override
        protected Kunde doInBackground(Integer... integers) {
            return mAsyncTaskDao.loadKundeById(integers[0]);
        }

    }

    private class DeleteAsyncTask extends AsyncTask<Kunde, Void, Void> {
        private KundenDao mAsyncTaskDao;
        public DeleteAsyncTask(KundenDao mAsyncTaskDao) {
            this.mAsyncTaskDao = mAsyncTaskDao;
        }

        @Override
        protected Void doInBackground(Kunde... kundes) {
            mAsyncTaskDao.delete(kundes[0]);
            return null;
        }
    }
}
