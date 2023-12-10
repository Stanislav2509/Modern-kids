package com.app.ModernKids.model.enums;

public enum StatusName {
    CART ("Cart"),
    BOUGHT ("Bought"),
    NEW("New"),
    SENT_BY_COURIER("Sent by courier"),
    COMPLETED("Completed"),
    FAILED("Failed"),
    ANSWERED("Answered");

    private final String displayValue;

     StatusName(String displayValue){
        this.displayValue=displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
