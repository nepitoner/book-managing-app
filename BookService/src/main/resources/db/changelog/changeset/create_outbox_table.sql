CREATE TABLE IF NOT EXISTS outbox (
    outbox_id SERIAL PRIMARY KEY,
    book_id UUID NOT NULL
);

-- rollback DROP TABLE outbox;