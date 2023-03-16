package com.lib;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class XUtils {
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public static Date convertStringtoDate(String text) {
        try {
            return sdf.parse(text);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String convertDatetoString(Date date) {
        try {
            return sdf.format(date);
        } catch (Exception e) {
            return sdf.format(new Date());
        }
    }
}
