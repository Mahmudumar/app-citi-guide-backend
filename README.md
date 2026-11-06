# App Citi Guide – Backend

Spring Boot foundation for the App Citi Guide eProject. This repository currently contains the application setup and Supabase PostgreSQL connection only. Feature APIs and database models should be added by the team as requirements are agreed.

## Technology
- Java 21
- Spring Boot 3.5.7
- Maven
- Spring Web, Spring Data JPA / Hibernate
- PostgreSQL JDBC driver
- Supabase PostgreSQL

## Prerequisites
- JDK 21
- Maven 3.6.3 or later
- A Supabase project with its PostgreSQL database

## 1. Get Supabase database details
1. Open your Supabase project.
2. Select Connect and open the connection details.
3. Use the Session pooler details if your network does not support IPv6 or if you need a pooled connection.
4. Copy the host, port, database name, username, and database password.

Use the PostgreSQL connection details, not the Supabase API URL or API keys. The Spring application connects to PostgreSQL through JDBC.

## 2. Set environment variables
Use .env.example as a reference and set these variables in your operating system, IDE run configuration, or deployment environment:

| Variable | Purpose |
| --- | --- |
| SUPABASE_DB_HOST | Database host from Supabase |
| SUPABASE_DB_PORT | Database port |
| SUPABASE_DB_NAME | Database name, commonly postgres |
| SUPABASE_DB_USER | Database username |
| SUPABASE_DB_PASSWORD | Database password |
| DB_POOL_SIZE | Optional connection pool size; defaults to 5 |
| PORT | Optional server port; defaults to 8080 |

Spring Boot does not load .env files by default. Do not put real credentials in application.properties or commit them.

Example for macOS/Linux:

    export SUPABASE_DB_HOST="your-host"
    export SUPABASE_DB_PORT="5432"
    export SUPABASE_DB_NAME="postgres"
    export SUPABASE_DB_USER="your-database-user"
    export SUPABASE_DB_PASSWORD="your-database-password"
    mvn spring-boot:run

On Windows PowerShell, set each variable with $env:VARIABLE_NAME="value" before running Maven.

## 3. Run the application

From the repository root:

    mvn clean spring-boot:run

The application listens on http://localhost:8080 by default. The basic health endpoint is:

    http://localhost:8080/actuator/health

A healthy response confirms the application is running. It does not by itself prove that a database query has succeeded.

## Project structure

    src/main/java/com/appcitiguide/backend/
    ├── AppCitiGuideBackendApplication.java
    ├── config/       # Shared Spring configuration
    ├── controller/   # REST endpoints
    ├── dto/          # API request and response objects
    ├── entity/       # JPA mappings for PostgreSQL tables
    ├── exception/    # Shared exceptions and API error handling
    ├── repository/   # Spring Data JPA repositories
    └── service/      # Application business logic

## Notes for the next developer
- Confirm the database schema and naming conventions with the team before adding entities.
- Add JPA classes in entity, repository interfaces in repository, business logic in service, and API endpoints in controller.
- Use DTOs at the API boundary instead of returning JPA entities directly.
- Add validation to request DTOs and handle errors centrally in exception.
- Add focused configuration classes in config only when needed.
- Keep secrets in environment variables. Never commit .env or real database passwords.
- Hibernate schema generation is disabled (ddl-auto=none), so the application will not create or alter Supabase tables. Manage schema changes through Supabase SQL migrations or the team's agreed database workflow.
- After entities and matching database tables exist, consider changing ddl-auto to validate so Hibernate checks mappings without changing the schema.

## Current scope
This is only the backend foundation. Authentication, user roles, places, categories, search, reviews, routes, and other App Citi Guide features have intentionally not been implemented yet.
