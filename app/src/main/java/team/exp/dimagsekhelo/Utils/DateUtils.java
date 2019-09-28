package team.exp.dimagsekhelo.Utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public String getTimeDifferenceFromCurrentTime(String date1){
        Date date  = getDateFromString(date1);
        Log.d("Date ",date.toString());
        Date currentDate = new Date();
        Double difference = 0D;

        if(date != null)
            difference = (double) (Math.abs(currentDate.getTime() - date.getTime()));

        Log.d("Difference",difference+" : "+difference/(3600000 * 24));

        Integer days = (int) (difference / (1000*60*60*24));
        Double tempDays = (Double) (difference / (1000*60*60*24));

        Integer hours = (int) (difference - (tempDays * (1000*60*60*24)));

        return (days+1)+" Days "+hours+" Hrs";

    }


    private Date getDateFromString(String date){
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
