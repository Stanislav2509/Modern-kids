package com.app.ModernKids.model.enums;

public enum StatusName {
    NEW("New"),
    SENT_BY_COURIER("Sent by courier"),
    COMPLETED("Completed");

    private final String displayValue;

     StatusName(String displayValue){
        this.displayValue=displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
