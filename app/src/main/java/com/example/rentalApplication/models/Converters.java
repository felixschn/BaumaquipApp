package com.example.rentalApplication.models;


import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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


}