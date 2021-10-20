package com.example.rentalApplication.models;


import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class Converters implements Serializable {
    @TypeConverter
    public String fromVertragBaumaschineList(List<Baumaschine> vertragBaumaschineList) {
        if (vertragBaumaschineList == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Baumaschine>>() {
        }.getType();
        String json = gson.toJson(vertragBaumaschineList, type);
        return json;

    }

    @TypeConverter
    public List<Baumaschine> toVertragBaumaschineList(String vertragBaumaschineString) {
        if (vertragBaumaschineString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Baumaschine>>() {
        }.getType();
        List<Baumaschine> vertragBaumaschineList = gson.fromJson(vertragBaumaschineString, type);
        return vertragBaumaschineList;
    }

    @TypeConverter
    public static String BigDecimalToString(BigDecimal price) {
        return price.toString();
    }

    @TypeConverter
    public static BigDecimal stringToBigDecimal(String priceAsString) {
        return new BigDecimal(priceAsString);
    }

    @TypeConverter
    public static String dateToString(LocalDate date){
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yy", Locale.GERMAN);
        return date.format(dateFormat);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static LocalDate stringToDate(String strDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        return LocalDate.parse(strDate, formatter);
    }



}