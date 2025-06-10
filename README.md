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

Prerequisites: Java 17+, Maven 3.9+, PostgreSQL 15 (tested on)

### 1. Clone the repository

```bash
  git clone https://github.com/mihan2002/leave-request-api.git
```

### 2. Configure the enviroment

Change the directory to the `leave-request-api` path.

```bash
  cd leave-request-api
```
Configuring Application Properties

Edit src/main/resources/application.properties:

```bash
  spring.datasource.url=your_database_url
  spring.datasource.username=your_username
  spring.datasource.password=your_password
```

### 3. Run the backend

```bash
./mvnw spring-boot:run
```


### 4.Frontend setup(Optional)
-if you didnt setup the frontend use this link and follow the guide 
```bash
  https://github.com/mihan2002/leave-request-ui.git
```
