package com.example.rentalApplication.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.rentalApplication.models.Vertrag;

import java.util.List;
@Dao
public interface VertragDao {

    @Insert
    void insert(Vertrag vertrag);

    @Delete
    void delete(Vertrag vertrag);

    //@Query("SELECT *, rowid From Vertrag")
    //LiveData<List<Vertrag>> getAllVertrag;
    @Query("SELECT *, rowid From Vertrag")
    LiveData<List<Vertrag>> getAllVertrag();

}
