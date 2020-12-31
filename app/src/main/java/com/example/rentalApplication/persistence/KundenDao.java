package com.example.rentalApplication.persistence;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;

import com.example.rentalApplication.models.Kunde;

@Dao
public interface KundenDao {

    @Insert
    void insert(Kunde kunde);

    @Delete
    void delete(Kunde kunde);
}
