-- Insert data into the author table
INSERT INTO author (name, birth_date) VALUES ('J.K. Rowling', '1965-07-31');
INSERT INTO author (name, birth_date) VALUES ('George R.R. Martin', '1948-09-20');
INSERT INTO author (name, birth_date) VALUES ('J.R.R. Tolkien', '1892-01-03');
INSERT INTO author (name, birth_date) VALUES ('Agatha Christie', '1890-09-15');
INSERT INTO author (name, birth_date) VALUES ('Isaac Asimov', '1920-01-02');

-- Insert data into the publisher table
INSERT INTO publisher (name, established_date) VALUES ('Bloomsbury Publishing', '1986-05-26');
INSERT INTO publisher (name, established_date) VALUES ('Bantam Books', '1945-08-01');
INSERT INTO publisher (name, established_date) VALUES ('HarperCollins', '1989-08-10');
INSERT INTO publisher (name, established_date) VALUES ('Penguin Books', '1935-07-30');
INSERT INTO publisher (name, established_date) VALUES ('Gnome Press', '1948-06-01');

-- Insert data into the book table
INSERT INTO book (title, isbn, published_date, pages, author_id, publisher_id) VALUES
    ('Harry Potter and the Philosopher\'s Stone', '9780747532699', '1997-06-26', 223, 1, 1),
('A Game of Thrones', '9780553103540', '1996-08-06', 694, 2, 2),
('The Hobbit', '9780618968633', '1937-09-21', 310, 3, 3),
('Murder on the Orient Express', '9780007119318', '1934-01-01', 256, 4, 4),
('Foundation', '9780553293357', '1951-06-01', 244, 5, 5);
