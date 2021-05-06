package com.example.rentalApplication.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;

@Fts4
@Entity
public class Kunde {
    @PrimaryKey
    public int rowid;

    @NonNull
    private String name;
    @NonNull
    private String telefonNumber;
    @NonNull
    private String streetName;
    @NonNull
    private String streetNumber;
    @NonNull
    private String zip;
    @NonNull
    private String location;
    @NonNull
    private String constructionSide;
    @NonNull
    private String contactPerson;


    public Kunde(@NonNull String name, @NonNull String streetName, @NonNull String streetNumber, @NonNull String zip, @NonNull String location, @NonNull String telefonNumber, @NonNull String constructionSide, @NonNull String contactPerson) {

        this.rowid = rowid;
        this.name = name;
        this.telefonNumber = telefonNumber;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.zip = zip;
        this.location = location;
        this.constructionSide = constructionSide;
        this.contactPerson = contactPerson;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelefonNumber() {
        return telefonNumber;
    }

    public void setTelefonNumber(String telefonNumber) {
        this.telefonNumber = telefonNumber;
    }

    public String getStreetName() {
        return streetName;
    }


    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(@NonNull String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getConstructionSide() {
        return constructionSide;
    }

    public void setConstructionSide(String constructionSide) {
        this.constructionSide = constructionSide;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }
}
