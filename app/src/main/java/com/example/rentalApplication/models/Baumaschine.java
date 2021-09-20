package com.example.rentalApplication.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;


@Entity
public class Baumaschine {

    @PrimaryKey(autoGenerate = true)
    public int rowid;
    @NonNull
    private String machineName;
    @NonNull
    private Integer amount;
    @NonNull
    private Double pricePerDay;
    @NonNull
    private Double pricePerWeekend;
    @NonNull
    private Double pricePerMonth;

    private Double operatingHours;
    private String degreeOfWear;
    private String amountOfGas;
    //create Boolean variable for expandable recyclerview
    private Boolean expanded;

    public Baumaschine(@NonNull String machineName, @NonNull Integer amount, @NonNull Double pricePerDay, @NonNull Double pricePerWeekend, @NonNull Double pricePerMonth, Double operatingHours, String degreeOfWear, String amountOfGas) {
        this.machineName = machineName;
        this.amount = amount;
        this.pricePerDay = pricePerDay;
        this.pricePerWeekend = pricePerWeekend;
        this.pricePerMonth = pricePerMonth;
        this.operatingHours = operatingHours;
        this.degreeOfWear = degreeOfWear;
        this.amountOfGas = amountOfGas;
        //set Boolean initially to false
        this.expanded = false;
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
    public Double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(@NonNull Double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    @NonNull
    public Double getPricePerWeekend() {
        return pricePerWeekend;
    }

    public void setPricePerWeekend(@NonNull Double pricePerWeekend) {
        this.pricePerWeekend = pricePerWeekend;
    }

    @NonNull
    public Double getPricePerMonth() {
        return pricePerMonth;
    }

    public void setPricePerMonth(@NonNull Double pricePerMonth) {
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


    //create getter and setter to manipulate the boolean variable per click
    public Boolean getExpanded() {
        return expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }
}
