package com.cryptocurrency;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cryptocurrency.controller.CurrencyCodeRequest;
import com.cryptocurrency.controller.ExchangeRateRequest;
import com.cryptocurrency.dao.ConversionHistoryDAO;
import com.cryptocurrency.helpers.DatabaseInitializer;
import com.cryptocurrency.model.ConversionHistoryRecord;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.cryptocurrency.controller.CryptoCurrencyRequest;
import com.cryptocurrency.helpers.StatusReport;

@WebServlet("/cryptoCurrency")
public class CryptoCurrencyMonitor extends HttpServlet {

    public static String currencySymbol = StringUtils.EMPTY;

    private ConversionHistoryDAO conversionHistoryDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            DatabaseInitializer databaseInitializer = new DatabaseInitializer();
            conversionHistoryDAO = databaseInitializer.getConversionHistoryDAO();
        } catch (ServletException e) {
            throw new ServletException("Failed to initialize the database", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String selectedCryptoCurrency = request.getParameter("cryptocurrency");
        String cryptoCurrencyPrice = getCryptoCurrencyPrice(request, selectedCryptoCurrency);

        response.setContentType("text/html;charset=UTF-8");
        if (cryptoCurrencyPrice.contains("Error"))
            response.getWriter().write("<p style=\"color: red;\">"+cryptoCurrencyPrice+"</p>");
        else
            response.getWriter().write("<p>Current unit price is</p><p>" + currencySymbol + " " + cryptoCurrencyPrice + "</p>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        doGet(request, response);
    }

    public String getCryptoCurrencyPrice(HttpServletRequest request, String selectedCryptoCurrency) throws IOException {
        String responseStr = getCryptoCurrencyResponse(selectedCryptoCurrency);
        try {
            JSONObject jsonObject = new JSONObject(responseStr);
            JSONObject data = jsonObject.getJSONObject("data");
            double priceUsd = Double.parseDouble(data.getString("priceUsd"));

            String ipAddress = request.getParameter("ipAddress");
            if (ipAddress == null || ipAddress.isEmpty()) {
                ipAddress = getClientIpAddress(request);
            }

            String currencyCode = getCurrencyCode(ipAddress);
            if (currencyCode.contains("Error")) {
                return currencyCode;
            }

            double exchangeRate = getExchangeRate(currencyCode);

            double priceLocal = priceUsd * exchangeRate;

            // Get the current date and time
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String currentDateAndTime = now.format(formatter);

            String cryptocurrencyName = selectedCryptoCurrency.substring(0, 1).toUpperCase() + selectedCryptoCurrency.substring(1);

            // Save conversion history record in the database
            ConversionHistoryRecord record = new ConversionHistoryRecord();
            record.setUserId(request.getParameter("username"));
            record.setDateTime(currentDateAndTime);
            record.setCryptocurrency(cryptocurrencyName);
            record.setConvertedAmount(currencySymbol + " " + priceLocal);
            record.setFromCurrency("USD");
            record.setToCurrency(currencyCode);

            conversionHistoryDAO.saveConversionHistory(record);

            return String.valueOf(priceLocal);
        } catch (JSONException e) {
            return e.getMessage();
        }
    }

    public String getCryptoCurrencyResponse(String selectedCryptoCurrency) {
        CryptoCurrencyRequest cryptoCurrencyRequest = new CryptoCurrencyRequest(selectedCryptoCurrency);
        StatusReport statusReport = cryptoCurrencyRequest.getStatusReport();

        return statusReport.getResponseStr();
    }

    public String getClientIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null) {
            //ipAddress = request.getRemoteAddr(); /* Commenting it, as project running on local so this will fetch local ip which is in-valid. */
            ipAddress = "49.36.239.30"; /* For now, providing a valid static ip-address. */
        }
        return ipAddress;
    }

    public String getCurrencyCode(String ipAddress) throws
            JSONException {

        currencySymbol = StringUtils.EMPTY;
        CurrencyCodeRequest currencyCodeRequest = new CurrencyCodeRequest(ipAddress);
        StatusReport statusReport = currencyCodeRequest.getStatusReport();

        if (statusReport.getResponseCode() != 200) {
            return "Error : while fetching the currency";
        }

        JSONObject jsonObject = new JSONObject(statusReport.getResponseStr());
        currencySymbol = jsonObject.getJSONObject("currency").getString("native");

        return jsonObject.getJSONObject("currency").getString("code");
    }

    double getExchangeRate(String currencyCode) throws
            JSONException {

        ExchangeRateRequest exchangeRateRequest = new ExchangeRateRequest(currencyCode);
        StatusReport statusReport = exchangeRateRequest.getStatusReport();

        JSONObject jsonObject = new JSONObject(statusReport.getResponseStr());

        return jsonObject.getJSONObject("rates").getDouble(currencyCode);
    }
}