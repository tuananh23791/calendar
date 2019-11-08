package com.qed.datepickerdialog;

import java.util.Date;

public class ItemClickEvent {
    Date dateSelected;

    public ItemClickEvent(Date dateSelected) {
        this.dateSelected = dateSelected;
    }

    public Date getDateSelected() {
        return dateSelected;
    }

    public void setDateSelected(Date dateSelected) {
        this.dateSelected = dateSelected;
    }
}
