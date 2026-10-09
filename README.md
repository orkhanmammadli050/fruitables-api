# Fruitables API - E-Commerce RESTful Backend Service

Fruitables API is a robust backend e-commerce web service engineered to support product catalog management, dynamic discount workflows, customer shopping carts, order processing lifecycles, and product review management. 

Originally developed as a monolithic Spring Boot MVC application, the project was completely refactored into a modern, decoupled RESTful API backend following clean architecture principles and SOLID design patterns.

---

## 🛠️ Tech Stack & Tools

* **Java Version:** Java 17
* **Frameworks & Ecosystem:** Spring Boot 3, Spring MVC, Spring Data JPA, Hibernate ORM
* **Security & Auth:** Spring Security, JSON Web Tokens (JWT), Role-Based Access Control (RBAC)
* **Database Management:** PostgreSQL
* **API Documentation & Testing:** RESTful API Architecture, OpenAPI 3.0, Swagger UI, Postman
* **Build & Tools:** Apache Maven, Git, IntelliJ IDEA

---

## ✨ Key Features & Technical Highlights

* **Monolith-to-Decoupled Refactoring:** Re-architected the controller layer from traditional Spring MVC view-rendering to standardized JSON REST API endpoints.
* **Authentication & Authorization:** Secure, stateless JWT-based authentication mechanism with encrypted password handling.
* **Role-Based Access Control (RBAC):** Fine-grained permission levels enforcing access separation between `ROLE_ADMIN` and `ROLE_USER`.
* **Database & ORM Optimization:** Structured relational database models using Spring Data JPA repositories with dynamic queries on PostgreSQL.
* **Core E-Commerce Domain Logic:**
  * Interactive shopping cart functionality (item addition, quantity updates, removal, session persistence).
  * End-to-end order processing and lifecycle status workflows.
  * Dynamic coupon and promotional discount calculations.
  * Customer rating and review feedback mechanisms.
* **Automated Notification Integration:** Embedded JavaMail email notification system for automated order confirmations and system updates.
* **Interactive API Documentation:** Embedded Swagger UI (`OpenAPI 3.0`) for real-time endpoint testing directly from the browser.

---

## 📄 API Documentation & Local Setup

Once the application is running locally on port `8080`, access the interactive Swagger UI documentation at:
```text
http://localhost:8080/swagger-ui/index.html
