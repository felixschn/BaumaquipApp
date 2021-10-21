package com.example.rentalApplication.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.time.LocalDate;
import java.util.List;

@Fts4
@Entity
public class Vertrag {
    @TypeConverters(Converters.class)
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    public int idVertrag;

    @TypeConverters(Converters.class)
    private List<Stuecklisteneintrag> stueckliste;

    @NonNull
    private int idKunde;

    @NonNull
    private LocalDate beginnVertrag;

    @NonNull
    private LocalDate endeVertrag;

    private Boolean expanded;
    private Boolean archived;

    /*liste erstellen, die eine Reihe von Baumaschinen IDs hält, um mehrere Baumaschinen einen Kunden zu zuordnen*/

    public Vertrag(@NonNull List<Stuecklisteneintrag> stueckliste, @NonNull int idKunde, @NonNull LocalDate beginnVertrag, @NonNull LocalDate endeVertrag) {
        this.stueckliste = stueckliste;
        this.idKunde = idKunde;
        this.beginnVertrag = beginnVertrag;
        this.endeVertrag = endeVertrag;
        //set Boolean initially to false
        this.expanded = false;
        this.archived = false;


    }

    public int getIdVertrag() {
        return idVertrag;
    }

    public List<Stuecklisteneintrag> getVertragStueckList() {
        return stueckliste;
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

    public List<Stuecklisteneintrag> getStueckliste() {
        return stueckliste;
    }

    public void setStueckliste(List<Stuecklisteneintrag> stueckliste) {
        stueckliste = stueckliste;
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
}
