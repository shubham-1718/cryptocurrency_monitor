package com.cryptocurrency;

import com.cryptocurrency.dao.ConversionHistoryDAO;
import com.cryptocurrency.helpers.DatabaseInitializer;
import com.cryptocurrency.model.ConversionHistoryRecord;
import com.google.gson.Gson;
import org.json.JSONException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/ConversionHistory")
public class ConversionHistory extends HttpServlet {
    public ConversionHistoryDAO conversionHistoryDAO;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        String userId = request.getParameter("username");
        try {
            List <ConversionHistoryRecord> conversionHistory = conversionHistoryDAO.getConversionHistoryByUserId(userId);

            // Convert conversionHistory to JSON
            Gson gson = new Gson();
            String jsonResponse = gson.toJson(conversionHistory);

            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(jsonResponse);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }
}