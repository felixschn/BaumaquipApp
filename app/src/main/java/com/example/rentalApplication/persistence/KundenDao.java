package com.example.rentalApplication.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.rentalApplication.models.Kunde;

import java.util.List;

@Dao
public interface KundenDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    long insert(Kunde kunde);

    @Insert
    void insertAll(Kunde... kundes);

    @Delete
    void delete(Kunde kunde);

    @Update
    void update(Kunde kunde);

    @Query("Select *, rowid from Kunde Where rowid = :id")
    Kunde getKundeById(int id);


    @Query("SELECT *, rowid FROM Kunde Where archived = 0 ORDER BY name ASC")
    LiveData<List<Kunde>> getAllKunden();

    @Query("SELECT *, rowid FROM Kunde Where archived = 1 ORDER BY name ASC")
    LiveData<List<Kunde>> getAllArchivedKunden();
}
