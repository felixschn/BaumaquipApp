package com.example.rentalApplication.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.math.BigDecimal;


@Entity
public class Baumaschine {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    public int idBaumaschine;
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
    //create Boolean variable for expandable recyclerview
    @Ignore
    private Boolean expanded;
    private Boolean archived;

    public Baumaschine(@NonNull String machineName, @NonNull Integer amount, @NonNull BigDecimal pricePerDay, @NonNull BigDecimal pricePerWeekend, @NonNull BigDecimal pricePerMonth, Double operatingHours, String degreeOfWear, String amountOfGas) {
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
        this.archived = false;
    }

    public int getIdBaumaschine() {
        return idBaumaschine;
    }

    public void setIdBaumaschine(int idBaumaschine) {
        this.idBaumaschine = idBaumaschine;
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


    //create getter and setter to manipulate the boolean variable per click
    public Boolean getExpanded() {
        return expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    public static Baumaschine[] populateBaumaschinenData(){
        return new Baumaschine[]{
                new Baumaschine("Bagger", 1,new BigDecimal(100), new BigDecimal(180), new BigDecimal(2000), 4000.0, "gut","voll"),
                new Baumaschine("Kran", 1,new BigDecimal(200), new BigDecimal(280), new BigDecimal(4000), 1000.0, "schlecht","voll")
        };
    }
}
