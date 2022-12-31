package com.improve10x.doordare.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DateUtils {

    public static String getFormattedTime(long millisInFuture) {
        TimeUnits timeUnits = new TimeUnits(millisInFuture);
        String output = "";
        NumberFormat formatter = new DecimalFormat("00");
        if(!output.isEmpty() || timeUnits.days > 0) {
            output += (output.isEmpty() ? "" : ":") + formatter.format(timeUnits.days);
        }
        if(!output.isEmpty() || timeUnits.hours > 0) {
            output += (output.isEmpty() ? "" : ":") + formatter.format(timeUnits.hours);
        }
        if(!output.isEmpty() || timeUnits.minutes > 0) {
            output += (output.isEmpty() ? "" : ":") + formatter.format(timeUnits.minutes);
        }
        if(!output.isEmpty() || timeUnits.seconds > 0) {
            output += (output.isEmpty() ? "" : ":") + formatter.format(timeUnits.seconds);
        }
        return output;
    }

    public static String getTimeLeftText(long millisInFuture) {
        TimeUnits timeUnits = new TimeUnits(millisInFuture);
        String output = "";
        if(timeUnits.days > 0) {
            output += timeUnits.days + " days ";
        }
        if(timeUnits.hours > 0) {
            output += timeUnits.hours + " hours ";
        }
        if(timeUnits.minutes > 0) {
            output += timeUnits.minutes + " minutes ";
        }
        if(timeUnits.seconds > 0) {
            output += timeUnits.seconds  + " seconds ";
        }
        return output;
    }

    public static String getAdvancedTimeLeftText(long millisInFuture) {
        TimeUnits timeUnits = new TimeUnits(millisInFuture);
        String output = "";
        if(timeUnits.days > 0) {
            output += timeUnits.days + " days ";
            if(timeUnits.days > 2) return output;
        }
        if(timeUnits.hours > 0) {
            output += timeUnits.hours + " hours ";
            if(timeUnits.hours >= 10) return output;
        }
        if(timeUnits.minutes > 0) {
            output += timeUnits.minutes + " minutes ";
            if(timeUnits.minutes >= 10) return output;
        }
        if(timeUnits.seconds > 0) {
            output += timeUnits.seconds  + " seconds ";
        }
        return output;
    }

    static class TimeUnits {
        long millis = 0l;
        int days = 0;
        int hours = 0;
        int minutes = 0;
        int seconds = 0;

        public TimeUnits(long millis) {
            this.millis = millis;
            computeUnits();
        }

        private void computeUnits() {
            long millisComputed = millis;
            days = (int)(millis / (1000 * 60 * 60 * 24));
            millisComputed -= days * 24 * 60 * 60 * 1000;
            hours = (int)(millisComputed / (1000 * 60 * 60));
            millisComputed -= hours * 60 * 60 * 1000;
            minutes = (int)(millisComputed / (1000 * 60));
            millisComputed -= minutes * 60 * 1000;
            seconds = (int)(millisComputed / 1000);
        }
    }
}