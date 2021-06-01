package com.example.rentalApplication.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Fts4
@Entity
public class Vertrag {
    @TypeConverters(Converters.class)
    @PrimaryKey
    public int rowid;

    @TypeConverters(Converters.class)
    private List<Baumaschine> vertragBaumaschineList;

    @NonNull
    private String vertragKunde;

    @NonNull
    private String beginnLeihe;

    @NonNull
    private String endeLeihe;

    /*liste erstellen, die eine Reihe von Baumaschinen IDs hält, um mehrere Baumaschinen einen Kunden zu zuordnen*/

    public Vertrag(@NonNull List<Baumaschine> vertragBaumaschineList, @NonNull String vertragKunde, @NonNull String beginnLeihe, @NonNull String endeLeihe) {
        this.rowid = rowid;
        this.vertragBaumaschineList = vertragBaumaschineList;
        this.vertragKunde = vertragKunde;
        this.beginnLeihe = beginnLeihe;
        this.endeLeihe = endeLeihe;

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
