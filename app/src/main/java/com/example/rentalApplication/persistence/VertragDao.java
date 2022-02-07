package com.example.rentalApplication.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.rentalApplication.models.Vertrag;

import java.time.LocalDate;
import java.util.List;

@Dao
public interface VertragDao {

    @Insert
    void insert(Vertrag vertrag);

    @Delete
    void delete(Vertrag vertrag);

    @Update
    void update(Vertrag vertrag);

    @Query("SELECT *, rowid from Vertrag Where rowid = :id")
    Vertrag getVertagById(int id);

    //@Query("SELECT *, rowid From Vertrag")
    //LiveData<List<Vertrag>> getAllVertrag;
    @Query("SELECT *, rowid From Vertrag WHERE archived = 0")
    LiveData<List<Vertrag>> getAllVertrag();

    @Query("SELECT *, rowid From Vertrag WHERE archived = 1")
    LiveData<List<Vertrag>> getAllArchivedVertrag();


}
