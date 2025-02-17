package com.supermarket.application.services;

import com.supermarket.application.models.DemandPrediction;
import com.supermarket.application.models.Sale;
import com.supermarket.data.dao.SalesDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryForecastService {
    private final SalesDAO salesDAO;

    public InventoryForecastService() {
        this.salesDAO = new SalesDAO();
    }

    public List<DemandPrediction> predictDemand() throws SQLException {
        List<DemandPrediction> predictions = new ArrayList<>();
        List<Sale> sales = salesDAO.getAllSales();
        // Use a forecasting model (e.g., ARIMA or Prophet) to predict demand
        // Example: predictions.add(new DemandPrediction(...));
        return predictions;
    }
}