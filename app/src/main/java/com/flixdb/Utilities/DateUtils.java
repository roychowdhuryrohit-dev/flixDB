package com.flixdb.Utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static String formatDate(String unformattedDate) throws ParseException {

        Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(unformattedDate);
        DateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy", Locale.US);

        String formattedDate = dateFormat.format(date);
        return formattedDate;
    }
}
