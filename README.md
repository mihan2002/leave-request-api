# 📝 Leave Request Application

This is a full-stack **Leave Request Management** system built with a **React (Vite)** frontend and a **Spring Boot** backend. The backend uses **PostgreSQL** for data storage and implements **JWT-based authentication and authorization**.

This document contains only the backend setup.

---

## 🔧 Tech Stack

- Spring Boot
- Spring Security
- PostgreSQL
- JPA (Hibernate)
- JWT Token Authentication

---

## 📁 Project Structure

Located inside the  `/leave-request-api` folder:

The application includes

- `User` and `LeaveRequest` entities
- `AuthController` and `LeaveRequestController`
- Services and Repositories
- JWT Utility classes

---

## 🚀 Getting Started

Prerequisites: - Java 17+ - Maven 3.9+ - PostgreSQL(tested on)

### 1. Clone the Repository

```bash
git clone https://github.com/mihan2002/leave-request-api.git
cd leave-request-api
```

### 2. Backend Setup

Configuring Application Properties

Edit src/main/resources/application.properties:

```bash
  spring.datasource.url=your_database_url
  spring.datasource.username=your_username
  spring.datasource.password=your_password
```

### 4. Run the Backend

```bash
./mvnw spring-boot:run
```


### 5.Frontend setup(Optional)
-if you didnt setup the frontend use this link and follow the guide 
```bash
  https://github.com/mihan2002/leave-request-ui.git
```
