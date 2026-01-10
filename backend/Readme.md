# Spring Boot Backend Deployment Guide 

## Prerequisites

- Java Development Kit (JDK 17 or higher) installed.
- Maven installed.
- Spring Boot application source code or JAR file.

## Step 1: Install Java

1. Verify if Java is installed by running the following command:

   ```bash
   java -version
   ```

If Java is not installed, install the JDK from OpenJDK.

```shell
apt update && apt install openjdk-17-jdk -y
java -version
```

## Step 2: Install Maven

Installing maven in ubuntu:

```shell
apt install maven -y
```

### Verify Maven installation:

```bash

mvn -version
```

## Step 3: Build the Spring Boot Application

### Update DB credentials in application.properties:
```
pwd
```

```bash
vim backend/src/main/resources/application.properties
```
```shell
spring.datasource.url=jdbc:mariadb://localhost:3306/student_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
```

### Build springboot Application using maven

```shell
mvn clean package
```

## Step 4: Run the Application

Run the generated JAR file by using the following command:

```bash

java -jar target\spring-backend-v1.jar
```

The application will start and be accessible at:

http://localhost:8080

### Step 5: Keep the Application Running


To keep the application running in the background, you can use nohup or a similar method. 

