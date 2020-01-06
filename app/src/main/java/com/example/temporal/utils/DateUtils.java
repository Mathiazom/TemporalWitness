package com.example.temporal.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {


    public static String formatTimeInterval(final Date startTime, final Date endTime){

        long duration = endTime.getTime() - startTime.getTime();

        int hours = (int)(duration / (1000*60*60));
        int mins = (int)((duration - 1000*60*60*hours)/(1000*60));
        int secs = (int)((duration - 1000*60*mins)/(1000));

        String durationText = secs + "s";
        if(hours > 0){
            durationText = mins + "m " + durationText;
            durationText = hours + "h " + durationText;
        }else if (mins > 0){

            durationText = mins + "m " + durationText;

        }

        return durationText;

    }


    public static String currentTimeString(){

        final Calendar now = Calendar.getInstance();

        final String hour = String.valueOf(now.get(Calendar.HOUR_OF_DAY));
        final String minute = String.valueOf(now.get(Calendar.MINUTE));

        final StringBuilder currentTime = new StringBuilder();

        if (hour.length() < 2){
            currentTime.append("0");
        }

        currentTime
                .append(hour)
                .append(":");

        if (minute.length() < 2){
            currentTime.append("0");
        }

        currentTime.append(minute);

        return currentTime.toString();


    }


}
