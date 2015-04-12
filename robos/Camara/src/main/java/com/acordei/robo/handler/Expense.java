package com.acordei.robo.handler;

/**
 * Created by deivison on 4/11/15.
 */
public class Expense {

    public Expense() {
    }

    public Expense(String value, String type) {
        this.value = value;
        this.type = type;
    }

    private String value;
    private String type;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
