package com.app.ModernKids.model.enums;

public enum CategoryName {
    BABY_GIRL("Baby girl"),
    BABY_BOY("Baby boy"),
    GIRL("Girl"),
    BOY("Boy");

    private final String displayValue;


    CategoryName(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue(){
        return this.displayValue;
    }
}
