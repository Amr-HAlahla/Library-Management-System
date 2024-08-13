CREATE TABLE author (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        birth_date DATE
);

CREATE TABLE publisher (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(100) NOT NULL,
                           established_date DATE
);

CREATE TABLE book (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      title VARCHAR(100) NOT NULL,
                      isbn VARCHAR(20) NOT NULL UNIQUE,
                      published_date DATE,
                      pages INT,
                      author_id BIGINT,
                      publisher_id BIGINT,
                      CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES author(id),
                      CONSTRAINT fk_publisher FOREIGN KEY (publisher_id) REFERENCES publisher(id)
);
