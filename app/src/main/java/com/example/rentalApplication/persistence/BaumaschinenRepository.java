package com.example.rentalApplication.persistence;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.rentalApplication.models.Baumaschine;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class BaumaschinenRepository {
    private static BaumaschinenRepository INSTANCE = null;

    private final BaumaschinenDao baumaschinenDao;
    private final LiveData<List<Baumaschine>> allBaumaschinen;
    private final LiveData<List<Baumaschine>> allBaumaschinenForSpinner;
    private final LiveData<List<Baumaschine>> allArchivedBaumaschinen;
    private Baumaschine anyBaumaschine;
    private final String DB_NAME = "rent_db";

    private BaumaschinenRepository(Application application) {
        RentDatabase db = RentDatabase.getDatabase(application);
        baumaschinenDao = db.baumaschinenDao();
        allBaumaschinen = baumaschinenDao.getAllBaumaschinen();
        allBaumaschinenForSpinner = baumaschinenDao.getAllBaumaschinenForSpinner();
        allArchivedBaumaschinen = baumaschinenDao.getAllArchivedBaumaschinen();
    }

    public static synchronized BaumaschinenRepository getInstance(Application application) {
        if (null == INSTANCE) {
            INSTANCE = new BaumaschinenRepository(application);
        }
        return INSTANCE;
    }


    //retrieving all non archived Baumaschinen
    public LiveData<List<Baumaschine>> getAllBaumaschinen() {
        return allBaumaschinen;
    }

    public LiveData<List<Baumaschine>> getAllBaumaschinenForSpinner(){return allBaumaschinenForSpinner;}

    //retrieving all archived Baumaschinen
    public LiveData<List<Baumaschine>> getAllArchivedBaumaschinen() {
        return allArchivedBaumaschinen;
    }

    //public void getBaumaschinen(){return allBaumaschinen; }

    public void insert(Baumaschine baumaschine) {
        //RentDatabase.databaseWriteWxecutor.execute(() -> {baumaschinenDao.insert(baumaschine);});
        new InsertAsyncTask(baumaschinenDao).execute(baumaschine);
    }

    public void update(Baumaschine baumaschine) {
        new UpdateAsyncTask(baumaschinenDao).execute(baumaschine);

    }

    public void delete(Baumaschine baumaschine) {
        new DeleteAsyncTask(baumaschinenDao).execute(baumaschine);
    }

    //loading Baumaschine per id to enable modifying Baumaschine
    public Baumaschine getBaumaschineById(int id) {
        Integer rowid = id;
        try {
            return new ModifyAsyncTask(baumaschinenDao).execute(rowid).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Baumaschine getAnyBaumaschine() {
        return anyBaumaschine;
    }

    private static class InsertAsyncTask extends AsyncTask<Baumaschine, Void, Void> {
        private final BaumaschinenDao mAsyncTaskDao;

        InsertAsyncTask(BaumaschinenDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Baumaschine... baumaschines) {
            mAsyncTaskDao.insert(baumaschines[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Baumaschine, Void, Void> {
        private final BaumaschinenDao mAsyncTaskDao;

        UpdateAsyncTask(BaumaschinenDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Baumaschine... baumaschines) {
            mAsyncTaskDao.update(baumaschines[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Baumaschine, Void, Void> {
        private final BaumaschinenDao mAsyncTaskDao;

        DeleteAsyncTask(BaumaschinenDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Baumaschine... baumaschines) {
            mAsyncTaskDao.delete(baumaschines[0]);
            return null;
        }
    }


    private static class ModifyAsyncTask extends AsyncTask<Integer, Void, Baumaschine> {
        private final BaumaschinenDao mAsyncTaskDao;

        ModifyAsyncTask(BaumaschinenDao dao) {
            this.mAsyncTaskDao = dao;

        }

        @Override
        protected Baumaschine doInBackground(Integer... integers) {
            return mAsyncTaskDao.getBaumaschineById(integers[0]);
        }

        @Override
        protected void onPostExecute(Baumaschine baumaschine) {
            super.onPostExecute(baumaschine);

        }
    }

    /*private static class FetchAsyncTask extends AsyncTask<Baumaschine, Void, Void>{
        private BaumaschinenDao mAsyncTaskDao;
        FetchAsyncTask(BaumaschinenDao dao){this.mAsyncTaskDao = dao;}
        @Override
        protected Void doInBackground (){
            mAsyncTaskDao.getBaumaschinen();
            return null;
        }
    }*/

}
