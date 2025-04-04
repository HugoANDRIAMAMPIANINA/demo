package com.example.demo.model;

public class Income {
    private String period;
    private float total;
    private float salary;
    private float aid;
    private float selfEmployed;
    private float passive;
    private float other;

    public Income(String period, float total, float salary, float aid, float selfEmployed, float passive, float other) {
        this.period = period;
        this.total = total;
        this.salary = salary;
        this.aid = aid;
        this.selfEmployed = selfEmployed;
        this.passive = passive;
        this.other = other;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public float getAid() {
        return aid;
    }

    public void setAid(float aid) {
        this.aid = aid;
    }

    public float getSelfEmployed() {
        return selfEmployed;
    }

    public void setSelfEmployed(float selfEmployed) {
        this.selfEmployed = selfEmployed;
    }

    public float getPassive() {
        return passive;
    }

    public void setPassive(float passive) {
        this.passive = passive;
    }

    public float getOther() {
        return other;
    }

    public void setOther(float other) {
        this.other = other;
    }
}
