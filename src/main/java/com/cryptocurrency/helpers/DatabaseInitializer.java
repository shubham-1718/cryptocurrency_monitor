package com.cryptocurrency.helpers;

import com.cryptocurrency.dao.ConversionHistoryDAO;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Properties;

public class DatabaseInitializer {
    private ConversionHistoryDAO conversionHistoryDAO;
    public DatabaseInitializer() throws ServletException {
        loadDatabaseProperties();
    }

    public void loadDatabaseProperties() throws ServletException {
        Properties props = new Properties();
        try {
            props.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
            String dbUrl = props.getProperty("spring.datasource.url");
            String dbUsername = props.getProperty("spring.datasource.username");
            String dbPassword = props.getProperty("spring.datasource.password");
            Class.forName(props.getProperty("spring.datasource.driver-class-name"));

            conversionHistoryDAO = new ConversionHistoryDAO(dbUrl, dbUsername, dbPassword);
        } catch (IOException e) {
            throw new ServletException("Failed to load database properties", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ConversionHistoryDAO getConversionHistoryDAO() {
        return conversionHistoryDAO;
    }
}