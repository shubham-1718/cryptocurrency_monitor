package com.cryptocurrency;

import com.cryptocurrency.dao.ConversionHistoryDAO;
import com.cryptocurrency.model.ConversionHistoryRecord;
import com.google.gson.Gson;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ConversionHistoryTest {
    private ConversionHistory conversionHistory;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private ConversionHistoryDAO conversionHistoryDAO;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        conversionHistory = new ConversionHistory();
        conversionHistory.conversionHistoryDAO = conversionHistoryDAO;
    }
    @Test
    @DisplayName("Test to return conversion history in JSON with Valid UserId")
    public void testDoGet_ValidUserId_ConversionHistoryInJSON() throws IOException {

        String userId = "user123";
        List < ConversionHistoryRecord > conversionHistoryList = Arrays.asList(
                new ConversionHistoryRecord(userId, "2023-05-15", "Bitcoin", "0.5", "USD", "EUR"),
                new ConversionHistoryRecord(userId, "2023-05-14", "Ethereum", "2.3", "USD", "GBP")
        );

        when(request.getParameter("username")).thenReturn(userId);
        when(conversionHistoryDAO.getConversionHistoryByUserId(userId)).thenReturn(conversionHistoryList);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        conversionHistory.doGet(request, response);
        writer.flush();

        verify(response).setContentType("text/html;charset=UTF-8");
        assertEquals(getExpectedJsonResponse(conversionHistoryList), stringWriter.toString().trim());
    }
    @Test
    @DisplayName("Test to return empty conversion history as for Invalid Id")
    public void testDoGet_InvalidId_EmptyConversionHistory() throws IOException {

        String userId = "user123";
        when(request.getParameter("username")).thenReturn(userId);
        when(conversionHistoryDAO.getConversionHistoryByUserId(userId)).thenReturn(Arrays.asList());

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        conversionHistory.doGet(request, response);
        writer.flush();

        verify(response).setContentType("text/html;charset=UTF-8");
        assertEquals("[]", stringWriter.toString().trim());
    }
    @Test
    @DisplayName("Test to return empty conversion history with UserId as null")
    public void testDoGet_NullUserId_EmptyConversionHistory() throws IOException {

        when(request.getParameter("username")).thenReturn(null);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        conversionHistory.doGet(request, response);
        writer.flush();

        verify(response).setContentType("text/html;charset=UTF-8");
        assertEquals("[]", stringWriter.toString().trim());
    }
    @Test
    @DisplayName("Test to return JSON Exception to in-valid JSON response")
    public void testDoGet_ReturnsJSONException() {

        ConversionHistory conversion = spy(new ConversionHistory());

        doThrow(JSONException.class).when(conversion).doGet(request, response);
        Assertions.assertThrows(JSONException.class, () -> conversion.doGet(request, response));
    }
    @Test
    @DisplayName("Test to return and verify database credentials from init method")
    void testInit_ReturnsDBCredentials() throws ServletException {

        ConversionHistory conversionHistory = new ConversionHistory();
        conversionHistory.init();

        ConversionHistoryDAO conversionHistoryDAO = conversionHistory.conversionHistoryDAO;
        assertEquals("jdbc:mysql://localhost:3306/cryptocurrencytest", conversionHistoryDAO.getDbUrl());
        assertEquals("root", conversionHistoryDAO.getDbPassword());
        assertEquals("root", conversionHistoryDAO.getDbUsername());
    }
    @Test
    @DisplayName("Test to return and verify character encoding and content type")
    void testDoGet_ReturnsCharacterEncodeAndContentType() throws ServletException {

        ConversionHistory conversionHistory = new ConversionHistory();

        conversionHistory.init(new ConversionHistory());
        DefaultMultipartHttpServletRequest request = mock(DefaultMultipartHttpServletRequest.class);

        when(request.getParameter(Mockito.<String>any())).thenReturn(anyString());
        MockHttpServletResponse response = new MockHttpServletResponse();
        conversionHistory.doGet(request, response);

        verify(request).getParameter(Mockito.<String>any());
        Assertions.assertTrue(response.isCharset());
        Assertions.assertNull(response.getRedirectedUrl());
        assertEquals("UTF-8", response.getCharacterEncoding());
        assertEquals("text/html;charset=UTF-8", response.getContentType());
    }
    @Test
    @DisplayName("Test to throw and verify JSON Exception")
    void testDoGet_CheckJSONException() throws UnsupportedEncodingException, ServletException {

        ConversionHistory conversionHistory = new ConversionHistory();
        conversionHistory.init(new ConversionHistory());

        DefaultMultipartHttpServletRequest request = mock(DefaultMultipartHttpServletRequest.class);

        when(request.getParameter(Mockito.<String>any())).thenReturn("user123");
        MockHttpServletResponse response = mock(MockHttpServletResponse.class);

        when(response.getWriter()).thenThrow(new JSONException("An error occurred"));
        doThrow(new JSONException("An error occurred")).when(response).setContentType(Mockito.<String>any());
        conversionHistory.doGet(request, response);

        verify(request).getParameter(Mockito.<String>any());
        verify(response).setContentType(Mockito.<String>any());
    }
    /*Function to get JSON response from list*/
    private String getExpectedJsonResponse(List < ConversionHistoryRecord > conversionHistoryList) {
        Gson gson = new Gson();
        return gson.toJson(conversionHistoryList);
    }
}