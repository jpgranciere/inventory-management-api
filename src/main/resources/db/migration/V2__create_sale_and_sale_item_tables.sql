CREATE TABLE sale (
    id BIGSERIAL PRIMARY KEY,
    create_at TIMESTAMP NOT NULL DEFAULT NOW(),
    total NUMERIC(19, 2) NOT NULL
);

CREATE TABLE sale_item (
    id BIGSERIAL PRIMARY KEY,

    sale_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,

    quantity INT NOT NULL,
    unit_price NUMERIC(19, 2) NOT NULL,
    sub_total NUMERIC(19, 2) NOT NULL,

    CONSTRAINT fk_sale_item_sale
        FOREIGN KEY (sale_id) REFERENCES sale(id),

    CONSTRAINT fk_sale_item_product
        FOREIGN KEY (product_id) REFERENCES product(id)
);