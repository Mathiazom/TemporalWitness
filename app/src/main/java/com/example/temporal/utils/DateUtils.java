package com.example.temporal.utils;

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


}
