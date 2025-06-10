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

Prerequisites: Java 21+, Maven 3.9+, PostgreSQL 15 (tested on)

### 1. Clone the repository

```bash
  git clone https://github.com/mihan2002/leave-request-api.git
```

### 2. Configure the enviroment

Change the directory to `leave-request-api`.

```bash
  cd leave-request-api
```

Edit `src/main/resources/application.properties` file to configure the database connection details.

```bash
  spring.datasource.url=jdbc:postgresql://<host>:<your_port_number>/<database_name>
  spring.datasource.username=myuser
  spring.datasource.password=mypassword
```

### 3. Run the backend

Open a command window and change directory to the `leave-request-api` folder and run the below commands.

```bash
 mvnw spring-boot:run
```


### 4. Frontend setup (Optional)
  If you haven't already setup the frontend application, please use the below link to setup the frontend application.
```bash
  https://github.com/mihan2002/leave-request-ui.git
```
