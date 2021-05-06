package com.example.rentalApplication.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.rentalApplication.models.Baumaschine;

import java.util.List;

@Dao
public interface BaumaschinenDao {

    @Insert
    void insert(Baumaschine baumaschine);

    @Delete
    void delete(Baumaschine baumaschine);

    @Query("SELECT *, rowid FROM Baumaschine")
    LiveData<List<Baumaschine>> getAllBaumaschinen();

    @Query("SELECT *, rowid From Baumaschine")
    List<Baumaschine> getBaumaschine();

}
