-- Creating the 'products' table
CREATE TABLE products (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    barcode TEXT UNIQUE NOT NULL,
    price REAL NOT NULL,
    stock INTEGER NOT NULL
);

-- Creating the 'sales' table
CREATE TABLE sales (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    product_id INTEGER,
    quantity INTEGER,
    total_price REAL,
    sale_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- Creating the 'inventory' table
CREATE TABLE inventory (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    product_id INTEGER,
    stock_level INTEGER,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- Insert some initial products (optional)
INSERT INTO products (name, barcode, price, stock) VALUES ('Product A', '123456', 10.50, 100);
INSERT INTO products (name, barcode, price, stock) VALUES ('Product B', '654321', 15.75, 50);
