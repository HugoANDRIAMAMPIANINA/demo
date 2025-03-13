package com.example.demo.model;

public class Expense {
    private String period;
    private float total;
    private float housing;
    private float food;
    private float outing;
    private float transport;
    private float travel;
    private float taxes;
    private float other;

    public Expense(String period, float total, float housing, float food, float outing, float transport, float travel, float taxes, float other) {
        this.period = period;
        this.total = total;
        this.housing = housing;
        this.food = food;
        this.outing = outing;
        this.transport = transport;
        this.travel = travel;
        this.taxes = taxes;
        this.other = other;
    }

    public Expense(String period, float total, float housing, float outing, float transport, float travel, float taxes, float other) {}

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

    public float getHousing() {
        return housing;
    }

    public void setHousing(float housing) {
        this.housing = housing;
    }

    public float getFood() {
        return food;
    }

    public void setFood(float food) {
        this.food = food;
    }

    public float getOuting() {
        return outing;
    }

    public void setOuting(float outing) {
        this.outing = outing;
    }

    public float getTransport() {
        return transport;
    }

    public void setTransport(float transport) {
        this.transport = transport;
    }

    public float getTravel() {
        return travel;
    }

    public void setTravel(float travel) {
        this.travel = travel;
    }

    public float getTaxes() {
        return taxes;
    }

    public void setTaxes(float taxes) {
        this.taxes = taxes;
    }

    public float getOther() {
        return other;
    }

    public void setOther(float other) {
        this.other = other;
    }
}
