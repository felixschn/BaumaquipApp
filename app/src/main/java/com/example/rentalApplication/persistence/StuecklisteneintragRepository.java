package com.example.rentalApplication.persistence;


import android.app.Application;
import android.os.AsyncTask;

import com.example.rentalApplication.models.Stuecklisteneintrag;
import com.example.rentalApplication.ui.Vertraege.Stuecklisteneintrag.AsyncTaskStuecklisteneintragIdResponse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class StuecklisteneintragRepository {
    private static StuecklisteneintragRepository INSTANCE = null;
    private final StuecklisteneintragDao stuecklisteneintragDao;
    private AsyncTaskStuecklisteneintragIdResponse asyncTaskStuecklisteneintragIdResponse;

    private StuecklisteneintragRepository(Application application) {
        RentDatabase db = RentDatabase.getDatabase(application);
        stuecklisteneintragDao = db.stuecklisteneintragDao();

    }

    public static synchronized StuecklisteneintragRepository getInstance(Application application) {
        if (null == INSTANCE) {
            INSTANCE = new StuecklisteneintragRepository(application);
        }
        return INSTANCE;
    }

    public long insert(Stuecklisteneintrag stuecklisteneintrag) throws ExecutionException, InterruptedException {
        return new InsertAsyncTask(stuecklisteneintragDao).execute(stuecklisteneintrag).get();
    }

    public void update(Stuecklisteneintrag stuecklisteneintrag) {
        new UpdateAsyncTask(stuecklisteneintragDao).execute(stuecklisteneintrag);
    }

    public void delete(Stuecklisteneintrag stuecklisteneintrag) {
        new DeleteAsyncTask(stuecklisteneintragDao).execute(stuecklisteneintrag);
    }

    public List<Stuecklisteneintrag> getAllStuecklisteneintragForId(int id) {
        Integer rowid = id;
        try {
            return new ModifyAsyncTask(stuecklisteneintragDao).execute(rowid).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Stuecklisteneintrag getStuecklisteneintragById(int id) {
        Integer rowid = id;
        try {
            return new StuecklisteneintragByIdAsyncTask(stuecklisteneintragDao).execute(rowid).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer getAmountOfCurrentlyRentedMachine(int id) {
        Integer baumaschineId = id;
        List<Integer> currentList = new ArrayList<>();
        try {
            //asynctask returns a list of Integers; sum up all values of the list
            return new AmountofCurrentlyRentedAsyncTask(stuecklisteneintragDao).execute(baumaschineId).get().stream().mapToInt(Integer::intValue).sum();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Stuecklisteneintrag> getStuecklisteneintragForDate(LocalDate start, LocalDate end, int id) {
        try {
            return new StuecklisteneintragForDateAsyncTask(stuecklisteneintragDao, start, end, id).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Stuecklisteneintrag> getAllStuecklisteneintag(Boolean isArchived) {
        try {
            if (!isArchived) {
                return new AllStuecklisteneintragAsyncTask(stuecklisteneintragDao, isArchived).execute().get();
            } else {
                return new AllStuecklisteneintragAsyncTask(stuecklisteneintragDao, isArchived).execute().get();
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static class InsertAsyncTask extends AsyncTask<Stuecklisteneintrag, Void, Long> {
        private final StuecklisteneintragDao mAsyncTaskDao;

        public long insertId;

        InsertAsyncTask(StuecklisteneintragDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Long doInBackground(Stuecklisteneintrag... stuecklisteneintrag) {
            insertId = mAsyncTaskDao.insert(stuecklisteneintrag[0]);
            return insertId;
        }

        /*@Override
        protected void onPostExecute(Long insertId) {
            super.onPostExecute(insertId);
        }*/
    }

    private static class StuecklisteneintragByIdAsyncTask extends AsyncTask<Integer, Void, Stuecklisteneintrag> {
        private final StuecklisteneintragDao mAsyncTaskDao;

        StuecklisteneintragByIdAsyncTask(StuecklisteneintragDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Stuecklisteneintrag doInBackground(Integer... integers) {
            return mAsyncTaskDao.getStuecklisteneintragById(integers[0]);
        }
    }

    private static class AmountofCurrentlyRentedAsyncTask extends AsyncTask<Integer, Void, List<Integer>> {
        private final StuecklisteneintragDao mAsyncTaskDao;
        private final LocalDate today = LocalDate.now();

        public AmountofCurrentlyRentedAsyncTask(StuecklisteneintragDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected List<Integer> doInBackground(Integer... integers) {
            return mAsyncTaskDao.getAmountOfCurrentlyRentedMachines(integers[0], today);
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Stuecklisteneintrag, Void, Void> {
        private final StuecklisteneintragDao mAsyncTaskDao;

        public UpdateAsyncTask(StuecklisteneintragDao stuecklisteneintragDao) {
            this.mAsyncTaskDao = stuecklisteneintragDao;
        }

        @Override
        protected Void doInBackground(Stuecklisteneintrag... stuecklisteneintrags) {
            mAsyncTaskDao.update(stuecklisteneintrags[0]);
            return null;
        }
    }

    private static class ModifyAsyncTask extends AsyncTask<Integer, Void, List<Stuecklisteneintrag>> {
        private final StuecklisteneintragDao mAsyncTaskDao;

        ModifyAsyncTask(StuecklisteneintragDao stuecklisteneintragDao) {
            this.mAsyncTaskDao = stuecklisteneintragDao;
        }

        @Override
        protected List<Stuecklisteneintrag> doInBackground(Integer... integers) {
            return mAsyncTaskDao.getAllStuecklisteneintragForId(integers[0]);
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Stuecklisteneintrag, Void, Void> {
        private final StuecklisteneintragDao mAsyncTaskDao;

        DeleteAsyncTask(StuecklisteneintragDao stuecklisteneintragDao) {
            this.mAsyncTaskDao = stuecklisteneintragDao;
        }

        @Override
        protected Void doInBackground(Stuecklisteneintrag... stuecklisteneintrags) {
            mAsyncTaskDao.delete(stuecklisteneintrags[0]);
            return null;
        }
    }

    private static class StuecklisteneintragForDateAsyncTask extends AsyncTask<Void, Void, List<Stuecklisteneintrag>> {
        private final StuecklisteneintragDao mAsyncTaskDao;
        private final LocalDate start;
        private final LocalDate end;
        private final int id;

        public StuecklisteneintragForDateAsyncTask(StuecklisteneintragDao mAsyncDao, LocalDate start, LocalDate end, int id) {
            this.mAsyncTaskDao = mAsyncDao;
            this.start = start;
            this.end = end;
            this.id = id;
        }

        @Override
        protected List<Stuecklisteneintrag> doInBackground(Void... voids) {
            return mAsyncTaskDao.getStuecklisteneintragForDate(start, end, id);
        }
    }

    private static class AllStuecklisteneintragAsyncTask extends AsyncTask<Void, Void, List<Stuecklisteneintrag>> {
        private final StuecklisteneintragDao mAsyncTaskDao;
        private final Boolean isArchived;

        public AllStuecklisteneintragAsyncTask(StuecklisteneintragDao mAsyncTaskDao, Boolean isArchived) {
            this.mAsyncTaskDao = mAsyncTaskDao;
            this.isArchived = isArchived;
        }

        @Override
        protected List<Stuecklisteneintrag> doInBackground(Void... voids) {
            if (!isArchived) {
                return mAsyncTaskDao.getAllStuecklisteneintrag();
            } else {
                return mAsyncTaskDao.getAllArchivedStuecklisteneintrag();
            }
        }
    }


}
