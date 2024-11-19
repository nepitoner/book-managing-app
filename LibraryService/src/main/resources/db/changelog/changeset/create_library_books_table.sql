CREATE TABLE IF NOT EXISTS library_books
(
    library_book_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    book_id UUID,
    is_available BOOLEAN NOT NULL,
    checkout_date TIMESTAMP,
    return_date TIMESTAMP
);

-- rollback DROP TABLE library_books;