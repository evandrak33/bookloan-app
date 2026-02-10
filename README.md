City Public Library – Book Loan Application


This project is a Spring Boot web application for managing a public library book loan system.

It supports:

---Book catalog management

---Book borrowing and returning

---User wishlists

---Role-based access (ADMIN / USER)

---Server-side rendered UI using Thymeleaf


-Technology Stack

Java 17

Spring Boot

Spring MVC

Spring Security

Spring Data JPA

Thymeleaf (SSR)

H2 In-Memory Database

Maven

HTML / CSS

-User Roles
--ADMIN (Librarian)

View all books

Add new books

Edit existing books

Borrow books on behalf of users

View all loans

Return books

View all users

--USER (Library Member)

View available books

View personal loans

Add/remove books from wishlist

View wishlist status (available / borrowed / borrowed by me)

Cannot borrow or return books directly

-Authentication

Authentication is handled using Spring Security with:

Custom UserDetailsService

DAO Authentication Provider

BCrypt password encoding

Form-based login


-Database

The application uses H2 in-memory database

Data exists only while the application is running

On restart, the database is reset and re-seeded via DataInitializer

----H2 Console

URL: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:librarydb

Username: sa

Password: (empty)


-User Interface

The UI is:

Server-side rendered (SSR)

Styled consistently across all pages

Includes:

Login

Books

Loans

Wishlist

Add / Edit / Borrow Book pages

-Design principles:

Clear separation of ADMIN and USER actions

Consistent header, footer, and background imagery

Accessible and readable tables



-Data Initialization

On startup, the application automatically:

Creates sample users

Creates sample books

Assigns roles

Prepares the system for immediate testing

This logic is implemented in DataInitializer.





Build & Run Instructions

-System Prerequisites

Java 17

Maven

-Check installation:

java -version
mvn -version

-Build the Application

From the root directory of the project, run:


mvn clean package


This will compile the project and create a JAR file in the target/ directory.

-Run the Application

You can run the application in one of the following ways:

--Option 1: Run with Maven (recommended)

mvn spring-boot:run


--Option 2: Run the generated JAR


java -jar target/bookloan-app.jar


-Access the Application

Open a browser and go to:

http://localhost:8080/login

The application uses an H2 in-memory database, so data is available only while the application is running.

Test Credentials

The application is pre-seeded with sample users via DataInitializer.


-Credentials

--ADMIN

Username: admin

Password: admin123

--USERS 

---example: 

Username: maria.kappa — Password: user123

Passwords are stored encrypted. These credentials are for testing and demonstration purposes only.

-Stopping the Application

To stop the application:

Press CTRL + C in the terminal

