package com.example.apple.indianmistry;

public class servicez {
    String type;
    String problem;
    String day,mon,year;
    String hour,min;
    String ap;
    String add;

    public servicez() {

    }

    public servicez(String type, String problem, String add, String day, String mon, String year, String hour, String min, String ap) {
        this.type = type;
        this.problem = problem;
        this.day = day;
        this.mon = mon;
        this.year = year;
        this.hour = hour;
        this.min = min;
        this.ap = ap;
        this.add = add;
    }

    public String getAp() {
        return ap;
    }

    public void setAp(String ap) {
        this.ap = ap;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMon() {
        return mon;
    }

    public void setMon(String mon) {
        this.mon = mon;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }



}
