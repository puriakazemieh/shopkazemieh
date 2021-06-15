package com.example.mvvm.utils;

import java.text.DecimalFormat;

public class PriceConverter {
    public static String converter(int price) {
        DecimalFormat decimalFormat=new DecimalFormat("###,###");
        String price1=decimalFormat.format(Integer.valueOf(price));
        return price1 + " تومان ";
    }
}
