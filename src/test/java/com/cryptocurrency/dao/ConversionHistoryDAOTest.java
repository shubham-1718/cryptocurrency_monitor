package com.cryptocurrency.dao;

import com.cryptocurrency.helpers.DatabaseInitializer;
import com.cryptocurrency.model.ConversionHistoryRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.sql.*;
import java.util.List;

public class ConversionHistoryDAOTest {
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private ConversionHistoryDAO conversionHistoryDAO;

    @BeforeEach
    public void setUp() throws ServletException {
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        try {
            DatabaseInitializer databaseInitializer = new DatabaseInitializer();
            conversionHistoryDAO = databaseInitializer.getConversionHistoryDAO();
        } catch (ServletException e) {
            throw new ServletException("Failed to initialize the database", e);
        }
    }
    @Test
    @DisplayName("Test to check the constructor")
    public void testConstructor() {
        ConversionHistoryDAO actualConversionHistoryDAO = new ConversionHistoryDAO("jdbc:mysql://localhost:3306/cryptocurrencytest", "root",
                "root");

        assertEquals("root", actualConversionHistoryDAO.getDbPassword());
        assertEquals("jdbc:mysql://localhost:3306/cryptocurrencytest", actualConversionHistoryDAO.getDbUrl());
        assertEquals("root", actualConversionHistoryDAO.getDbUsername());
    }
    @Test
    @DisplayName("Test to return status of conversion history")
    public void testSaveConversionHistory() throws SQLException {

        ConversionHistoryRecord testRecord = new ConversionHistoryRecord("testUser", "2023-05-15", "Bitcoin", "1.5", "USD", "EUR");

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        int insertStatus = conversionHistoryDAO.saveConversionHistory(testRecord);

        assertTrue(insertStatus>0);
        assertEquals(1, insertStatus);
    }
    @Test
    @DisplayName("Test to save and check status of conversion history")
    public void testStatusOfConversionHistory() {
        ConversionHistoryDAO conversionHistoryDAO = new ConversionHistoryDAO("jdbc:mysql://localhost:3306/cryptocurrencytest", "root",
                "root");
        assertEquals(1, conversionHistoryDAO
                .saveConversionHistory(new ConversionHistoryRecord("user123", "2020-03-01", "GBP", "10.90", "USD", "EUR")));
    }
    @Test
    @DisplayName("Test to return the conversion history for given userId")
    public void testGetConversionHistoryByUserId() throws SQLException {

        String testUserId = "testUser";
        ConversionHistoryRecord expectedRecord = new ConversionHistoryRecord(testUserId, "2023-05-15", "Bitcoin", "1.5", "USD", "EUR");

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("conversionDate")).thenReturn("2023-05-15");
        when(mockResultSet.getString("cryptocurrency")).thenReturn("Bitcoin");
        when(mockResultSet.getString("convertedAmount")).thenReturn("1.5");
        when(mockResultSet.getString("fromCurrency")).thenReturn("USD");
        when(mockResultSet.getString("toCurrency")).thenReturn("EUR");

        List<ConversionHistoryRecord> conversionHistory = conversionHistoryDAO.getConversionHistoryByUserId(testUserId);

        assertNotNull(conversionHistory);
        assertTrue(conversionHistory.size()>0);
        ConversionHistoryRecord actualRecord = conversionHistory.get(0);
        assertEquals(expectedRecord.getUserId(), actualRecord.getUserId());
        assertEquals(expectedRecord.getDateTime(), actualRecord.getDateTime());
        assertEquals(expectedRecord.getCryptocurrency(), actualRecord.getCryptocurrency());
        assertEquals(expectedRecord.getConvertedAmount(), actualRecord.getConvertedAmount());
        assertEquals(expectedRecord.getFromCurrency(), actualRecord.getFromCurrency());
        assertEquals(expectedRecord.getToCurrency(), actualRecord.getToCurrency());
    }
}

