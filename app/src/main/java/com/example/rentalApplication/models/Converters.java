package com.example.rentalApplication.models;


import android.os.Build;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Converters implements Serializable {
    @TypeConverter
    public String fromVertragBaumaschineList(List<Baumaschine> vertragBaumaschineList) {
        if (null == vertragBaumaschineList) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Baumaschine>>() {
        }.getType();
        return gson.toJson(vertragBaumaschineList, type);
    }

    @TypeConverter
    public List<Baumaschine> toVertragBaumaschineList(String vertragBaumaschineString) {
        if (null == vertragBaumaschineString) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Baumaschine>>() {
        }.getType();
        return gson.fromJson(vertragBaumaschineString, type);
    }

    @TypeConverter
    public static String bigDecimalToString(BigDecimal price) {
        return price.toString();
    }

    @TypeConverter
    public static BigDecimal stringToBigDecimal(String priceAsString) {
        return new BigDecimal(priceAsString);
    }

    @TypeConverter
    public static String localDateToString(LocalDate date) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yy", Locale.GERMAN);
        return date.format(dateFormat);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static LocalDate stringToLocalDate(String strDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        return LocalDate.parse(strDate, formatter);
    }

    public static LocalDate editTextToLocalDate(EditText editText) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        return LocalDate.parse(editText.getText().toString(), formatter);
    }

    public static String dateToString(Date date) {
        //format the Date Type in LocaleDate
        LocalDate ldate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDateToString(ldate);

    }

    public String fromIntegerList(List<Integer> intList) {
        if (intList == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Integer>>() {
        }.getType();
        return gson.toJson(intList, type);

    }

    @TypeConverter
    public List<Integer> toIntegerList(String intListString) {
        if (null == intListString) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Integer>>() {
        }.getType();
        return gson.fromJson(intListString, type);
    }

    @TypeConverter
    public String stuecklisteToString(List<Stuecklisteneintrag> stueckliste) {
        if (null == stueckliste) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Stuecklisteneintrag>>() {
        }.getType();
        return gson.toJson(stueckliste, type);
    }

    @TypeConverter
    public List<Stuecklisteneintrag> stringToStueckliste(String string) {
        if (null == string) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Baumaschine>>() {
        }.getType();
        return gson.fromJson(string, type);
    }
}