package com.example.rentalApplication.models;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
public class Stuecklisteneintrag {

    @ColumnInfo(name = "rowid")
    @PrimaryKey
    public int idStueckList;
    @NonNull
    private int idBaumaschine;
    @NonNull
    private Integer amount;
    @NonNull
    private Double operatingHours_begin;
    private Double operatingHours_end;
    @NonNull
    private Boolean insurance = false;
    @NonNull
    private BigDecimal price;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Stuecklisteneintrag(@NonNull Baumaschine machine, @NonNull Integer amount,
                               @NonNull LocalDate begin_date, @NonNull LocalDate end_date) {
        this.idBaumaschine = machine.getIdBaumaschine();
        this.amount = amount;
        this.operatingHours_begin = machine.getOperatingHours();
        this.operatingHours_end = null;


        long rental_period = ChronoUnit.DAYS.between(begin_date, end_date) + 1;  // +1 to include last day

        //TODO: Check & adjust logic of if-elif-else
        if ((4 == rental_period) &&
                (DayOfWeek.FRIDAY == begin_date.getDayOfWeek()) &&
                (DayOfWeek.MONDAY == end_date.getDayOfWeek())) {

            this.price = machine.getPricePerWeekend();
        } else if (30 < rental_period) {
            this.price = machine.getPricePerMonth().multiply(BigDecimal.valueOf(rental_period / 30));
        } else {
            this.price = machine.getPricePerDay().multiply(BigDecimal.valueOf(rental_period));
        }
    }

    public int getIdStueckList() {
        return idStueckList;
    }

    public void setIdStueckList(int idStueckList) {
        this.idStueckList = idStueckList;
    }

    public int getIdBaumaschine() {
        return idBaumaschine;
    }

    public void setIdBaumaschine(int idBaumaschine) {
        this.idBaumaschine = idBaumaschine;
    }

    @NonNull
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(@NonNull Integer amount) {
        this.amount = amount;
    }

    @NonNull
    public Double getOperatingHours_begin() {
        return operatingHours_begin;
    }

    public void setOperatingHours_begin(@NonNull Double operatingHours_begin) {
        this.operatingHours_begin = operatingHours_begin;
    }

    public Double getOperatingHours_end() {
        return operatingHours_end;
    }

    public void setOperatingHours_end(Double operatingHours_end) {
        this.operatingHours_end = operatingHours_end;
    }

    @NonNull
    public Boolean getInsurance() {
        return insurance;
    }

    public void setInsurance(@NonNull Boolean insurance) {
        this.insurance = insurance;
    }

    @NonNull
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(@NonNull BigDecimal price) {
        this.price = price;
    }

}

