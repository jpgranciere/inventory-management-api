CREATE TABLE cash_transaction (
    id BIGSERIAL PRIMARY KEY,
    cash_register_id BIGINT NOT NULL,
    type VARCHAR(20) NOT NULL,
    amount NUMERIC(19,2) NOT NULL,
    reason VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    created_by VARCHAR(100) NOT NULL,

    CONSTRAINT fk_cash_transaction_register
        FOREIGN KEY (cash_register_id)
        REFERENCES cash_register(id),

    CONSTRAINT chk_cash_transaction_type
        CHECK (type IN ('WITHDRAWAL', 'SUPPLY')),

    CONSTRAINT chk_cash_transaction_amount
        CHECK (amount > 0)
);

CREATE INDEX idx_cash_transaction_register
    ON cash_transaction(cash_register_id);