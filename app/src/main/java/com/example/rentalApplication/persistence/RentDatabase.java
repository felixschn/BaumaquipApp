package com.example.rentalApplication.persistence;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.rentalApplication.models.Baumaschine;
import com.example.rentalApplication.models.Kunde;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Baumaschine.class, Kunde.class}, version = 1, exportSchema = false)
public abstract class RentDatabase extends RoomDatabase {

    public abstract KundenDao kundenDao();
    public abstract BaumaschinenDao baumaschinenDao();

    private static final int NUMBER_OF_THREADS = 4;

    private static volatile RentDatabase INSTANCE;

    static final ExecutorService databaseWriteWxecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static RentDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            //synchronized: ensure that a crucial section of the code is never executed concurrently by two different threads
            synchronized (RentDatabase.class) {
                if (INSTANCE == null) {
                    // TODO fallBackOnDestructiveMigration löscht bei Update die Datenbank Inhalte -> Migrationen verwenden
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), RentDatabase.class, "rent_database").fallbackToDestructiveMigration().addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
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
    };

    public static class PopulateDbAsync extends AsyncTask<Void,Void,Void>{
        private final KundenDao kundenDao;
        private final BaumaschinenDao baumaschinenDao;
        PopulateDbAsync(RentDatabase db) {
            kundenDao = db.kundenDao();
            baumaschinenDao = db.baumaschinenDao();}

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
