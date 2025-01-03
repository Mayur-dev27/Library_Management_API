# Library_Management_API

A robust application designed to streamline library operations, including book management, user account handling, and overdue fine calculation.

## Table of Contents
Features
Technologies Used
Project Structure
Installation and Setup
API Endpoints
Future Enhancements

# Features
Book Lending and Returns: Manage book borrowing and return processes seamlessly.
Fine Calculation: Calculates fines for overdue books at ₹5 per day.
JWT Authentication: Secures endpoints using JSON Web Tokens for user authentication and authorization.
Automated Cron Job: A daily scheduled task to check for overdue books and apply fines.
Exception Handling: Graceful error handling with meaningful responses.
Dynamic Responses: Uses Map<String, Object> for flexible API responses.

# Technologies Used
Java
Spring Boot
Spring Data JPA (Hibernate)
JWT (JSON Web Tokens)
Cron Jobs
Lombok
H2 Database (for development)
Postman (for testing)

# Project Structure

src/main/java/com/library
├── controller
├── entity
├── service
├── repository
├── exception
├── request
├── response
└── config

# Installation and Setup
Clone the repository:
bash
Copy code
git clone https://github.com/your-username/library-management-system.git
Navigate to the project directory:
bash
Copy code
cd library-management-system
## Build the project:
bash
Copy code
mvn clean install
## Run the application:
bash
Copy code
mvn spring-boot:run
# API Endpoints
## Authentication
Login: /api/auth/login
Register: /api/auth/register
Book Management
Add Book: /api/books (POST)
Get All Books: /api/books (GET)
Delete Book: /api/books/{id} (DELETE)
User Management
Add User: /api/users (POST)
Get All Users: /api/users (GET)
Fine Management
Check Fines: /api/fines/{userId} (GET)
# Future Enhancements
Advanced Search: Add filters for genre, author, and availability.
Notifications: Email or SMS alerts for due dates.
Admin Dashboard: Visualize library metrics like active users, borrowed books, etc.
Integration with Cloud Storage: Store book data in a cloud-based database.
