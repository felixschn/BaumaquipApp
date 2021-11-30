package com.example.rentalApplication.persistence;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.rentalApplication.models.Stuecklisteneintrag;

import java.util.List;

@Dao
public interface StuecklisteneintragDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Stuecklisteneintrag stuecklisteneintrag);
    @Delete
    void delete(Stuecklisteneintrag stuecklisteneintrag);
    @Update
    void update(Stuecklisteneintrag stuecklisteneintrag);

    @Query("SELECT *, rowid From stuecklisteneintrag WHERE idBaumaschine = :id")
    List<Stuecklisteneintrag> getAllStuecklisteneintragForId(int id);

}
