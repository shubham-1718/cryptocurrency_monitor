package com.cryptocurrency.dao;

import com.cryptocurrency.model.ConversionHistoryRecord;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConversionHistoryDAO {
    private final String dbUrl;
    private final String dbUsername;
    private final String dbPassword;

    public ConversionHistoryDAO(String dbUrl, String dbUsername, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
    }

    public int saveConversionHistory(ConversionHistoryRecord record) {
        int insertStatus = 0;
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {
            // Check if the username already exists in the user_data table
            String checkUserQuery = "SELECT recordId FROM user_data WHERE userName = ?";
            PreparedStatement checkUserStatement = connection.prepareStatement(checkUserQuery);
            checkUserStatement.setString(1, record.getUserId());
            ResultSet userResultSet = checkUserStatement.executeQuery();

            int recordId;
            if (userResultSet.next()) {
                // Username already exists, retrieve the existing recordId
                recordId = userResultSet.getInt("recordId");
            } else {
                // Username doesn't exist, insert a new record and retrieve the generated recordId
                String insertUserQuery = "INSERT INTO user_data (userName) VALUES (?)";
                PreparedStatement insertUserStatement = connection.prepareStatement(insertUserQuery, Statement.RETURN_GENERATED_KEYS);
                insertUserStatement.setString(1, record.getUserId());
                insertUserStatement.executeUpdate();

                ResultSet generatedKeys = insertUserStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    recordId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Failed to retrieve the generated recordId.");
                }
            }
            // Insert the conversion history using the userId
            String conversionQuery = "INSERT INTO conversion_history (recordId, conversionDate, cryptocurrency, convertedAmount, fromCurrency, toCurrency) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement conversionStatement = connection.prepareStatement(conversionQuery);
            conversionStatement.setInt(1, recordId);
            conversionStatement.setString(2, record.getDateTime());
            conversionStatement.setString(3, record.getCryptocurrency());
            conversionStatement.setString(4, record.getConvertedAmount());
            conversionStatement.setString(5, record.getFromCurrency());
            conversionStatement.setString(6, record.getToCurrency());
            insertStatus = conversionStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return insertStatus;
    }

    public List < ConversionHistoryRecord > getConversionHistoryByUserId(String userId) {
        List < ConversionHistoryRecord > conversionHistory = new ArrayList < > ();
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {
            String query = "SELECT * FROM conversion_history INNER JOIN user_data ON conversion_history.recordId = user_data.recordId WHERE user_data.userName = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String conversionDate = resultSet.getString("conversionDate");
                String cryptoCurrencyName = resultSet.getString("cryptocurrency");
                String amount = resultSet.getString("convertedAmount");
                String fromCurrency = resultSet.getString("fromCurrency");
                String toCurrency = resultSet.getString("toCurrency");
                ConversionHistoryRecord record = new ConversionHistoryRecord(userId, conversionDate, cryptoCurrencyName, amount, fromCurrency, toCurrency);
                conversionHistory.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conversionHistory;
    }

    public String getDbUrl() {
        return this.dbUrl;
    }

    public String getDbUsername() {
        return this.dbUsername;
    }

    public String getDbPassword() {
        return this.dbPassword;
    }

}