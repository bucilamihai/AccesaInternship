package com.example.price_comparator.utils;

public class MathUtils {

    public static double calculatePercentage(double value, int percentage) {
        double result = (value * percentage) / 100.0;
        return result;
    }

    public static double round(double value, int precision) {
        double scale = Math.pow(10, precision);
        return Math.round(value * scale) / scale;
    }
}
