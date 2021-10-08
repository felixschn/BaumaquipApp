package com.example.rentalApplication.persistence;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.rentalApplication.models.Baumaschine;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class BaumaschinenRepository {
    private BaumaschinenDao baumaschinenDao;
    private LiveData<List<Baumaschine>> allBaumaschinen;
    private LiveData<List<Baumaschine>> allArchivedBaumaschinen;
    private Baumaschine anyBaumaschine;
    private String DB_NAME = "rent_db";

    public BaumaschinenRepository(Application application) {
        RentDatabase db = RentDatabase.getDatabase(application);
        baumaschinenDao = db.baumaschinenDao();
        allBaumaschinen = baumaschinenDao.getAllBaumaschinen();
        allArchivedBaumaschinen = baumaschinenDao.getAllArchivedBaumaschinen();
    }

    //retrieving all non archived Baumaschinen
    public LiveData<List<Baumaschine>> getAllBaumaschinen() {
        return allBaumaschinen;
    }

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

    public void delete(Baumaschine baumaschine){
        new DeleteAsyncTask(baumaschinenDao).execute(baumaschine);
    }

    //loading Baumaschine per id to enable modifying Baumaschine
    public Baumaschine loadBaumaschineById(int id) {
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

    public Baumaschine getAnyBaumaschine(){return anyBaumaschine;}

    private static class InsertAsyncTask extends AsyncTask<Baumaschine, Void, Void> {
        private BaumaschinenDao mAsyncTaskDao;

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
        private BaumaschinenDao mAsyncTaskDao;

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
        private BaumaschinenDao mAsyncTaskDao;

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
        private BaumaschinenDao mAsyncTaskDao;

        ModifyAsyncTask(BaumaschinenDao dao) {
            this.mAsyncTaskDao = dao;

        }

        @Override
        protected Baumaschine doInBackground(Integer... integers) {
            return mAsyncTaskDao.loadBaumaschineById(integers[0]);
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
