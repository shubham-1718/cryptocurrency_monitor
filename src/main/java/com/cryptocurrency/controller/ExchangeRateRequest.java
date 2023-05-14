package com.cryptocurrency.controller;

import com.cryptocurrency.helpers.StatusReport;
import com.cryptocurrency.helpers.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class ExchangeRateRequest {

    StatusReport statusReport = new StatusReport();
    int responseCode = -1;
    URL url;
    HttpURLConnection conn;
    StringBuilder responseStrBuilder = new StringBuilder();
    BufferedReader br = null;

    public ExchangeRateRequest(String currencyCode) {

        try {
            url = new URL("https://openexchangerates.org/api/latest.json?app_id=" + StringUtils.EXCHANGE_RATE_API_KEY + "&base=USD&symbols=" + currencyCode);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            System.out.println("Error creating restURL object");
            e.printStackTrace();
            statusReport.setStatus(false);
            return;
        }

        try {
            conn = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("Error in openConnection for restURL");
            e.printStackTrace();
            statusReport.setStatus(false);
            return;
        }

        try {
            conn.setRequestMethod("GET");
        } catch (ProtocolException e) {
            // TODO Auto-generated catch block
            System.out.println("Error in setRequestMethod");
            e.printStackTrace();
            statusReport.setStatus(false);
            return;
        }

        conn.setRequestProperty("Accept", "application/json");

        try {
            responseCode = conn.getResponseCode();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            statusReport.setStatus(false);
            return;
        }

        try {
            br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("Error in invoking rets API - getInputStream");
            e.printStackTrace();
            statusReport.setStatus(false);
            statusReport.setResponseCode(responseCode);
            statusReport.setResponseStr(responseStrBuilder.toString());
            return;
        }

        String readLine = null;

        try {
            while ((readLine = br.readLine()) != null)
                responseStrBuilder.append(readLine);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("Error in reading rets API Response");
            e.printStackTrace();
            statusReport.setStatus(false);
            statusReport.setResponseCode(responseCode);
            statusReport.setResponseStr(responseStrBuilder.toString());
            return;
        }

        statusReport.setStatus(true);
        statusReport.setResponseCode(responseCode);
        statusReport.setResponseStr(responseStrBuilder.toString());

        conn.disconnect();

    }

    public StatusReport getStatusReport() {
        return statusReport;
    }

    public void setStatusReport(StatusReport statusReport) {
        this.statusReport = statusReport;
    }

}