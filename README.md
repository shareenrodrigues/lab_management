Online Laboratory Management System

##  Features

-  Book, cancel, and reschedule appointments
-  Add and remove lab tests to appointments
-  Upload and view test results
-  Role-based UI for **clients** and **staff**
-  View all appointments with tests and result details
-  Responsive and user-friendly UI using **PrimeNG**

---

## Technologies Used

| Layer       | Tech Stack                              |
|-------------|------------------------------------------|
| Frontend    | Angular 16, PrimeNG, TypeScript          |
| Backend     | Spring Boot, Hibernate, Java 17          |
| Database    | MySQL 8.x , Amazon RDS                               |
| UI Styling  | SCSS, PrimeFlex, PrimeIcons                         |
| APIs        | RESTful using Spring Web MVC             |

---

##  Getting Started

###  Prerequisites

- Node.js & npm
- Angular CLI (`npm install -g @angular/cli`)
- Java 17+
- Maven or Gradle
- MySQL Server

---

##  Backend Setup (Spring Boot)

1. Navigate to the `service/lab-management` folder:
    ```bash
    cd backend
    ```

2. Update `application.properties` with your MySQL config:
    ```properties
spring.datasource.url=jdbc:mysql://project-157.cp264wc02tq4.us-east-2.rds.amazonaws.com:3306/lab_management
spring.datasource.username=admin
spring.datasource.password=labmanager
    ```

3. Run the backend:
    ```bash
    ./mvnw spring-boot:run
    ```

4. The API will be live at:
    ```
    http://localhost:8080/api
    ```

---

##  Frontend Setup (Angular)

1. Navigate to the 'Client' folder:
    ```bash
    cd Client
    ```

2. Install dependencies:
    ```bash
    npm install
    ```

3. Run the frontend:
    ```bash
    ng serve
    ```

4. The app will be running at:
    ```
    http://localhost:4200
    ```

---

##SQL Scripts

Get SQl scripts in '/Scripts' folder.

## User Roles

| Role   | Permissions                                               |
|--------|-----------------------------------------------------------|
| Client | View/book/reschedule/cancel appointments, view results   |
| Staff  | View all appointments, update test results               |

---
