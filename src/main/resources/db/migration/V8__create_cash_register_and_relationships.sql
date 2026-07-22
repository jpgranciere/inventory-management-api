CREATE TABLE cash_register
(
    id BIGSERIAL PRIMARY KEY,

    cash_register_status VARCHAR(20) NOT NULL,

    opened_at TIMESTAMP NOT NULL,

    opening_balance DECIMAL(19, 2) NOT NULL DEFAULT 0,

    closed_at TIMESTAMP NULL,

    legacy_closing_id BIGINT NULL,
    legacy_sale_date DATE NULL,

    CONSTRAINT chk_cash_register_status
        CHECK (cash_register_status IN ('OPEN', 'CLOSED')),

    CONSTRAINT chk_cash_register_opening_balance
        CHECK (opening_balance >= 0),

    CONSTRAINT chk_cash_register_closed_state
        CHECK (
            (cash_register_status = 'OPEN' AND closed_at IS NULL)
            OR
            (cash_register_status = 'CLOSED' AND closed_at IS NOT NULL)
        )
);


ALTER TABLE cash_register_closing
    ADD COLUMN cash_register_id BIGINT;

ALTER TABLE sale
    ADD COLUMN cash_register_id BIGINT;


INSERT INTO cash_register
(
    cash_register_status,
    opened_at,
    opening_balance,
    closed_at,
    legacy_closing_id
)
SELECT
    'CLOSED',
    reference_date::timestamp,
    0.00,
    COALESCE(closed_at, reference_date::timestamp),
    id
FROM cash_register_closing;


UPDATE cash_register_closing closing
SET cash_register_id = register.id
FROM cash_register register
WHERE register.legacy_closing_id = closing.id;


UPDATE sale sale_record
SET cash_register_id = closing.cash_register_id
FROM cash_register_closing closing
WHERE sale_record.cash_register_id IS NULL
  AND sale_record.created_at >= closing.reference_date::timestamp
  AND sale_record.created_at <
      closing.reference_date::timestamp + INTERVAL '1 day';


INSERT INTO cash_register
(
    cash_register_status,
    opened_at,
    opening_balance,
    closed_at,
    legacy_sale_date
)
SELECT
    'CLOSED',
    created_at::date::timestamp,
    0.00,
    MAX(created_at),
    created_at::date
FROM sale
WHERE cash_register_id IS NULL
GROUP BY created_at::date;


UPDATE sale sale_record
SET cash_register_id = register.id
FROM cash_register register
WHERE sale_record.cash_register_id IS NULL
  AND register.legacy_sale_date = sale_record.created_at::date;


ALTER TABLE cash_register_closing
    ALTER COLUMN cash_register_id SET NOT NULL;

ALTER TABLE sale
    ALTER COLUMN cash_register_id SET NOT NULL;


ALTER TABLE cash_register_closing
    ADD CONSTRAINT fk_cash_register_closing_cash_register
        FOREIGN KEY (cash_register_id)
        REFERENCES cash_register (id);

ALTER TABLE sale
    ADD CONSTRAINT fk_sale_cash_register
        FOREIGN KEY (cash_register_id)
        REFERENCES cash_register (id);


ALTER TABLE cash_register_closing
    ADD CONSTRAINT uk_cash_register_closing_register
        UNIQUE (cash_register_id);


CREATE UNIQUE INDEX uk_cash_register_single_open
    ON cash_register ((1))
    WHERE cash_register_status = 'OPEN';


CREATE INDEX idx_sale_cash_register_id
    ON sale (cash_register_id);

CREATE INDEX idx_cash_register_status
    ON cash_register (cash_register_status);

CREATE INDEX idx_cash_register_opened_at
    ON cash_register (opened_at);

CREATE INDEX idx_cash_register_closing_reference_date
    ON cash_register_closing (reference_date);


ALTER TABLE cash_register_closing
    DROP COLUMN IF EXISTS closing_status;


ALTER TABLE cash_register
    DROP COLUMN legacy_closing_id;

ALTER TABLE cash_register
    DROP COLUMN legacy_sale_date;