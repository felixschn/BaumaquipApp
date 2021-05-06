package com.example.rentalApplication.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Fts4
@Entity
public class Vertrag {
    @PrimaryKey
    public int rowid;

    @NonNull
    private String vertragBaumaschine;

    @NonNull
    private String vertragKunde;

    @NonNull
    private String beginnLeihe;

    @NonNull
    private String endeLeihe;


    /*liste erstellen, die eine Reihe von Baumaschinen IDs hält, um mehrere Baumaschinen einen Kunden zu zuordnen*/

    public Vertrag(@NonNull String vertragBaumaschine, @NonNull String vertragKunde, @NonNull String beginnLeihe, @NonNull String endeLeihe){
        this.rowid = rowid;
        this.vertragBaumaschine = vertragBaumaschine;
        this.vertragKunde = vertragKunde;
        this.beginnLeihe = beginnLeihe;
        this.endeLeihe = endeLeihe;
    }


    @NonNull
    public String getVertragBaumaschine() {
        return vertragBaumaschine;
    }

    public void setVertragBaumaschine(@NonNull String vertragBaumaschine) {
        this.vertragBaumaschine = vertragBaumaschine;
    }

    @NonNull
    public String getVertragKunde() {
        return vertragKunde;
    }

    public void setVertragKunde(@NonNull String vertragKunde) {
        this.vertragKunde = vertragKunde;
    }

    @NonNull
    public String getBeginnLeihe() {
        return beginnLeihe;
    }

    public void setBeginnLeihe(@NonNull String beginnLeihe) {
        this.beginnLeihe = beginnLeihe;
    }

    @NonNull
    public String getEndeLeihe() {
        return endeLeihe;
    }

    public void setEndeLeihe(@NonNull String endeLeihe) {
        this.endeLeihe = endeLeihe;
    }
}
