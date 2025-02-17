package com.supermarket.application.services;

import com.supermarket.data.dao.SalesDAO;
import com.supermarket.data.dao.ProductDAO;
import com.supermarket.integration.reports.ExcelExporter;
import com.supermarket.integration.reports.PDFGenerator;
import com.supermarket.application.models.Sale;
import com.supermarket.application.models.Product;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ReportService {
    private final SalesDAO salesDAO = new SalesDAO();
    private final ProductDAO productDAO = new ProductDAO();

    /**
     * Generates a sales report in PDF format.
     */
    public void generateSalesReport() throws SQLException, IOException {
        List<Sale> sales = salesDAO.getAllSales();
        PDFGenerator.generateSalesReport(sales);
    }

    /**
     * Generates an inventory report in Excel format.
     */
    public void generateInventoryReport() throws SQLException, IOException {
        List<Product> products = productDAO.getAllProducts(1); // Replace 1 with the branch ID
        ExcelExporter.exportInventoryReport(products);
    }
}