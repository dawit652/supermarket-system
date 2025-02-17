package com.supermarket.application.services;

import com.supermarket.data.dao.SalesDAO;
import com.supermarket.application.models.Sale;

import java.sql.SQLException;
import java.util.Date; // Use java.util.Date
import java.util.List;

public class SalesService {
    private final SalesDAO saleDAO = new SalesDAO();
    public void addSale(Sale sale) throws SQLException {
        saleDAO.addSale(sale);
    }

    public void processSale(Sale sale) throws SQLException {
        double totalPrice = sale.getTotalPrice();
        int quantity=sale.getQuantity();
        double discount = sale.getDiscount();
        double finalPrice = (totalPrice - (totalPrice * discount / 100))*quantity; // Apply discount
        sale.setTotalPrice(finalPrice);
        saleDAO.addSale(sale);
    }


    public List<Sale> getSalesForToday(int branchId) throws SQLException {
        Date date = new Date(); // Use the current date
        return saleDAO.getSalesByDate(date);
    }

    public List<Sale> getSalesByBranch(int branchId) throws SQLException {
        return saleDAO.getSalesByBranch(branchId);
    }
}