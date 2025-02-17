package com.supermarket.integration.reports;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.supermarket.application.models.Product;

public class ExcelExporter {
    /**
     * Exports an inventory report in Excel format.
     *
     * @param products A list of products to include in the report.
     * @throws IOException If an I/O error occurs.
     */
    public static void exportInventoryReport(List<Product> products) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Inventory Report");

        // Create header row
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Product Name");
        header.createCell(1).setCellValue("Barcode");
        header.createCell(2).setCellValue("Price");
        header.createCell(3).setCellValue("Stock");

        // Add product data
        int rowNum = 1;
        for (Product product : products) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(product.getName());
            row.createCell(1).setCellValue(product.getBarcode());
            row.createCell(2).setCellValue(product.getPrice());
            row.createCell(3).setCellValue(product.getStock());
        }

        // Write to file
        try (FileOutputStream fileOut = new FileOutputStream("inventory_report.xlsx")) {
            workbook.write(fileOut);
        }
    }
}