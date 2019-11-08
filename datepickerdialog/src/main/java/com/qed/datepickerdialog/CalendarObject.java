package com.qed.datepickerdialog;

public class CalendarObject {
    private String date = "";
    boolean enable = true;
    String monthName;
    String year;
    String fullDateName = "";

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getFullDateName() {
        return fullDateName;
    }

    public void setFullDateName(String fullDateName) {
        this.fullDateName = fullDateName;
    }
}
