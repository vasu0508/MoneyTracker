package com.vasu.moneytracker;

public class newClass {
    private String title;
    private String Value;
    private String Date;

    public newClass(String name, String birthday,String Date) {
        this.Value = birthday;
        this.title = name;
        this.Date=Date;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String Value) {
        this.Value = Value;
    }

    public String gettitle() {
        return title;
    }

    public void settitle(String title) {
        this.title = title;
    }
    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }
}
