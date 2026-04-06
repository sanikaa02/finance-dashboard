# Finance Dashboard - Complete Project

A full-stack Finance Dashboard application with Spring Boot backend, html ,css, JavaScript frontend, MySQL database, JWT authentication, and AWS deployment.

---


# 🎯 Project Overview

This is a Finance Dashboard system that allows organizations to manage their financial records with role-based access control.

## Key Highlights

* ✅ Role-Based Access Control (RBAC)
* ✅ JWT Authentication
* ✅ Dashboard Analytics
* ✅ Clean Architecture
* ✅ Soft Delete
* ✅ AWS Deployment Ready

---

# ✨ Features

## Authentication

* JWT based authentication
* BCrypt password encryption
* Role based authorization

## User Management (Admin Only)

* Create users
* Update users
* Delete users
* Toggle user status

## Financial Records

* Add income/expense
* Update transactions
* Delete transactions
* Filter by category/date/type

## Dashboard Analytics

* Total Income
* Total Expense
* Net Balance
* Category wise analytics
* Monthly Trends

---

# 🛠️ Technology Stack

## Backend

* Spring Boot 3
* Java 17
* Spring Security
* JWT
* Maven

## Frontend

* HTML
* CSS
* JavaScript

## Database

* MySQL 8

## Deployment

* AWS EC2
* AWS S3
* AWS RDS

---

# 📁 Project Structure

## 📁 Project Structure
 
```
finance-dashboard/
├── backend/                          # Spring Boot backend
│   ├── src/main/java/com/finance/
│   │   ├── FinanceDashboardApplication.java
│   │   ├── controller/
│   │   │   ├── AuthController.java
│   │   │   ├── UserController.java
│   │   │   ├── FinancialRecordController.java
│   │   │   └── DashboardController.java
│   │   ├── service/
│   │   │   ├── AuthService.java
│   │   │   ├── UserService.java
│   │   │   ├── FinancialRecordService.java
│   │   │   └── DashboardService.java
│   │   ├── repository/
│   │   │   ├── UserRepository.java
│   │   │   └── FinancialRecordRepository.java
│   │   ├── entity/
│   │   │   ├── User.java
│   │   │   └── FinancialRecord.java
│   │   ├── dto/
│   │   │   ├── LoginRequest.java
│   │   │   ├── LoginResponse.java
│   │   │   ├── UserDTO.java
│   │   │   ├── FinancialRecordDTO.java
│   │   │   ├── DashboardSummaryDTO.java
│   │   │   ├── CategorySummaryDTO.java
│   │   │   └── MonthlyTrendDTO.java
│   │   ├── security/
│   │   │   ├── JwtUtil.java
│   │   │   ├── CustomUserDetailsService.java
│   │   │   └── JwtAuthenticationFilter.java
│   │   ├── config/
│   │   │   └── SecurityConfig.java
│   │   └── exception/
│   │       ├── ResourceNotFoundException.java
│   │       ├── AccessDeniedException.java
│   │       └── GlobalExceptionHandler.java
│   ├── src/main/resources/
│   │   └── application.properties
│   └── pom.xml
│
├── frontend/                       
│   ├── index.html

---

# 🚀 Local Setup

## Prerequisites

* Java 17
* Maven
* MySQL 8
* Git

---

# Step 1: Clone Project

```bash
git clone <repository-url>
cd finance-dashboard
```

---

# Step 2: Database Setup (SQL Commands)

## Create Database

```sql
CREATE DATABASE finance_dashboard;
USE finance_dashboard;
```

---

# Create Users Table

```sql
CREATE TABLE users (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL,
  password VARCHAR(255) NOT NULL,
  role ENUM('ADMIN','ANALYST','VIEWER') NOT NULL,
  status ENUM('ACTIVE','INACTIVE') DEFAULT 'ACTIVE',
  deleted TINYINT(1) DEFAULT 0,
  deleted_at TIMESTAMP NULL DEFAULT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY email (email)
);
```

---

# Create Financial Records Table

```sql
CREATE TABLE financial_records (
  id BIGINT NOT NULL AUTO_INCREMENT,
  amount DECIMAL(15,2) NOT NULL,
  type ENUM('INCOME','EXPENSE') NOT NULL,
  category VARCHAR(50) NOT NULL,
  date DATE NOT NULL,
  description TEXT,
  created_by BIGINT NOT NULL,
  deleted TINYINT(1) DEFAULT 0,
  deleted_at TIMESTAMP NULL DEFAULT NULL,
  deleted_by BIGINT DEFAULT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (created_by) REFERENCES users(id),
  FOREIGN KEY (deleted_by) REFERENCES users(id)
);
```

---

# Insert Demo Users

```sql
INSERT INTO users (id,name,email,password,role,status,deleted) VALUES
(1,'Admin','admin@finance.com','$2a$10$MnUp1anX1fqbbIvqAetmFuGWVChCVWKzKjpGi1aAi6uyDdzKRUUbC','ADMIN','ACTIVE',0),
(2,'Sarah Admin','sarah.admin@finance.com','$2a$10$c5pbX3H/blgvF1SByosFu.nb1Nt6gdppzTUV87CEwzUKM/QSh761C','ADMIN','ACTIVE',0),
(3,'Mike Analyst','analyst@finance.com','$2a$10$c5pbX3H/blgvF1SByosFu.nb1Nt6gdppzTUV87CEwzUKM/QSh761C','ANALYST','ACTIVE',0),
(4,'David Analyst','david@finance.com','$2a$10$c5pbX3H/blgvF1SByosFu.nb1Nt6gdppzTUV87CEwzUKM/QSh761C','ANALYST','ACTIVE',0),
(5,'Viewer User','viewer@finance.com','$2a$10$c5pbX3H/blgvF1SByosFu.nb1Nt6gdppzTUV87CEwzUKM/QSh761C','VIEWER','ACTIVE',0);
```

---

# Insert Financial Records

```sql
INSERT INTO financial_records
(amount,type,category,date,description,created_by)
VALUES
(85000,'INCOME','Salary','2024-01-05','January Salary',1),
(12000,'INCOME','Freelance','2024-01-10','Website Project',1),
(5000,'INCOME','Investment','2024-01-15','Dividends',1),
(25000,'EXPENSE','Rent','2024-01-01','House Rent',1),
(8000,'EXPENSE','Groceries','2024-01-05','Groceries',1),
(4500,'EXPENSE','Utilities','2024-01-08','Electricity Bill',1),
(3000,'EXPENSE','Transport','2024-01-10','Fuel',1),
(2000,'EXPENSE','Shopping','2024-01-22','Clothes',1);
```

---

# Default Login Credentials

| Role    | Email                                             | Password    |
| ------- | ------------------------------------------------- | ----------- |
| Admin   | [admin@finance.com](mailto:admin@finance.com)     | password123 |
| Analyst | [analyst@finance.com](mailto:analyst@finance.com) | password123 |
| Viewer  | [viewer@finance.com](mailto:viewer@finance.com)   | password123 |

---

# Step 3: Configure Backend

Edit application.properties

```
spring.datasource.url=jdbc:mysql://localhost:3306/finance_dashboard
spring.datasource.username=root
spring.datasource.password=yourpassword

