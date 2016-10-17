package com.yizhao.services.clogservice;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * A thread-safe wrapper around DateFormat
 *
 * @author heng
 */
public class ThreadSafeDateFormat extends ThreadLocal<DateFormat> {

    private String format = null;
    private TimeZone tz = null;

    public ThreadSafeDateFormat( String format, TimeZone tz ) {
        this.format = format;
        this.tz = tz;
    }

    /* (non-Javadoc)
     * @see java.lang.ThreadLocal#initialValue()
     */
    @Override
    protected DateFormat initialValue() {
        DateFormat df = new SimpleDateFormat(format);
        if(tz != null){
            df.setTimeZone( tz );
        }
        return df;
    }
}
