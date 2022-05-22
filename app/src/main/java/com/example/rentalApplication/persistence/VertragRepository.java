package com.example.rentalApplication.persistence;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.rentalApplication.models.Vertrag;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class VertragRepository {
    private static VertragRepository INSTANCE = null;
    private VertragDao vertragDao;
    private LiveData<List<Vertrag>> allVertrag;
    private LiveData<List<Vertrag>> allArchivedVertrag;

    private VertragRepository(Application application) {
        RentDatabase db = RentDatabase.getDatabase(application);
        vertragDao = db.vertragDao();
        allVertrag = vertragDao.getAllVertrag();
        allArchivedVertrag = vertragDao.getAllArchivedVertrag();
    }

    public static synchronized VertragRepository getInstance(Application application) {
        if (null == INSTANCE) {
            INSTANCE = new VertragRepository(application);
        }
        return INSTANCE;
    }

    public Vertrag loadVertragById(int id) {
        Integer rowid = id;
        try {
            return new ModifyAsyncTask(vertragDao).execute(rowid).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LiveData<List<Vertrag>> getAllVertrag() {
        return allVertrag;
    }

    public LiveData<List<Vertrag>> getAllArchivedVertrag() {
        return allArchivedVertrag;
    }

    public void insert(Vertrag vertrag) {
        new InsertAsyncTask(vertragDao).execute(vertrag);
    }

    public void delete(Vertrag vertrag) {
        new DeleteAsyncTask(vertragDao).execute(vertrag);
    }

    public void update(Vertrag vertrag) {
        new UpdateAsyncTask(vertragDao).execute(vertrag);
    }

    public List<Vertrag> getAllExistingVertrag() {
        try {
            return new AllExistingVertragAsyncTask(vertragDao).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static class InsertAsyncTask extends AsyncTask<Vertrag, Void, Void> {
        private VertragDao mAsyncTaskDao;

        InsertAsyncTask(VertragDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Vertrag... vertrags) {
            mAsyncTaskDao.insert(vertrags[0]);
            return null;
        }
    }

    private static class ModifyAsyncTask extends AsyncTask<Integer, Void, Vertrag> {
        private VertragDao mAsyncTaskDao;

        ModifyAsyncTask(VertragDao mAsyncTaskDao) {
            this.mAsyncTaskDao = mAsyncTaskDao;
        }

        @Override
        protected Vertrag doInBackground(Integer... integers) {
            return mAsyncTaskDao.getVertagById(integers[0]);

        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Vertrag, Void, Void> {
        private VertragDao mAsyncTaskDao;

        DeleteAsyncTask(VertragDao vertragDao) {
            this.mAsyncTaskDao = vertragDao;
        }

        @Override
        protected Void doInBackground(Vertrag... vertrags) {
            mAsyncTaskDao.delete(vertrags[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Vertrag, Void, Void> {
        private VertragDao mAsynTaskDao;

        UpdateAsyncTask(VertragDao vertragDao) {
            this.mAsynTaskDao = vertragDao;
        }

        @Override
        protected Void doInBackground(Vertrag... vertrags) {
            mAsynTaskDao.update(vertrags[0]);
            return null;
        }
    }

    private static class AllExistingVertragAsyncTask extends AsyncTask<Void, Void, List<Vertrag>> {
        private VertragDao mAsyncTaskDoa;

        public AllExistingVertragAsyncTask(VertragDao mAsyncTaskDoa) {
            this.mAsyncTaskDoa = mAsyncTaskDoa;
        }

        @Override
        protected List<Vertrag> doInBackground(Void... voids) {
            return mAsyncTaskDoa.getAllExistingVertrag();
        }
    }
}

