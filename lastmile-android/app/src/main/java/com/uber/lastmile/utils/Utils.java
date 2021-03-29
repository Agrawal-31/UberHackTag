package com.uber.lastmile.utils;

import android.net.Uri;

import com.uber.lastmile.models.RiderRoute;
import com.uber.lastmile.models.RouteOption;

public class Utils {

    public static String secondsToString(int totalSecs) {
        int hours = totalSecs / 3600;
        int minutes = (totalSecs % 3600) / 60;

        String timeString = "ET:";

        if (hours > 0) {
            timeString += String.format(" %dhr", hours);
        }

        timeString += String.format(" %dmin", minutes);
        return timeString;
    }

    public static String stringTrimmer(String str) {
        if (str.length() <= 15)
            return str;

        return str.substring(0, 15) + "...";
    }

    public static Uri googleMapsUriFormer(RouteOption routeOption, RiderRoute riderRoute) {
        return Uri.parse("https://www.google.com/maps/dir/?api=1&origin=" + riderRoute.getRiderSourceX() + "," + riderRoute.getRiderSourceY()
                + "&destination=" + riderRoute.getRiderDestinationX() + "," + riderRoute.getRiderDestinationY()
                + "&waypoints=" + routeOption.getPackageSourceX() + "," + routeOption.getPackageSourceY()
                + "|" + routeOption.getPackageDestinationX() + "," + routeOption.getPackageDestinationY()
                + "&travelmode=driving");
    }
}
