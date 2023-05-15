package com.cryptocurrency.model;

public class ConversionHistoryRecord {
    private String userId;
    private String dateTime;
    private String cryptocurrency;
    private String convertedAmount;
    private String fromCurrency;
    private String toCurrency;

    public ConversionHistoryRecord(String userId, String dateTime, String cryptocurrency, String convertedAmount, String fromCurrency, String toCurrency) {
        this.userId = userId;
        this.dateTime = dateTime;
        this.cryptocurrency = cryptocurrency;
        this.convertedAmount = convertedAmount;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
    }

    public ConversionHistoryRecord() {}

    // Getters and setters

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getDateTime() {
        return dateTime;
    }
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getCryptocurrency() {
        return cryptocurrency;
    }

    public void setCryptocurrency(String cryptocurrency) {
        this.cryptocurrency = cryptocurrency;
    }

    public String getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(String convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

}