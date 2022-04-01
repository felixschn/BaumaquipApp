package com.example.rentalApplication.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(indices = {@Index(value = {"name", "location"},
        unique = true)})
public class Kunde {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    public int idKunde;

    @NonNull
    private String name;
    @NonNull
    private String telefonNumber;
    @NonNull
    private String email;
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

    //create Boolean varibale for expandable recyclerview
    private Boolean expanded;

    private Boolean archived;


    public Kunde(@NonNull String name, @NonNull String telefonNumber, @NonNull String email, @NonNull String streetName, @NonNull String streetNumber, @NonNull String zip, @NonNull String location, @NonNull String constructionSide, @NonNull String contactPerson) {

        this.name = name;
        this.telefonNumber = telefonNumber;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.email = email;
        this.zip = zip;
        this.location = location;
        this.constructionSide = constructionSide;
        this.contactPerson = contactPerson;

        this.expanded = false;
        this.archived = false;
    }

    public int getIdKunde() {
        return idKunde;
    }

    public void setIdKunde(int idKunde) {
        this.idKunde = idKunde;
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

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
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

    public static Kunde[] populateKundeData(){
        return new Kunde[]{
                new Kunde("Bergholz GmbH", "01234567891", "bergholz@gmx.de", "Zum Spielplatz", "12", "08525", "Plauen", "Plauen", "Klaus Bergholz")
        };
    }
}
