package com.cryptocurrency.helpers;

import com.cryptocurrency.dao.ConversionHistoryDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DatabaseInitializerTest {
    private DatabaseInitializer databaseInitializer;
    @BeforeEach
    void setup() throws ServletException {
        MockitoAnnotations.openMocks(this);
        databaseInitializer = new DatabaseInitializer();
    }
    @Test
    @DisplayName("Test the constructor to verify database configuration")
    void testConstructor() throws ServletException {
        ConversionHistoryDAO conversionHistoryDAO = (new DatabaseInitializer()).getConversionHistoryDAO();
        assertEquals("root", conversionHistoryDAO.getDbPassword());
        assertEquals("root", conversionHistoryDAO.getDbUsername());
        assertEquals("jdbc:mysql://localhost:3306/cryptocurrencytest", conversionHistoryDAO.getDbUrl());
    }
    @Test
    @DisplayName("Test to return character encoding and content type")
    void testLoadDatabaseProperties_Success() throws ServletException {

        System.setProperty("spring.datasource.url", "jdbc:mysql://localhost:3306/cryptocurrencytest");
        System.setProperty("spring.datasource.username", "root");
        System.setProperty("spring.datasource.password", "root");
        System.setProperty("spring.datasource.driver-class-name", "com.mysql.jdbc.Driver");

        databaseInitializer.loadDatabaseProperties();

        ConversionHistoryDAO dao = databaseInitializer.getConversionHistoryDAO();
        assertEquals("jdbc:mysql://localhost:3306/cryptocurrencytest", dao.getDbUrl());
        assertEquals("root", dao.getDbUsername());
        assertEquals("root", dao.getDbPassword());
    }
}

