package com.example.rentalApplication.persistence;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.rentalApplication.models.Stuecklisteneintrag;
import com.example.rentalApplication.models.Vertrag;

import java.time.LocalDate;
import java.util.List;

@Dao
public interface StuecklisteneintragDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Stuecklisteneintrag stuecklisteneintrag);
    @Delete
    void delete(Stuecklisteneintrag stuecklisteneintrag);
    @Update
    void update(Stuecklisteneintrag stuecklisteneintrag);

    @Query("SELECT *, rowid From stuecklisteneintrag WHERE archived = 0 and idBaumaschine = :id")
    List<Stuecklisteneintrag> getAllStuecklisteneintragForId(int id);

    @Query("SELECT *, rowid From Stuecklisteneintrag WHERE rowid = :id")
    Stuecklisteneintrag getStuecklisteneintragById(int id);

    @Query("SELECT *, rowid FROM Stuecklisteneintrag WHERE (beginDate BETWEEN :start AND :end OR endDate BETWEEN :start AND :end OR (beginDate < :end AND :start < endDate)) and idBaumaschine = :id and archived = 0")
    List<Stuecklisteneintrag> getStuecklisteneintragForDate(LocalDate start, LocalDate end, int id);

    @Query("SELECT *, rowid FROM Stuecklisteneintrag WHERE archived = 0")
    List<Stuecklisteneintrag> getAllStuecklisteneintrag();


}
