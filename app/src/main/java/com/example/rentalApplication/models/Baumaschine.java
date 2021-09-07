package com.example.rentalApplication.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.rentalApplication.persistence.BigDecimalConverter;

import java.math.BigDecimal;

@Fts4
@Entity
public class Baumaschine {

    @PrimaryKey
    public int rowid;
    @NonNull
    private String machineName;
    @NonNull
    private Integer amount;
    @NonNull
    @TypeConverters(Converters.class)
    private BigDecimal pricePerDay;
    @NonNull
    @TypeConverters(Converters.class)
    private BigDecimal pricePerWeekend;
    @NonNull
    @TypeConverters(Converters.class)
    private BigDecimal pricePerMonth;

    private Double operatingHours;
    private String degreeOfWear;
    private String amountOfGas;

    public Baumaschine(@NonNull String machineName, @NonNull Integer amount, @NonNull BigDecimal pricePerDay, @NonNull BigDecimal pricePerWeekend, @NonNull BigDecimal pricePerMonth, Double operatingHours, String degreeOfWear, String amountOfGas) {
        this.machineName = machineName;
        this.amount = amount;
        this.pricePerDay = pricePerDay;
        this.pricePerWeekend = pricePerWeekend;
        this.pricePerMonth = pricePerMonth;
        this.operatingHours = operatingHours;
        this.degreeOfWear = degreeOfWear;
        this.amountOfGas = amountOfGas;
    }

    public int getRowid() {
        return rowid;
    }

    public void setRowid(int rowid) {
        this.rowid = rowid;
    }

    @NonNull
    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(@NonNull String machineName) {
        this.machineName = machineName;
    }

    @NonNull
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(@NonNull Integer amount) {
        this.amount = amount;
    }

    @NonNull
    public BigDecimal getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(@NonNull BigDecimal pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    @NonNull
    public BigDecimal getPricePerWeekend() {
        return pricePerWeekend;
    }

    public void setPricePerWeekend(@NonNull BigDecimal pricePerWeekend) {
        this.pricePerWeekend = pricePerWeekend;
    }

    @NonNull
    public BigDecimal getPricePerMonth() {
        return pricePerMonth;
    }

    public void setPricePerMonth(@NonNull BigDecimal pricePerMonth) {
        this.pricePerMonth = pricePerMonth;
    }

    public Double getOperatingHours() {
        return operatingHours;
    }

    public void setOperatingHours(Double operatingHours) {
        this.operatingHours = operatingHours;
    }

    public String getDegreeOfWear() {
        return degreeOfWear;
    }

    public void setDegreeOfWear(String degreeOfWear) {
        this.degreeOfWear = degreeOfWear;
    }

    public String getAmountOfGas() {
        return amountOfGas;
    }

    public void setAmountOfGas(String amountOfGas) {
        this.amountOfGas = amountOfGas;
    }


}
