# Library Management System

## Overview

This project is a **Library Management System** built using **Spring Boot**. It demonstrates the use of advanced features in Spring Data JPA, including Specifications for dynamic query generation, entity relationships, and RESTful web services. The project also integrates **JWT token authentication and authorization** to manage user access and roles. It provides a comprehensive set of features to manage books, authors, and publishers in a library.

## Technologies Used

- **Java 22**
- **Spring Boot**
- **Spring Data JPA**
- **Spring Web (RESTful APIs)**
- **Jakarta Specifications**
- **Hibernate**
- **MySQL** (or any other preferred relational database)
- **Maven** (for dependency management)
- **JWT** (for authentication and authorization)

## Features

- **Book Management**: CRUD operations for books.
- **Author Management**: CRUD operations for authors.
- **Publisher Management**: CRUD operations for publishers.
- **Dynamic Queries**: Use of Spring Data JPA Specifications to create dynamic queries.
- **Publisher-Book Association**: Manage the relationship between publishers and the books they have published.
- **RESTful APIs**: Expose data and operations via RESTful endpoints.
- **JWT Authentication**: Secure endpoints with JWT tokens.
- **Role-Based Access Control**: Manage access to resources based on user roles.
- **Signup and Login**: Endpoints for user registration and authentication.

## Getting Started

### Prerequisites

Ensure you have the following installed:

- **Java 22**
- **Maven**
- **MySQL** (or any other relational database)

### Installation

1. **Clone the repository:**

   ```bash
   git clone https://github.com/your-username/library-management-system.git
   cd library-management-system
   ```
  
2. **Configure the Database:**

   Update the `application.properties` file in the `src/main/resources` directory with your database details:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/library_system
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

> **Note:** There is an SQL script file named `database-setup.sql` in the repository that creates the database and inserts some demo data. You can use this file to set up the database by executing it inside MySQL Workbench or any other preferred application for your SQL database type.

3. **Build the Project:**

  Use Maven to build the project:
  ```bash
  mvn clean install
  ```

4. **Run the Application:**

  ```bash
  mvn spring-boot:run
  ```

## Using the Application

### Authentication

Before accessing any endpoints, you need to authenticate.

- **Signup:** Create a new user account by sending a POST request to `/signup`.
- **Login:** Obtain a JWT token by sending a POST request to `/login` with your credentials.
- Alternatively, you can use the pre-existing super admin user inserted into the database when the application starts. Use this super admin's token to access the endpoints.

### Examples of Endpoints

#### Book Management
- **GET** `/books` - Retrieve all books.
- **POST** `/books` - Add a new book.
- **PUT** `/books/{id}` - Update an existing book.
- **DELETE** `/books/{id}` - Delete a book.

#### Author Management
- **GET** `/authors` - Retrieve all authors.
- **POST** `/authors` - Add a new author.
- **PUT** `/authors/{id}` - Update an existing author.
- **DELETE** `/authors/{id}` - Delete an author.

#### Publisher Management
- **GET** `/publishers` - Retrieve all publishers.
- **POST** `/publishers` - Add a new publisher.
- **PUT** `/publishers/{id}` - Update an existing publisher.
- **DELETE** `/publishers/{id}` - Delete a publisher.
- **GET** `/publishers/with-books-count?count={count}` - Retrieve publishers with exactly `{count}` books published.

> **Note:** All the endpoint paths listed above should be prefixed with the base URL `localhost:8080/api/`. For example, to access the endpoint to retrieve all books, use `http://localhost:8080/api/books`.

## Advanced Features

### Dynamic Queries with Specifications

This project demonstrates the use of Spring Data JPA Specifications to create dynamic and type-safe queries. For example, the `/publishers/with-books-count` endpoint uses a specification to filter publishers based on the exact number of books they have published.

> **Note:** All endpoint paths should be prefixed with the base URL `localhost:8080/api/specifications`. For instance, to access the `/publishers/with-books-count` endpoint, use `http://localhost:8080/api/specifications/publishers/with-books-count?count={count}`.

