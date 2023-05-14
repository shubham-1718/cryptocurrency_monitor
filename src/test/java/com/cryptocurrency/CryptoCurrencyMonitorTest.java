package com.cryptocurrency;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class CryptoCurrencyMonitorTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;

    private CryptoCurrencyMonitor cryptoCurrencyMonitor;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        cryptoCurrencyMonitor = new CryptoCurrencyMonitor();
    }

    @Test
    @DisplayName("Test to return price for bitcoin cryptocurrency")
    public void testDoGet_BitcoinPrice() throws Exception {

        String selectedCryptoCurrency = "bitcoin";
        String pattern = "€\\s\\d+(\\.\\d+)?";

        when(request.getParameter("cryptocurrency")).thenReturn(selectedCryptoCurrency);
        when(request.getParameter("ipAddress")).thenReturn("90.32.29.30");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        cryptoCurrencyMonitor.doGet(request, response);
        writer.flush();

        verify(response).setContentType("text/html;charset=UTF-8");
        assertTrue(stringWriter.toString().trim().matches(pattern));
    }

    @Test
    @DisplayName("Test to return price for ethereum cryptocurrency")
    public void testDoGet_EthereumPrice() throws Exception {

        String selectedCryptoCurrency = "ethereum";
        String pattern = "€\\s\\d+(\\.\\d+)?";

        when(request.getParameter("cryptocurrency")).thenReturn(selectedCryptoCurrency);
        when(request.getParameter("ipAddress")).thenReturn("90.32.29.30");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        cryptoCurrencyMonitor.doGet(request, response);
        writer.flush();

        verify(response).setContentType("text/html;charset=UTF-8");
        assertTrue(stringWriter.toString().trim().matches(pattern));
    }

    @Test
    @DisplayName("Test to return price for tether cryptocurrency")
    public void testDoGet_TetherPrice() throws Exception {

        String selectedCryptoCurrency = "tether";
        String pattern = "€\\s\\d+(\\.\\d+)?";

        when(request.getParameter("cryptocurrency")).thenReturn(selectedCryptoCurrency);
        when(request.getParameter("ipAddress")).thenReturn("90.32.29.30");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        cryptoCurrencyMonitor.doGet(request, response);
        writer.flush();

        verify(response).setContentType("text/html;charset=UTF-8");
        assertTrue(stringWriter.toString().trim().matches(pattern));
    }

    @Test
    @DisplayName("Test to return price for dogeCoin cryptocurrency")
    public void testDoGet_DogeCoinPrice() throws Exception {

        String selectedCryptoCurrency = "dogecoin";
        String pattern = "€\\s\\d+(\\.\\d+)?";

        when(request.getParameter("cryptocurrency")).thenReturn(selectedCryptoCurrency);
        when(request.getParameter("ipAddress")).thenReturn("90.32.29.30");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        cryptoCurrencyMonitor.doGet(request, response);
        writer.flush();

        verify(response).setContentType("text/html;charset=UTF-8");
        assertTrue(stringWriter.toString().trim().matches(pattern));
    }

    @Test
    @DisplayName("Test to return price for polygon cryptocurrency")
    public void testDoGet_PolygonPrice() throws Exception {

        String selectedCryptoCurrency = "polygon";
        String pattern = "€\\s\\d+(\\.\\d+)?";

        when(request.getParameter("cryptocurrency")).thenReturn(selectedCryptoCurrency);
        when(request.getParameter("ipAddress")).thenReturn("90.32.29.30");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        cryptoCurrencyMonitor.doGet(request, response);
        writer.flush();

        verify(response).setContentType("text/html;charset=UTF-8");
        assertTrue(stringWriter.toString().trim().matches(pattern));
    }

    @Test
    @DisplayName("Test to return price in INR Indian code for bitcoin cryptocurrency")
    public void testDoGet_BitcoinPriceCodeINR() throws Exception {

        String selectedCryptoCurrency = "bitcoin";
        String pattern = "₹\\s\\d+(\\.\\d+)?";

        when(request.getParameter("cryptocurrency")).thenReturn(selectedCryptoCurrency);
        when(request.getParameter("ipAddress")).thenReturn("49.36.239.30");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        cryptoCurrencyMonitor.doGet(request, response);
        writer.flush();

        verify(response).setContentType("text/html;charset=UTF-8");
        assertTrue(stringWriter.toString().trim().matches(pattern));
    }

    @Test
    @DisplayName("Test to return price in KRW Korean code for polygon cryptocurrency")
    public void testDoGet_PolygonPriceKRWCode() throws Exception {

        String selectedCryptoCurrency = "polygon";
        String pattern = "₩\\s\\d+(\\.\\d+)?";

        when(request.getParameter("cryptocurrency")).thenReturn(selectedCryptoCurrency);
        when(request.getParameter("ipAddress")).thenReturn("123.36.239.30");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        cryptoCurrencyMonitor.doGet(request, response);
        writer.flush();

        verify(response).setContentType("text/html;charset=UTF-8");
        assertTrue(stringWriter.toString().trim().matches(pattern));
    }

    @Test
    @DisplayName("Test to return price in $ Dollar code for bitcoin cryptocurrency")
    public void testDoGet_BitcoinPriceDollarCode() throws Exception {

        String selectedCryptoCurrency = "bitcoin";
        String pattern = "\\$\\s\\d+(\\.\\d+)?";

        when(request.getParameter("cryptocurrency")).thenReturn(selectedCryptoCurrency);
        when(request.getParameter("ipAddress")).thenReturn("21.32.29.30");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        cryptoCurrencyMonitor.doGet(request, response);
        writer.flush();

        verify(response).setContentType("text/html;charset=UTF-8");
        assertTrue(stringWriter.toString().trim().matches(pattern));
    }

    @Test
    @DisplayName("Test to return price in $ INR code for bitcoin cryptocurrency with empty ip address")
    public void testDoGet_BitcoinPriceINRCodeEmptyIpAddress() throws Exception {

        String selectedCryptoCurrency = "bitcoin";
        String pattern = "₹\\s\\d+(\\.\\d+)?";

        when(request.getParameter("cryptocurrency")).thenReturn(selectedCryptoCurrency);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        cryptoCurrencyMonitor.doGet(request, response);
        writer.flush();

        verify(response).setContentType("text/html;charset=UTF-8");
        assertTrue(stringWriter.toString().trim().matches(pattern));
    }

    @Test
    @DisplayName("Test to return price for bitcoin cryptocurrency with InValid Ip address")
    public void testDoGet_BitcoinPriceInValidIpAddress() throws Exception {

        String selectedCryptoCurrency = "bitcoin";
        String pattern = "₹\\s\\d+(\\.\\d+)?";

        when(request.getParameter("cryptocurrency")).thenReturn(selectedCryptoCurrency);
        when(request.getParameter("ipAddress")).thenReturn("3456.251.8.0");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        cryptoCurrencyMonitor.doGet(request, response);
        writer.flush();

        // Assert
        verify(response).setContentType("text/html;charset=UTF-8");
        System.out.println(stringWriter.toString().trim());
        assertFalse(stringWriter.toString().trim().matches(pattern));
    }

    @Test
    @DisplayName("Test to return price for bitcoin cryptocurrency with Success")
    public void testGetCryptoCurrencyPrice_Success() throws Exception {
        String selectedCryptoCurrency = "bitcoin";
        String pattern = "^\\d+(\\.\\d+)?$";

        when(request.getParameter("cryptocurrency")).thenReturn(selectedCryptoCurrency);
        when(request.getParameter("ipAddress")).thenReturn("90.32.29.30");

        // Invoke the method under test
        String result = cryptoCurrencyMonitor.getCryptoCurrencyPrice(request, selectedCryptoCurrency);
        System.out.println(result);

        assertTrue(result.matches(pattern));
    }

    @Test
    @DisplayName("Test to return price for bitcoin cryptocurrency but with JSON Exception")
    public void testGetCryptoCurrencyPrice_JSONException() throws Exception {
        String selectedCryptoCurrency = "bitcoin";

        when(request.getParameter("cryptocurrency")).thenReturn(selectedCryptoCurrency);
        when(request.getParameter("ipAddress")).thenReturn("90.32.29.30");

        JSONObject jsonObject = mock(JSONObject.class);

        // Mock the creation of JSONObject and throw JSONException
        Mockito.doThrow(new JSONException("JSON parsing error")).when(jsonObject).getJSONObject("data");

        String result = cryptoCurrencyMonitor.getCryptoCurrencyPrice(request, selectedCryptoCurrency);

        assertEquals("Error: JSON parsing error", result);
    }

    @Test
    @DisplayName("Test to return currency details for bitcoin cryptocurrency")
    public void testGetCryptoCurrencyResponse() {
        // Prepare test data
        String selectedCryptoCurrency = "bitcoin";

        String actualResponse = cryptoCurrencyMonitor.getCryptoCurrencyResponse(selectedCryptoCurrency);

        JSONObject jsonObject = new JSONObject(actualResponse);
        JSONObject data = jsonObject.getJSONObject("data");
        String Id = String.valueOf(data.getString("id"));
        String symbol = String.valueOf(data.getString("symbol"));

        assertEquals(Id, selectedCryptoCurrency);
        assertEquals(symbol, "BTC");
    }

    @Test
    @DisplayName("Test to return client Ip address with XForwardedForHeader")
    public void testGetClientIpAddress_WithXForwardedForHeader() {
        // Prepare test data
        String expectedIpAddress = "90.32.29.30";
        when(request.getHeader("X-Forwarded-For")).thenReturn(expectedIpAddress);

        String actualIpAddress = cryptoCurrencyMonitor.getClientIpAddress(request);

        assertEquals(expectedIpAddress, actualIpAddress);
    }

    @Test
    @DisplayName("Test to return client Ip address without XForwardedForHeader")
    public void testGetClientIpAddress_WithoutXForwardedForHeader() {
        // Prepare test data
        String expectedIpAddress = "90.32.29.30";
        when(request.getHeader("X-Forwarded-For")).thenReturn(null);

        String actualIpAddress = cryptoCurrencyMonitor.getClientIpAddress(request);

        assertNotEquals(expectedIpAddress, actualIpAddress);
    }

    @Test
    @DisplayName("Test to return currency code for given valid ip address")
    public void testGetCurrencyCode_Success() throws IOException, JSONException {

        String ipAddress = "90.32.29.30";
        String expectedCurrencyCode = "EUR";

        String actualCurrencyCode = cryptoCurrencyMonitor.getCurrencyCode(ipAddress);

        assertEquals(actualCurrencyCode, expectedCurrencyCode);

    }

    @Test
    @DisplayName("Test to return currency code for given in-valid ip address")
    public void testGetCurrencyCode_Error() throws IOException, JSONException {
        // Prepare test data
        String expectedCurrencyCode = "Error";
        String ipAddress = "3456.251.8.0";

        String actualCurrencyCode = cryptoCurrencyMonitor.getCurrencyCode(ipAddress);

        assertEquals(expectedCurrencyCode, actualCurrencyCode);
    }

    @Test
    @DisplayName("Test to return exchange rate with base USD for USD as same currencyCode")
    public void testGetExchangeRate_Success() throws IOException, JSONException {
        // Prepare test data
        String currencyCode = "USD";
        String pattern = "^[+]?\\d*\\.\\d+$";

        double actualExchangeRate = cryptoCurrencyMonitor.getExchangeRate(currencyCode);

        assertTrue(String.valueOf(actualExchangeRate).matches(pattern));
    }

    @Test
    @DisplayName("Test to return exchange rate with base USD for EUR as different currencyCode")
    public void testGetExchangeRate_WithAnotherCurrency() throws IOException, JSONException {
        // Prepare test data
        String currencyCode = "EUR";
        String pattern = "^[+]?\\d*\\.\\d+$";

        double actualExchangeRate = cryptoCurrencyMonitor.getExchangeRate(currencyCode);

        assertTrue(String.valueOf(actualExchangeRate).matches(pattern));
    }
}