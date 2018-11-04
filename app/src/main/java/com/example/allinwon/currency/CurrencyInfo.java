package com.example.allinwon.currency;

import com.google.gson.annotations.SerializedName;

public class CurrencyInfo {
    @SerializedName("name")
    private String currencyFName;
    private String currencySName;

    @SerializedName("rate")
    private double currencyRate;

    @SerializedName("date")
    private String currencyDate;

    private int currencyAmount;

    public CurrencyInfo() {
        currencyAmount = 1;
        currencySName = "";
    }

    public String getCurrencyFName() {
        return currencyFName;
    }

    public String getCurrencySName() {
        return currencySName;
    }

    public double getCurrencyRate() {
        return currencyRate;
    }

    public String getCurrencyDate() {
        return currencyDate;
    }

    public int getCurrencyAmount() {
        return currencyAmount;
    }

    public void setCurrencyFName(String name) {
        currencyFName = name;
    }

    public void setCurrencyRate(int rate) {
        currencyRate *= rate;
    }

    public void setCurrencyAmount(int amount) {
        currencyAmount = amount;
    }
}
