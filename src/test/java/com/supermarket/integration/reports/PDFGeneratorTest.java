package com.supermarket.integration.reports;

import com.supermarket.application.models.Sale;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class PDFGeneratorTest {
    public static void main(String[] args) {
        // Create sample sales data
        List<Sale> sales = new ArrayList<>();
        sales.add(new Sale(1, null, null, 100.0, 10.0, 1,1));
        sales.add(new Sale(2, null, null, 200.0, 20.0, 1,1));

        try {
            // Generate the PDF report
            PDFGenerator.generateSalesReport(sales);
            System.out.println("Sales report generated successfully.");
        } catch (Exception e) {
            System.err.println("Failed to generate sales report: " + e.getMessage());
        }
    }
}