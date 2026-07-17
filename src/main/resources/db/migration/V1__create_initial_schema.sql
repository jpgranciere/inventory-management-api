CREATE TABLE product (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    sku VARCHAR(100) NOT NULL UNIQUE,
    category VARCHAR(50) NOT NULL,
    stock_quantity INTEGER NOT NULL,
    min_stock_quantity INTEGER NOT NULL,
    status VARCHAR(50) NOT NULL,
    price DECIMAL(10,2) NOT NULL
);

CREATE TABLE stock_movement (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL,
    quantity INTEGER NOT NULL,
    movement_type VARCHAR(20) NOT NULL,
    observation VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_stock_movement_product
        FOREIGN KEY (product_id)
        REFERENCES product(id)
        ON DELETE RESTRICT
);

