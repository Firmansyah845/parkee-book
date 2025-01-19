# Technical Assessment - Library Management REST API

## Overview
This project is a **RESTful API** built using **Java Spring Boot** to manage library operations for administrators. It simplifies the process of managing books, borrowers, and borrowing records, ensuring that books are returned on time and providing clear tracking of overdue returns.

---

## Features
1. **Book Management**:
    - Add new books to the library.

2. **Borrower Management**:
    - Register new borrowers.

3. **Borrowing and Returning Books**:
    - Borrowers can borrow books.
    - Borrowers can return books.
    - Automatically calculate late days for overdue returns.

4. **Admin Features**:
    - Track overdue books.
    - View details of borrowing records.

---

## Technology Stack
- **Java**: Core programming language.
- **Spring Boot**: Framework for building REST APIs.
- **H2 Database**: In-memory database for development and testing.
- **Spring Data JPA**: Simplifies data access.
- **Lombok**: Reduces boilerplate code.
- **Maven**: Build tool and dependency manager.

---

## API Endpoints
### **Book Endpoints**
| Method | Endpoint       | Description          |
|--------|----------------|----------------------|
| POST   | `/api/books`   | Add a new book.      |

### **Borrower Endpoints**
| Method | Endpoint          | Description                |
|--------|-------------------|----------------------------|
| POST   | `/api/borrowers`  | Register a new borrower.   |

### **Borrow Record Endpoints**
| Method | Endpoint              | Description                |
|--------|-----------------------|----------------------------|
| POST   | `/api/borrow/borrow`  | Borrow a book.             |
| POST   | `/api/borrow/return`  | Return a book.             |
| GET    | `/api/borrow/records` | Get all borrowing records. |
| GET    | `/api/borrow/overdue` | Get all overdue records.   |

---

## Database Design (DDL)
```sql
CREATE DATABASE parkee_book;

USE parkee_book;

-- Tabel untuk menyimpan data buku
CREATE TABLE books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

SELECT * FROM books;


-- Tabel untuk menyimpan data peminjam
CREATE TABLE borrowers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

SELECT * FROM borrowers;

-- Tabel untuk menyimpan data peminjaman buku
CREATE TABLE borrow_records (
    id INT AUTO_INCREMENT PRIMARY KEY,
    book_id INT NOT NULL,
    borrower_id INT NOT NULL,
    borrow_date DATE NOT NULL,
    return_date DATE DEFAULT NULL,
    due_date DATE NOT NULL,
    returned BOOLEAN DEFAULT FALSE,
    late_days INT GENERATED ALWAYS AS (
        CASE
        WHEN return_date IS NOT NULL AND return_date > due_date THEN DATEDIFF(return_date, due_date)
        ELSE 0
        END
        ) STORED,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (book_id) REFERENCES books (id),
    FOREIGN KEY (borrower_id) REFERENCES borrowers (id)
);

SELECT * FROM borrow_records;
```

---

## Running the Project

### Prerequisites
1. **Java 17** or later.
2. **Maven 3.8+**.
3. IDE (e.g., IntelliJ IDEA).

### Steps
1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd parkee-backend-technical-test
   ```
2. Build the project:
   ```bash
   mvn clean install
   ```
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```
4. Access the API documentation at:
   [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).

---

## OpenAPI Documentation
OpenAPI documentation is automatically generated using **Springdoc OpenAPI**. The API specification is available at:
- JSON: `http://localhost:8080/v3/api-docs`
- Swagger UI: `http://localhost:8080/swagger-ui.html`

---

## Contact
For any queries or support, please contact [firmanqpro@gmail.com].

