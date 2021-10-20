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
    private List<Baumaschine> vertragBaumaschineList;

    @NonNull
    private String vertragKunde;

    @NonNull
    private LocalDate beginnLeihe;

    @NonNull
    private LocalDate endeLeihe;

    private Boolean expanded;
    private Boolean archived;

    /*liste erstellen, die eine Reihe von Baumaschinen IDs hält, um mehrere Baumaschinen einen Kunden zu zuordnen*/

    public Vertrag(@NonNull List<Baumaschine> vertragBaumaschineList, @NonNull String vertragKunde, @NonNull LocalDate beginnLeihe, @NonNull LocalDate endeLeihe) {
        this.idVertrag = idVertrag;
        this.vertragBaumaschineList = vertragBaumaschineList;
        this.vertragKunde = vertragKunde;
        this.beginnLeihe = beginnLeihe;
        this.endeLeihe = endeLeihe;
        //set Boolean initially to false
        this.expanded = false;
        this.archived = false;


    }

    public int getIdVertrag() {
        return idVertrag;
    }

    public List<Baumaschine> getVertragBaumaschineList() {
        return vertragBaumaschineList;
    }

    @NonNull
    public String getVertragKunde() {
        return vertragKunde;
    }

    public void setVertragKunde(@NonNull String vertragKunde) {
        this.vertragKunde = vertragKunde;
    }

    @NonNull
    public LocalDate getBeginnLeihe() {
        return beginnLeihe;
    }

    public void setBeginnLeihe(@NonNull LocalDate beginnLeihe) {
        this.beginnLeihe = beginnLeihe;
    }

    @NonNull
    public LocalDate getEndeLeihe() {
        return endeLeihe;
    }

    public void setEndeLeihe(@NonNull LocalDate endeLeihe) {
        this.endeLeihe = endeLeihe;
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
