package com.example.rentalApplication.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Entity
public class Vertrag {
    @ColumnInfo(name = "rowid")
    @PrimaryKey(autoGenerate = true)
    public int idVertrag;

    @TypeConverters(Converters.class)
    private List<Integer> stuecklisteIds;

    @NonNull
    private int idKunde;

    @NonNull
    private LocalDate beginnVertrag;

    @NonNull
    private LocalDate endeVertrag;

    private Boolean expanded;
    private Boolean archived;

    private BigDecimal sumOfRent;
    private BigDecimal discountOfRent;

    /*liste erstellen, die eine Reihe von Baumaschinen IDs hält, um mehrere Baumaschinen einen Kunden zu zuordnen*/

    public Vertrag(@NonNull List<Integer> stuecklisteIds, @NonNull int idKunde, @NonNull LocalDate beginnVertrag, @NonNull LocalDate endeVertrag, @NonNull BigDecimal sumOfRent, @NonNull BigDecimal discountOfRent ) {
        this.stuecklisteIds = stuecklisteIds;
        this.idKunde = idKunde;
        this.beginnVertrag = beginnVertrag;
        this.endeVertrag = endeVertrag;
        //set Boolean initially to false
        this.expanded = false;
        this.archived = false;

        this.sumOfRent = sumOfRent;
        this.discountOfRent = discountOfRent;


    }

    public int getIdVertrag() {
        return idVertrag;
    }


    @NonNull
    public int getIdKunde() {
        return idKunde;
    }

    public void setIdKunde(@NonNull int idKunde) {
        this.idKunde = idKunde;
    }

    @NonNull
    public LocalDate getBeginnVertrag() {
        return beginnVertrag;
    }

    public void setBeginnVertrag(@NonNull LocalDate beginnVertrag) {
        this.beginnVertrag = beginnVertrag;
    }

    @NonNull
    public LocalDate getEndeVertrag() {
        return endeVertrag;
    }

    public void setEndeVertrag(@NonNull LocalDate endeVertrag) {
        this.endeVertrag = endeVertrag;
    }

    public List<Integer> getStuecklisteIds() {
        return stuecklisteIds;
    }

    public void setStuecklisteIds(List<Integer> stuecklisteIds) {
        stuecklisteIds = stuecklisteIds;
    }

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

    public BigDecimal getSumOfRent() {
        return sumOfRent;
    }

    public void setSumOfRent(BigDecimal sumOfRent) {
        this.sumOfRent = sumOfRent;
    }

    public BigDecimal getDiscountOfRent() {
        return discountOfRent;
    }

    public void setDiscountOfRent(BigDecimal discountOfRent) {
        this.discountOfRent = discountOfRent;
    }
}
