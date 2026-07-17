CREATE TABLE cash_register_closing (
    id BIGSERIAL PRIMARY KEY,
    closed_at TIMESTAMP NOT NULL,
    reference_date DATE NOT NULL,
    total_pix DECIMAL(19, 4) NOT NULL DEFAULT 0.00,
    total_cash DECIMAL(19, 4) NOT NULL DEFAULT 0.00,
    total_debit DECIMAL(19, 4) NOT NULL DEFAULT 0.00,
    total_credit DECIMAL(19, 4) NOT NULL DEFAULT 0.00,
    total_amount DECIMAL(19, 4) NOT NULL DEFAULT 0.00,
    sales_count INT NOT NULL DEFAULT 0,
    closing_status VARCHAR(50) NOT NULL
);