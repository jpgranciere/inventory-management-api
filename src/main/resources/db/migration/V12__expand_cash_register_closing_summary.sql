ALTER TABLE cash_register_closing
    ADD COLUMN opening_balance NUMERIC(19, 2),
    ADD COLUMN supplies NUMERIC(19, 2),
    ADD COLUMN withdrawals NUMERIC(19, 2),
    ADD COLUMN expected_cash_balance NUMERIC(19, 2);


UPDATE cash_register_closing closing
SET
    opening_balance = COALESCE(register.opening_balance, 0),

    supplies = COALESCE(transactions.supplies, 0),

    withdrawals = COALESCE(transactions.withdrawals, 0),

    expected_cash_balance =
          COALESCE(register.opening_balance, 0)
        + COALESCE(closing.total_cash, 0)
        + COALESCE(transactions.supplies, 0)
        - COALESCE(transactions.withdrawals, 0)

FROM cash_register register

LEFT JOIN (
    SELECT
        cash_register_id,

        SUM(
            CASE
                WHEN type = 'SUPPLY' THEN amount
                ELSE 0
            END
        ) AS supplies,

        SUM(
            CASE
                WHEN type = 'WITHDRAWAL' THEN amount
                ELSE 0
            END
        ) AS withdrawals

    FROM cash_transaction
    GROUP BY cash_register_id

) transactions
    ON transactions.cash_register_id = register.id

WHERE closing.cash_register_id = register.id;


ALTER TABLE cash_register_closing
    ALTER COLUMN opening_balance SET NOT NULL,
    ALTER COLUMN supplies SET NOT NULL,
    ALTER COLUMN withdrawals SET NOT NULL,
    ALTER COLUMN expected_cash_balance SET NOT NULL;