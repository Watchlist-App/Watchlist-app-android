package com.watchlistapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by VEINHORN on 19/01/14.
 */
public class DateUtil {
    public static String convertDate(String inputString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(inputString);
        } catch(ParseException exception) {
            exception.printStackTrace();
        }
        SimpleDateFormat postFormater = new SimpleDateFormat("MMMM dd, yyyy");
        String postDate = postFormater.format(date);
        return postDate;
    }
}
