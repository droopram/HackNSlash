package nl.antifraude.mijnid.util;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jozua on 2014/04/13.
 */
public class ParseUtils {

    private static final String TAG = "";
    private static final DateFormat FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat FORMAT_DATE_TIME = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static Date parseDateTime(String value) {
        try {
            return FORMAT_DATE_TIME.parse(value);
        } catch (ParseException e) {
            Log.e(TAG, "Error parsing datetime as java.util.date", e);
        }
        return null;
    }

    public static Date parseDate(String value) {
        try {
            FORMAT_DATE.parse(value);
        } catch (ParseException e) {
            Log.e(TAG, "Error parsing date as java.util.date", e);
        }
        return null;
    }

}
