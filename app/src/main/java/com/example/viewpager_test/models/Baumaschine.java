package com.example.viewpager_test.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;

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
    private Double pricePerDay;

    private Double operatingHours;
    private String degreeOfWear;
    private String amountOfGas;


    public Baumaschine(int rowid, @NonNull String machineName, @NonNull Integer amount, @NonNull Double pricePerDay, Double operatingHours, String degreeOfWear, String amountOfGas) {
        this.rowid = rowid;
        this.machineName = machineName;
        this.amount = amount;
        this.pricePerDay = pricePerDay;
        this.operatingHours = operatingHours;
        this.degreeOfWear = degreeOfWear;
        this.amountOfGas = amountOfGas;
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
    public Double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(@NonNull Double pricePerDay) {
        this.pricePerDay = pricePerDay;
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
