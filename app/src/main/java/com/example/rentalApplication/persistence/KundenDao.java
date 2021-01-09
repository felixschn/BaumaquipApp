package com.example.rentalApplication.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.rentalApplication.models.Kunde;

import java.util.List;

@Dao
public interface KundenDao {

    @Insert
    void insert(Kunde kunde);

    @Delete
    void delete(Kunde kunde);

    @Query("SELECT *, rowid FROM Baumaschine")
    LiveData<List<Kunde>> getAllKunden();
}
