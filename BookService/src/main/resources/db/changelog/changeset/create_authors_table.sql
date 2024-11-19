CREATE TABLE IF NOT EXISTS authors
(
    author_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    pen_name   VARCHAR(255)
);

-- rollback DROP TABLE authors;