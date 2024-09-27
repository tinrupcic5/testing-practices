CREATE TABLE payments
(
    id             BIGSERIAL PRIMARY KEY,
    amount         DECIMAL(19, 4) NOT NULL,
    currency       VARCHAR(3)     NOT NULL, --  USD, EUR, KN.
    payment_date   TIMESTAMP      NOT NULL,
    status         VARCHAR(50)    NOT NULL, -- 'PENDING', 'COMPLETED', 'REJECTED' ..
    payment_method VARCHAR(20)    NOT NULL, --  'CARD', 'PAYPAL'.
    is_payed       BOOLEAN DEFAULT FALSE,
    CONSTRAINT chk_payment_status CHECK (status IN ('PENDING', 'COMPLETED', 'REJECTED'))
);
