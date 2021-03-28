package com.uber.lastmile.utils;

public class Utils {

    public static String secondsToString(int totalSecs){
        int hours = totalSecs / 3600;
        int minutes = (totalSecs % 3600) / 60;

        String timeString = "ET:";

        if(hours > 0) {
         timeString += String.format(" %dhr", hours);
        }

        timeString += String.format(" %dmin", minutes);
        return timeString;
    }

    public static String stringTrimmer(String str){
        if(str.length() <= 15)
            return str;

        return str.substring(0, 15) + "...";
    }
}
