package com.example.rentalApplication.persistence;


import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.example.rentalApplication.models.Stuecklisteneintrag;
import com.example.rentalApplication.ui.Vertraege.AddVertragActivity;
import com.example.rentalApplication.ui.Vertraege.Stuecklisteneintrag.AsyncTaskStuecklisteneintragIdResponse;

public class StuecklisteneintragRepository {
    private static StuecklisteneintragRepository INSTANCE = null;

    private StuecklisteneintragDao stuecklisteneintragDao;
    private AsyncTaskStuecklisteneintragIdResponse asyncTaskStuecklisteneintragIdResponse;

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

    public Long insert(Stuecklisteneintrag stuecklisteneintrag){
        asyncTaskStuecklisteneintragIdResponse = new AsyncTaskStuecklisteneintragIdResponse() {
            @Override
            public void idAfterInsert(long id) {

            }
        };
        new InsertAsyncTask(stuecklisteneintragDao, asyncTaskStuecklisteneintragIdResponse).execute(stuecklisteneintrag);
        return null;
    }


    private static class InsertAsyncTask extends AsyncTask<Stuecklisteneintrag, Void, Long>{
        private StuecklisteneintragDao mAsyncTaskDao;
        public AsyncTaskStuecklisteneintragIdResponse delegate;
        public long insertId;
        InsertAsyncTask(StuecklisteneintragDao dao, AsyncTaskStuecklisteneintragIdResponse asyncResponse){this.mAsyncTaskDao = dao; this.delegate = asyncResponse;}

        @Override
        protected Long doInBackground(Stuecklisteneintrag... stuecklisteneintrag){
            insertId = mAsyncTaskDao.insert(stuecklisteneintrag[0]);
            return insertId;
        }

        @Override
        protected void onPostExecute(Long insertId) {

            delegate.idAfterInsert(insertId);
        }
    }


}
