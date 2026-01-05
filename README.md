# 📚 Library Management System

A **Spring Boot–based Library Management System** that allows users to **rent physical books**, **read digital books online**, and manage library operations through a secure and scalable backend API.

This project is designed following modern backend best practices, focusing on **security**, **performance**, and **maintainability**.

---

## ✨ Features

### 📖 Book Management

* Register and manage books
* Support for physical and digital books
* Availability tracking
* Book categories and metadata

### 👤 User Management

* User registration and authentication
* Role-based access (Admin, Librarian, User)
* Secure profile management

### 🔐 Authentication & Authorization

* Spring Security integration
* JWT-based authentication
* Role and permission-based access control

### 📄 Pagination

* Paginated book listings
* Integration with Spring Data `Page` and `Pageable`
* Sorting and filtering support

### ⚠️ Global Error Handling

* Centralized exception handling with `@ControllerAdvice`
* Standardized error response format
* Meaningful HTTP status codes

### 🚦 Rate Limiting

* Request rate limiting per user or IP
* Protection against abuse and brute-force attacks

### ⚡ Caching

* Cache frequently accessed data (books, categories)
* Spring Cache abstraction support
* Optional Redis integration

### 📝 Logging

* Centralized logging configuration
* Request and error logging
* Traceability for debugging and monitoring

### 📘 API Documentation

* OpenAPI / Swagger UI
* Automatically generated API documentation
* Clear request and response definitions

### 🧩 API Versioning

* Versioned endpoints (e.g. `/api/v1`)
* Backward compatibility support

### 🔄 Asynchronous Processing

* Async operations using `@Async`
* Non-blocking tasks (notifications, background updates)

### 🗄️ Database Relationships

* JPA/Hibernate entity relationships
* Users, Books, Rentals, and Categories
* Optimized lazy and eager loading strategies

### 🔒 Security Best Practices

* CSRF protection
* CORS configuration
* Secure HTTP headers
* Input validation and sanitization

### 📁 File Upload / Download

* Upload book covers
* Upload digital book files (PDF, EPUB, etc.)
* Secure file download and streaming

### 📦 Standard Response Format (Optional)

* Unified API response structure
* Consistent success and error payloads

### 🧵 Background Jobs & Queues

* Scheduled jobs (overdue rentals, reminders)
* Background processing for long-running tasks
* Retry and failure handling

---

## 🎯 Project Goals

* Provide a complete backend for library systems
* Enable book rental and online reading functionality
* Ensure scalability and security
* Follow clean architecture and Spring Boot best practices

---

## 🛠️ Tech Stack

* Java
* Spring Boot
* Spring Security
* Spring Data JPA / Hibernate
* OpenAPI / Swagger
* PostgreSQL / MySQL (configurable)
* Redis (optional)
* Maven / Gradle

---

## 🚀 Getting Started

### Prerequisites

* Java 17+
* Maven or Gradle
* Database (PostgreSQL / MySQL)

### Run the Application

```bash
./mvnw spring-boot:run
```

or

```bash
./gradlew bootRun
```

---

## ⚙️ Configuration

Application configuration is managed via `application.yml`
Example:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/library
    username: library_user
    password: password
```

---

## 🧪 Testing

* Unit tests for services and utilities
* Integration tests using Spring Boot Test
* Test containers support

---

## 📄 License

This project is licensed under the **MIT License**.
