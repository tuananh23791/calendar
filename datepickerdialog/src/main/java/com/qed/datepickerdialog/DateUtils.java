package com.qed.datepickerdialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static final DateUtils ourInstance = new DateUtils();

    public static DateUtils getInstance() {
        return ourInstance;
    }

    private DateUtils() {
    }

    public Calendar getCalenDarSelected() {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateFormat.parse(DatePickerDialog.stringDateSelected));
            return calendar;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return Calendar.getInstance();
    }

    public Date getDateSelected() {
        try {
            return dateFormat.parse(DatePickerDialog.stringDateSelected);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new Date();
    }

    public int getYearSelected() {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateFormat.parse(DatePickerDialog.stringDateSelected));
            return calendar.get(Calendar.YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public String getCurrentStringDate() {
        Calendar calendar = Calendar.getInstance();

        return dateFormat.format(calendar.getTime());
    }

    public SimpleDateFormat getSimpleDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(SimpleDateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }
}
