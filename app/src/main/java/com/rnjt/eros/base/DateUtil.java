package com.rnjt.eros.base;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

    public static final String FORMAT_yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public static final String FORMAT_dd_MMMM_yyyy = "dd MMMM, yyyy";
    public static final String FORMAT_yyyy_MM_dd_hh_mm_ss = "yyyy-MM-dd HH:mm:ss";

    private DateUtil() {

    }

    public static Date convertUTCtoLocalDate(String utcDateTime, String utcDateFormat) {
        SimpleDateFormat utcDateFormatter = new SimpleDateFormat(utcDateFormat);
        utcDateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date utcDate = utcDateFormatter.parse(utcDateTime);
            return utcDate;
        } catch (ParseException e) {
            LogUtils.logE( "Error Parsing UTC date : " + utcDateTime);
        }
        return new Date();
    }

    public static String convertUTCtoLocalDateTime(String timeStamp, String format) {
        String dateString = "";
        Date fDate = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_yyyy_MM_dd_hh_mm_ss);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            fDate = simpleDateFormat.parse(timeStamp);

            //TODO change h to hh
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            dateString = formatter.format(fDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateString;
    }

    public static String convertTimestampToDateTime(long timeStamp, String format) {
        String dateString = "";
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        dateString = formatter.format(timeStamp);

        return dateString;
    }

    public static String getCurrentDateTimeStamp(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }


    public static String formatDate(String date, String initDateFormat, String endDateFormat) throws ParseException {

        Date initDate = new SimpleDateFormat(initDateFormat).parse(date);
        SimpleDateFormat formatter = new SimpleDateFormat(endDateFormat);
        String parsedDate = formatter.format(initDate);

        return parsedDate;
    }

    public static String getCurrentUTCTimestamp() {
        SimpleDateFormat f = new SimpleDateFormat(FORMAT_yyyy_MM_dd_hh_mm_ss);
        f.setTimeZone(TimeZone.getTimeZone("UTC"));
        String utcTime = f.format(new Date());

        return utcTime;
    }


}
