package com.example.rentalApplication.persistence;

import androidx.room.TypeConverter;

import java.math.BigDecimal;

public class BigDecimalConverter {

    @TypeConverter
    public static String BigDecimalToString (BigDecimal price){
        return price.toString();
    }
    @TypeConverter
    public static BigDecimal stringToBigDecimal (String priceAsString){
        return new BigDecimal(priceAsString);
    }
}
