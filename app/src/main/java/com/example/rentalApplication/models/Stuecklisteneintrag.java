package com.example.rentalApplication.models;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.rentalApplication.persistence.BaumaschinenRepository;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
public class Stuecklisteneintrag {

    @ColumnInfo(name = "rowid")
    @PrimaryKey(autoGenerate = true)
    public int idStueckList;

    @NonNull
    private int idBaumaschine;

    @NonNull
    private Integer amount;


    @TypeConverters(Converters.class)
    private Double operatingHours_begin;

    @TypeConverters(Converters.class)
    private Double operatingHours_end;

    @NonNull
    @TypeConverters(Converters.class)
    private Boolean insurance = false;

    @NonNull
    @TypeConverters(Converters.class)
    private BigDecimal price;

    @Ignore
    private BaumaschinenRepository baumaschinenRepository;
    @Ignore
    private Application application;



    private LocalDate beginDate, endDate;

    public Stuecklisteneintrag() {
    }

    public Stuecklisteneintrag(@NonNull int idBaumaschine, @NonNull Integer amount,
                               @NonNull LocalDate beginDate, @NonNull LocalDate endDate, Application application) {
        this.idBaumaschine = idBaumaschine;
        this.amount = amount;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.application = application;


        this.operatingHours_end = null;

        baumaschinenRepository = BaumaschinenRepository.getInstance(application);
        Baumaschine baumaschine = baumaschinenRepository.getBaumaschineById(idBaumaschine);

        this.operatingHours_begin = baumaschine.getOperatingHours();



        long rental_period = ChronoUnit.DAYS.between(beginDate, endDate) + 1;  // +1 to include last day

        //TODO: Check & adjust logic of if-elif-else
        if ((4 == rental_period) &&
                (DayOfWeek.FRIDAY == beginDate.getDayOfWeek()) &&
                (DayOfWeek.MONDAY == endDate.getDayOfWeek())) {

            this.price = baumaschine.getPricePerWeekend();
        } else if (30 < rental_period) {
            this.price = baumaschine.getPricePerMonth().multiply(BigDecimal.valueOf(rental_period / 30));
        } else {
            this.price = baumaschine.getPricePerDay().multiply(BigDecimal.valueOf(rental_period));
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

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}

