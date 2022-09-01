package com.example.rentalApplication.persistence;

import android.app.Application;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.example.rentalApplication.models.Kunde;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class KundenRepository {
    private static KundenRepository INSTANCE = null;
    private final KundenDao kundenDao;
    private final LiveData<List<Kunde>> allKunden;
    private final LiveData<List<Kunde>> allArchivedKunden;

    private KundenRepository(Application application) {
        RentDatabase db = RentDatabase.getDatabase(application);
        kundenDao = db.kundenDao();
        allKunden = kundenDao.getAllKunden();
        allArchivedKunden = kundenDao.getAllArchivedKunden();
    }

    public static synchronized KundenRepository getInstance(Application application) {
        if (null == INSTANCE) {
            INSTANCE = new KundenRepository(application);
        }
        return INSTANCE;
    }

    public LiveData<List<Kunde>> getAllKunden() {
        return allKunden;
    }

    public LiveData<List<Kunde>> getAllArchivedKunden() {
        return allArchivedKunden;
    }

    public void insert(Kunde kunde) {
        new InsertAsyncTask(kundenDao).execute(kunde);
    }

    public void update(Kunde kunde) {
        new UpdateAsyncTask(kundenDao).execute(kunde);
    }

    public void delete(Kunde kunde) {
        new DeleteAsyncTask(kundenDao).execute(kunde);
    }

    public Kunde loadKundeById(int id) {
        Integer rowid = id;
        try {
            return new ModifyAsyncTask(kundenDao).execute(rowid).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static class InsertAsyncTask extends AsyncTask<Kunde, Void, Void> {
        private final KundenDao mAsyncTaskDao;

        InsertAsyncTask(KundenDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Kunde... kundes) {
            try{
                mAsyncTaskDao.insert(kundes[0]);
            }
            catch (SQLiteConstraintException exception){

            }
            return null;
        }
    }

        private static class UpdateAsyncTask extends AsyncTask<Kunde, Void, Void> {
            private final KundenDao mAsyncTaskDao;

            public UpdateAsyncTask(KundenDao kundenDao) {
                this.mAsyncTaskDao = kundenDao;
            }

            @Override
            protected Void doInBackground(Kunde... kundes) {
                mAsyncTaskDao.update(kundes[0]);
                return null;
            }
        }

        private static class ModifyAsyncTask extends AsyncTask<Integer, Void, Kunde> {
            private final KundenDao mAsyncTaskDao;

            public ModifyAsyncTask(KundenDao mAsyncTaskDao) {
                this.mAsyncTaskDao = mAsyncTaskDao;
            }

            @Override
            protected Kunde doInBackground(Integer... integers) {
                return mAsyncTaskDao.getKundeById(integers[0]);
            }

        }

        private class DeleteAsyncTask extends AsyncTask<Kunde, Void, Void> {
            private final KundenDao mAsyncTaskDao;

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
