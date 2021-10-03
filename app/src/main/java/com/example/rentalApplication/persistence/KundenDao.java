package com.example.rentalApplication.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.rentalApplication.models.Kunde;

import java.util.List;

@Dao
public interface KundenDao {

    @Insert
    void insert(Kunde kunde);

    @Delete
    void delete(Kunde kunde);

    @Update
    void update(Kunde kunde);

    @Query("Select *, rowid from Kunde Where rowid = :id")
    Kunde loadKundeById(int id);


    @Query("SELECT *, rowid FROM Kunde ORDER BY name ASC")
    LiveData<List<Kunde>> getAllKunden();
}
