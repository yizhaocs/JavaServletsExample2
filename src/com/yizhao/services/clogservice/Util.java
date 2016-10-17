package com.yizhao.services.clogservice;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yzhao on 10/17/16.
 */
public class Util {
    private static ThreadSafeDateFormat defaultDateFormat = new ThreadSafeDateFormat(Constants.DEFAULT_TIMESTAMP_FORMAT, null);

    /**
     * Formats passed {@link Date} parameter to {@link String}.
     * Faster version using static DateFormat
     *
     * @param timestamp the time stamp as a Date
     * @return a string representing passed date in the format DEFAULT_DATE_FORMAT
     */
    public static String timestampToDefaultString(Date timestamp) {
        String formatedTimstampStr = null;
        if (timestamp != null) {
            formatedTimstampStr = defaultDateFormat.get().format(timestamp);
        }
        return formatedTimstampStr;
    }

    /**
     * Formats passed {@link Date} parameter to {@link String} as per the passed
     * format.
     *
     * @param timestamp the time stamp as a Date
     * @param format format to convert the value
     * @return a string representing passed date in the format passed.
     */
    public static String timestampToString(Date timestamp, String format) {
        String formatedTimstampStr = null;
        if (timestamp != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            formatedTimstampStr = sdf.format(timestamp);
        }
        return formatedTimstampStr;
    }
}
