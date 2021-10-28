package com.example.rentalApplication.persistence;


import android.app.Application;
import android.os.AsyncTask;

import com.example.rentalApplication.models.Stuecklisteneintrag;

public class StuecklisteneintragRepository {
    private static StuecklisteneintragRepository INSTANCE = null;

    private StuecklisteneintragDao stuecklisteneintragDao;

    private StuecklisteneintragRepository(Application application){
        RentDatabase db = RentDatabase.getDatabase(application);
        stuecklisteneintragDao = db.stuecklisteneintragDao();

    }

    public static synchronized StuecklisteneintragRepository getInstance(Application application){
        if(null == INSTANCE){
            INSTANCE = new StuecklisteneintragRepository(application);
        }
        return INSTANCE;
    }

    public void insert(Stuecklisteneintrag stuecklisteneintrag){
        new InsertAsyncTask(stuecklisteneintragDao).execute(stuecklisteneintrag);
    }


    private static class InsertAsyncTask extends AsyncTask<Stuecklisteneintrag, Void, Void>{
        private StuecklisteneintragDao mAsyncTaskDao;

        InsertAsyncTask(StuecklisteneintragDao dao){this.mAsyncTaskDao = dao;}

        @Override
        protected Void doInBackground(Stuecklisteneintrag... stuecklisteneintrag){
            mAsyncTaskDao.insert(stuecklisteneintrag[0]);
            return null;
        }
    }


}
