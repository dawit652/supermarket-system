package com.supermarket.application.services;

import com.supermarket.application.models.Product;
import com.supermarket.data.dao.ProductDAO;
import java.sql.SQLException;
import java.util.List;

public class ProductService {
    private final ProductDAO productDAO;

    public ProductService() {
        this.productDAO = new ProductDAO();
    }

    // Add a new product with validation
    public void addProduct(Product product) throws SQLException, IllegalArgumentException {
        if (product.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0.");
        }
        if (product.getStock() < 0) {
            throw new IllegalArgumentException("Stock cannot be negative.");
        }
        productDAO.addProduct(product);
    }


    // Get all products
    public List<Product> getAllProducts(int branchId) throws SQLException {
        return productDAO.getAllProducts(branchId);
    }

    // Update a product
    public void updateProduct(Product product) throws SQLException, IllegalArgumentException {
        if (product.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0.");
        }
        if (product.getStock() < 0) {
            throw new IllegalArgumentException("Stock cannot be negative.");
        }
        productDAO.updateProduct(product);
    }

    // Delete a product
    public void deleteProduct(int id) throws SQLException {
        productDAO.deleteProduct(id);
    }



}