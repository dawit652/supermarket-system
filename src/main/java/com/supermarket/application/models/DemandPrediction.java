package com.supermarket.application.models;

import java.util.Date;

public class DemandPrediction {
    private int productId;
    private Date predictionDate;
    private double predictedDemand;

    // Constructor
    public DemandPrediction(int productId, Date predictionDate, double predictedDemand) {
        this.productId = productId;
        this.predictionDate = predictionDate;
        this.predictedDemand = predictedDemand;
    }

    // Getters and Setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Date getPredictionDate() {
        return predictionDate;
    }

    public void setPredictionDate(Date predictionDate) {
        this.predictionDate = predictionDate;
    }

    public double getPredictedDemand() {
        return predictedDemand;
    }

    public void setPredictedDemand(double predictedDemand) {
        this.predictedDemand = predictedDemand;
    }
}