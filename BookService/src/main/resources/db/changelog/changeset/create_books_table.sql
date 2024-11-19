CREATE TABLE IF NOT EXISTS books (
    book_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    isbn VARCHAR(20) NOT NULL UNIQUE,
    title VARCHAR(255) NOT NULL,
    genre VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    author_id UUID NOT NULL,
    FOREIGN KEY (author_id) REFERENCES authors(author_id) ON DELETE CASCADE
);

-- rollback DROP TABLE books;