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
                new Baumaschine("Kran", 1,new BigDecimal(200), new BigDecimal(280), new BigDecimal(4000), 1000.0, "schlecht","voll"),
                new Baumaschine("Schrauben", 100,                   new BigDecimal(200), new BigDecimal(280), new BigDecimal(4000),null,null,null),

                new Baumaschine("Stampfer BT 60", 1,                new BigDecimal(28),  new BigDecimal(42), new BigDecimal(9999), null, null, null),
                new Baumaschine("Vibrationsplatte BPR 50/ 55 D", 1, new BigDecimal(42),  new BigDecimal(63), new BigDecimal(9999), null, null, null),
                new Baumaschine("Rüttelplatte BVP 18/45", 1,        new BigDecimal(28),  new BigDecimal(42), new BigDecimal(9999), null, null, null),
                new Baumaschine("Flaschenrüttler", 1,               new BigDecimal(20),  new BigDecimal(30), new BigDecimal(9999), null, null, null),

                // TODO: Katalog Baustelle
                new Baumaschine("Stützen bis 2,50m A", 1000,       new BigDecimal(9999),  new BigDecimal(9999), new BigDecimal(5), null, null, null),
                new Baumaschine("Stützen bis 3,00m A", 1000,       new BigDecimal(9999),  new BigDecimal(9999), new BigDecimal(5), null, null, null),
                new Baumaschine("Stützen bis 3,00m B", 1000,       new BigDecimal(9999),  new BigDecimal(9999), new BigDecimal(5), null, null, null),
                new Baumaschine("Stützen bis 3,50m B", 1000,       new BigDecimal(9999),  new BigDecimal(9999), new BigDecimal(5), null, null, null),
                new Baumaschine("Stützen bis 4,00m B", 1000,       new BigDecimal(9999),  new BigDecimal(9999), new BigDecimal(5), null, null, null),
                new Baumaschine("Bauzaun Feld", 1000,              new BigDecimal(9999),  new BigDecimal(9999), new BigDecimal("3.5"), null, null, null),
                new Baumaschine("Bauzaun Fuß", 1000,               new BigDecimal(9999),  new BigDecimal(9999), new BigDecimal(2), null, null, null),
                new Baumaschine("Bautüren", 1000,                  new BigDecimal(9999),  new BigDecimal(9999), new BigDecimal(5), null, null, null),
                // TODO: Katalog Gerüste
                new Baumaschine("Gerüste", 1,                  new BigDecimal(9999),  new BigDecimal(9999), new BigDecimal(5), null, null, null),

                new Baumaschine("Kombihammer", 20,                new BigDecimal(20),  new BigDecimal(30), new BigDecimal(9999), null, null, null),
                new Baumaschine("Abbruchhammer 30 kg", 10,        new BigDecimal(38),  new BigDecimal(57), new BigDecimal(9999), null, null, null),
                new Baumaschine("Erdlochbohrer", 10,              new BigDecimal(25),  new BigDecimal(33), new BigDecimal(9999), null, null, null),
                new Baumaschine("Kernlochbohrer", 10,             new BigDecimal(38),  new BigDecimal(57), new BigDecimal(9999), null, null, null),

                new Baumaschine("Betonsilo", 1,                new BigDecimal(20),  new BigDecimal(30), new BigDecimal(9999), null, null, null),
                new Baumaschine("Bordsteinzange", 1,           new BigDecimal(10),  new BigDecimal(15), new BigDecimal(9999), null, null, null),
                new Baumaschine("Knacke", 1,                   new BigDecimal(22),  new BigDecimal(33), new BigDecimal(9999), null, null, null),
                new Baumaschine("Stapelpaletten", 1,           new BigDecimal(9999),  new BigDecimal(9999), new BigDecimal(5), null, null, null),

                new Baumaschine("Verputzmaschine VR1", 1, new BigDecimal(95),  new BigDecimal("142.5"), new BigDecimal(9999), null, null, null),
                new Baumaschine("Verputzmaschine VR6", 1, new BigDecimal(100),  new BigDecimal(150), new BigDecimal(9999), null, null, null),
                new Baumaschine("Durchlaufmischer VR2302", 1, new BigDecimal(28),  new BigDecimal(42), new BigDecimal(9999), null, null, null),
                new Baumaschine("Stielreibe", 1, new BigDecimal(28),  new BigDecimal(42), new BigDecimal(9999), null, null, null),
                new Baumaschine("Rührer", 1, new BigDecimal(17),  new BigDecimal("25.5"), new BigDecimal(9999), null, null, null),
                new Baumaschine("Airless Farbspritzgerät", 1, new BigDecimal(45),  new BigDecimal("67.5"), new BigDecimal(9999), null, null, null),

                new Baumaschine("Rotationslaser", 1, new BigDecimal(30),  new BigDecimal(45), new BigDecimal(9999), null, null, null),

                new Baumaschine("Bautrockner 158 l/Tag", 1, new BigDecimal(20),  new BigDecimal(30), new BigDecimal(9999), null, null, null),
                new Baumaschine("Bautrockner  43 l/Tag", 1, new BigDecimal(20),  new BigDecimal(30), new BigDecimal(9999), null, null, null),
                new Baumaschine("Heizgeräte 3,0 kW", 1, new BigDecimal(8),  new BigDecimal(12), new BigDecimal(9999), null, null, null),
                new Baumaschine("Heizgeräte 9,0 kW", 1, new BigDecimal(10),  new BigDecimal(15), new BigDecimal(9999), null, null, null),
                new Baumaschine("Laubbläser", 1,        new BigDecimal(14),  new BigDecimal(26), new BigDecimal(9999), null, null, null),
                new Baumaschine("Schneefräse", 1,       new BigDecimal(55),  new BigDecimal("82.5"), new BigDecimal(9999), null, null, null),

                new Baumaschine("Fugenschneider", 1,  new BigDecimal(33),  new BigDecimal("49.5"), new BigDecimal(9999), null, null, null),
                new Baumaschine("Tischssäge", 1,      new BigDecimal(38),  new BigDecimal(57), new BigDecimal(9999), null, null, null),
                new Baumaschine("Baukreissäge", 1,    new BigDecimal(35),  new BigDecimal(52), new BigDecimal(9999), null, null, null),
                new Baumaschine("Trennschneider", 1,  new BigDecimal(28),  new BigDecimal(42), new BigDecimal(9999), null, null, null),
                new Baumaschine("Bandsäge", 1,        new BigDecimal(35),  new BigDecimal(44), new BigDecimal(9999), null, null, null),
                new Baumaschine("Betonschleifer", 1,  new BigDecimal(28),  new BigDecimal(42), new BigDecimal(9999), null, null, null),
                new Baumaschine("Spezialsäge", 1,     new BigDecimal(35),  new BigDecimal("52.5"), new BigDecimal(9999), null, null, null),

                new Baumaschine("Stromerzeuger", 1,  new BigDecimal(35),  new BigDecimal("52.5"), new BigDecimal(9999), null, null, null),
                new Baumaschine("Kompressor", 1,     new BigDecimal(28),  new BigDecimal(42), new BigDecimal(9999), null, null, null),

                new Baumaschine("Hochdruckreiniger", 1,     new BigDecimal(28),  new BigDecimal(42), new BigDecimal(9999), null, null, null),
                new Baumaschine("Hochdruckreiniger Heißwasser", 1,  new BigDecimal(38),  new BigDecimal(57), new BigDecimal(9999), null, null, null),
                new Baumaschine("Nass-/ Trockensauger", 1,  new BigDecimal(22),  new BigDecimal(33), new BigDecimal(9999), null, null, null),
                new Baumaschine("C-Pumpe", 1,               new BigDecimal(25),  new BigDecimal("37.5"), new BigDecimal(9999), null, null, null),
                new Baumaschine("B-Pumpe", 1,               new BigDecimal(35),  new BigDecimal("52.5"), new BigDecimal(9999), null, null, null),

                new Baumaschine("Bagger 300.9 D", 1,                new BigDecimal(75),  new BigDecimal("112.5"), new BigDecimal(9999), null, null, null),
                new Baumaschine("Kurzheckbagger 301.6-05 A", 1,     new BigDecimal(83),  new BigDecimal("124.5"), new BigDecimal(9999), null, null, null),
                new Baumaschine("Kurzheckbagger 302.7D CR", 1,      new BigDecimal(91),  new BigDecimal("136.5"), new BigDecimal(9999), null, null, null),
                new Baumaschine("Kurzheckbagger 303.5 E2 CR", 1,    new BigDecimal(99),  new BigDecimal("148.5"), new BigDecimal(9999), null, null, null),
                new Baumaschine("Mini Dumper", 1,                   new BigDecimal(30),  new BigDecimal(45), new BigDecimal(9999), null, null, null)

        };
    }
}
