package com.example.rentalApplication.persistence;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.rentalApplication.models.Stuecklisteneintrag;

@Dao
public interface StuecklisteneintragDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Stuecklisteneintrag stuecklisteneintrag);
    @Delete
    void delete(Stuecklisteneintrag stuecklisteneintrag);
    @Update
    void update(Stuecklisteneintrag stuecklisteneintrag);

}
