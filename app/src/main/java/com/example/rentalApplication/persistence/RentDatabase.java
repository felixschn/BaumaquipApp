package com.example.rentalApplication.persistence;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.rentalApplication.models.Baumaschine;
import com.example.rentalApplication.models.Converters;
import com.example.rentalApplication.models.Kunde;
import com.example.rentalApplication.models.Vertrag;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Baumaschine.class, Kunde.class, Vertrag.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class RentDatabase extends RoomDatabase {

    public abstract KundenDao kundenDao();
    public abstract BaumaschinenDao baumaschinenDao();
    public abstract VertragDao vertragDao();

    private static final int NUMBER_OF_THREADS = 4;

    private static volatile RentDatabase INSTANCE;

    static final ExecutorService databaseWriteWxecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static RentDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            //synchronized: ensure that a crucial section of the code is never executed concurrently by two different threads
            synchronized (RentDatabase.class) {
                if (INSTANCE == null) {
                    // TODO fallBackOnDestructiveMigration löscht bei Update die Datenbank Inhalte -> Migrationen verwenden
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), RentDatabase.class, "rent_database").fallbackToDestructiveMigration().addCallback(roomCallback).build();
                }
            }
        }
        return INSTANCE;
    }

/*    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteWxecutor.execute(() -> {
                KundenDao kDao = INSTANCE.kundenDao();
                BaumaschinenDao bDao = INSTANCE.baumaschinenDao();
            });
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }

        @Override
        public void onDestructiveMigration(@NonNull SupportSQLiteDatabase db) {
            super.onDestructiveMigration(db);
        }
    };*/

    public static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(INSTANCE).execute();
        }
    };

    public static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{
        private final KundenDao kundenDao;
        private final BaumaschinenDao baumaschinenDao;
        private final VertragDao vertragDao;
        PopulateDbAsyncTask(RentDatabase db) {
            kundenDao = db.kundenDao();
            baumaschinenDao = db.baumaschinenDao();
            vertragDao = db.vertragDao();}
        @Override
        protected Void doInBackground(Void... voids) {
            //baumaschinenDao.insert(new Baumaschine("Testmaschine", 1,10.00,20.00,30.00,null,null, null));
            return null;
        }
    }
}
