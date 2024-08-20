-- Create the database and switch to it
CREATE DATABASE IF NOT EXISTS library_system;
USE library_system;

-- Create tables
CREATE TABLE IF NOT EXISTS author
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(100) NOT NULL,
    birth_date DATE
);

CREATE TABLE IF NOT EXISTS publisher
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    name             VARCHAR(100) NOT NULL,
    established_date DATE
);

CREATE TABLE IF NOT EXISTS book
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    title          VARCHAR(100) NOT NULL,
    isbn           VARCHAR(20)  NOT NULL UNIQUE,
    published_date DATE,
    pages          INT,
    author_id      BIGINT,
    publisher_id   BIGINT,
    CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES author (id),
    CONSTRAINT fk_publisher FOREIGN KEY (publisher_id) REFERENCES publisher (id)
);

-- Insert data into the author table
INSERT INTO author (name, birth_date)
VALUES ('J.K. Rowling', '1965-07-31'),
       ('George R.R. Martin', '1948-09-20'),
       ('J.R.R. Tolkien', '1892-01-03'),
       ('Agatha Christie', '1890-09-15'),
       ('Isaac Asimov', '1920-01-02');

-- Insert data into the publisher table
INSERT INTO publisher (name, established_date)
VALUES ('Bloomsbury Publishing', '1986-05-26'),
       ('Bantam Books', '1945-08-01'),
       ('HarperCollins', '1989-08-10'),
       ('Penguin Books', '1935-07-30'),
       ('Gnome Press', '1948-06-01');

-- Insert data into the book table
INSERT INTO book (title, isbn, published_date, pages, author_id, publisher_id)
VALUES ('Harry Potter and the Philosopher\'s Stone', '9780747532699', '1997-06-26', 223, 1, 1),
       ('A Game of Thrones', '9780553103540', '1996-08-06', 694, 2, 2),
       ('The Hobbit', '9780618968633', '1937-09-21', 310, 3, 3),
       ('Murder on the Orient Express', '9780007119318', '1934-01-01', 256, 4, 4),
       ('Foundation', '9780553293357', '1951-06-01', 244, 5, 5);
