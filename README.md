Loan Approval Process

Backend application for handling loan applications, including validation, decision-making and payment schedule generation.

Tech Stack
Java 21
Spring Boot 3.x
PostgreSQL
Flyway
SpringDoc (Swagger / OpenAPI)
Docker & Docker Compose
Running the Application
1. Build the project

On Windows:

mvnw.cmd clean package

On macOS/Linux:

./mvnw clean package


2. Start the full system
docker compose up --build

This will start:

PostgreSQL database
Spring Boot application
API Documentation

Swagger UI is available at:

http://localhost:8080/swagger-ui/index.html

Features
- Create loan applications
- Validate Estonian personal code
- Reject customers exceeding configured age limit
- Generate annuity-based payment schedule
- View loan application details with schedule
- Review loan applications
- Approve or reject applications with reason
  
Notes
- Database schema is managed with Flyway migrations
- Default server port: 8080
- Database is initialized automatically on startup
