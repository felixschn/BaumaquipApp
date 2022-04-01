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

    @Insert
    void insertAll(Baumaschine... baumaschines);

    @Delete
    void delete(Baumaschine baumaschine);

    @Update
    void update(Baumaschine baumaschine);

    /*@Query("UPDATE baumaschine SET machineName = :updateName, amount = :updateAmount, pricePerDay = :updatePricePerDay, pricePerWeekend = :updatePricePerWeekend, pricePerMonth = :updatePricePerMonth, operatingHours = :updateOperatingHours, degreeOfWear = :updateDegreeOfWear, amountOfGas = :updateAmountOfGas WHERE rowid = :rowid")
    void update(int rowid, String updateName, int updateAmount, BigDecimal updatePricePerDay, BigDecimal updatePricePerWeekend, BigDecimal updatePricePerMonth, Double updateOperatingHours, String updateDegreeOfWear, String updateAmountOfGas);*/
    @Query("SELECT *, rowid FROM Baumaschine WHERE archived = 0 ORDER BY machineName ASC ")
    LiveData<List<Baumaschine>> getAllBaumaschinen();

    @Query("SELECT *, rowid FROM Baumaschine WHERE archived = 1 ORDER BY machineName ASC")
    LiveData<List<Baumaschine>> getAllArchivedBaumaschinen();

    @Query("SELECT *, rowid FROM Baumaschine WHERE amount > 0 ORDER BY machineName ASC")
    LiveData<List<Baumaschine>> getAllBaumaschinenForSpinner();

    @Query("SELECT *, rowid From Baumaschine WHERE rowid = :id")
    Baumaschine getBaumaschineById(int id);
}
