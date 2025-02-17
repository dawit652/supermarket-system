package com.supermarket.integration.reports;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.supermarket.application.models.Sale;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class PDFGenerator {
    /**
     * Generates a sales report in PDF format using iText 7.x.
     *
     * @param sales A list of sales to include in the report.
     * @throws FileNotFoundException If the file cannot be created.
     */
    public static void generateSalesReport(List<Sale> sales) throws FileNotFoundException {
        // Define the output file
        String filePath = "sales_report.pdf";
        File file = new File(filePath);

        // Create a new PDF writer
        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdfDoc = new PdfDocument(writer);

        // Create a layout document
        Document document = new Document(pdfDoc);

        // Add a title
        document.add(new Paragraph("Sales Report"));

        // Add sales data
        for (Sale sale : sales) {
            document.add(new Paragraph("Sale ID: " + sale.getId()));
            document.add(new Paragraph("Total Price: " + sale.getTotalPrice()));
            document.add(new Paragraph("Discount: " + sale.getDiscount()));
            document.add(new Paragraph("Branch ID: " + sale.getBranchId()));
            document.add(new Paragraph("-----------------------------"));
        }

        // Close the document
        document.close();
    }
}