jwt.secret=yourSecretKey
```

---

Step 4: Run Backend
cd backend
mvn clean install
mvn spring-boot:run

Backend URL

http://localhost:8080
Step 5: Run Frontend

Using VS Code Live Server

Frontend URL

http://127.0.0.1:5500/frontend/index.html

OR Using Python

cd frontend
python -m http.server 3000

Frontend URL

http://localhost:3000


---

# Testing

Test Logins

Admin
[admin@finance.com](mailto:admin@finance.com)
password123

Analyst
[analyst@finance.com](mailto:analyst@finance.com)
password123

Viewer
[viewer@finance.com](mailto:viewer@finance.com)
password123

---

# 📘 Swagger API Documentation

After running backend, open Swagger UI:

```
http://localhost:8080/swagger-ui/index.html
```

 Direct Login Endpoint:

```
http://localhost:8080/swagger-ui/index.html#/auth-controller/login
```

## Swagger Testing Steps

1. Run Backend
2. Open Swagger URL
3. Click Auth Controller
4. Click Login API
5. Click "Try it out"
6. Enter credentials
7. Execute
8. Copy JWT token
9. Click Authorize button (Top Right)
10. Paste token as:

```
Bearer YOUR_TOKEN
```

Now test all secured APIs directly from Swagger.

---

# 🧪 API Testing (Postman / Curl)

You can test APIs using Postman or Curl

## 1. Login API

POST /api/auth/login

Request

```
{
  "email": "admin@finance.com",
  "password": "password123"
}
```

Response

```
{
  "token": "JWT_TOKEN",
  "type": "Bearer",
  "id": 1,
  "name": "Admin",
  "role": "ADMIN"
}
```

---

## 2. Get All Users (Admin Only)

GET /api/users

Headers

```
Authorization: Bearer YOUR_TOKEN
```

---

## 3. Create Financial Record

POST /api/records

Headers

```
Authorization: Bearer YOUR_TOKEN
```

Request

```
{
  "amount": 10000,
  "type": "INCOME",
  "category": "Salary",
  "date": "2024-01-10",
  "description": "Monthly Salary"
}
```

---

## 4. Dashboard Summary

GET /api/dashboard/summary

Headers

```
Authorization: Bearer YOUR_TOKEN
```

---

## 5. Category Summary

GET /api/dashboard/category-summary

---

## 6. Monthly Trends

GET /api/dashboard/monthly-trends

---

## Postman Testing Steps

1. Login API
2. Copy JWT Token
3. Add Authorization Header
4. Test All APIs

---


