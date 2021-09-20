package com.example.rentalApplication.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.rentalApplication.models.Baumaschine;

import java.util.List;

@Dao
public interface BaumaschinenDao {

    @Insert
    void insert(Baumaschine baumaschine);

    @Delete
    void delete(Baumaschine baumaschine);

    @Update
    void update(Baumaschine baumaschine);

    @Query("SELECT *, rowid FROM Baumaschine")
    LiveData<List<Baumaschine>> getAllBaumaschinen();

    @Query("SELECT *, rowid FROM Baumaschine WHERE rowid = :rowId")
    List<Baumaschine> getBaumaschine(int rowId);


    @Query("SELECT *, rowid From Baumaschine WHERE rowid = :id")
    Baumaschine loadBaumaschineById(int id);

}
