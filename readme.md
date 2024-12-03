# Project Management API

This is a Spring Boot application that provides REST APIs for managing projects and tasks.

## Prerequisites

- Java 17 or higher
- Maven
- Docker (optional)

## Building the Application

```bash
mvn clean package
```

## Running the Application

### Using Maven

```bash
mvn spring-boot:run
```

### Using Docker

```bash
docker-compose up --build
```

## Testing

Run the tests using:

```bash
mvn test
```

## API Documentation

Once the application is running, you can access:
- Swagger UI: http://localhost:8080/swagger-ui.html
- API Docs: http://localhost:8080/api-docs

## H2 Database Console

Access the H2 console at http://localhost:8080/h2-console with these credentials:
- JDBC URL: jdbc:h2:mem:projectdb
- Username: sa
- Password: password

## Available Endpoints

### Projects
- POST /api/projects - Create a new project
- GET /api/projects - List all projects
- GET /api/projects/{projectId} - Get project details
- GET /api/projects/summary - Get projects summary with task counts

### Tasks
- POST /api/projects/{projectId}/tasks - Add task to project
- GET /api/projects/{projectId}/tasks - List project tasks (supports pagination and filtering)
- PUT /api/tasks/{taskId} - Update task
- DELETE /api/tasks/{taskId} - Delete task

## Environment Variables

The application uses default configuration suitable for local development. For production deployment, consider configuring these environment variables:
- SPRING_PROFILES_ACTIVE
- SERVER_PORT
- SPRING_DATASOURCE_URL
- SPRING_DATASOURCE_USERNAME
- SPRING_DATASOURCE_PASSWORD