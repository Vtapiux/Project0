# Project 0

## Overview
This project is a Loan Management System built using Java, Javalin, and PostgreSQL. It provides functionalities to manage loan applications, track repayments, and generate reports.

## Prerequisites
Before running the application, ensure you have the following installed:
- Java 17
- Maven
- PostgreSQL

## How to Run the Application
You can run the application using Maven or by executing the JAR file.

### Running with Maven
1. Navigate to the project root directory.
2. Compile and package the application:
   ```sh
   mvn clean package
   ```
3. Run the application:
   ```sh
   mvn exec:java
   ```

### Running with a JAR File
1. Package the application:
   ```sh
   mvn clean package
   ```
2. Run the application using Java:
   ```sh
   java -jar target/Project0-1.0-SNAPSHOT.jar
   ```

## Database Configuration
The application connects to a PostgreSQL database. Ensure your database is running and properly configured.

### Default Configuration
- **Database**: PostgreSQL
- **Host**: `localhost`
- **Port**: `5432`
- **Username**: `postgres`
- **Password**: `Minombre01`

Modify the database configuration in the `application.properties` file or set environment variables accordingly.

```properties
DB_URL=jdbc:postgresql://localhost:5432/postgres
DB_USER=postgres
DB_PASSWORD=Minombre01
```

## Running Unit Tests
To execute unit tests, run the following command:
```sh
mvn test
```
This will run all unit tests and display the results in the terminal.

## License
This project is licensed under the MIT License.

