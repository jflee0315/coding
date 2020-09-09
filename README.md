# The Coding Project
---
Coding assignment with Sidecar Health.

## Description
---
It is a simple RESTful service for CRUD operations on a database table `employee`.

## Assumptions
---
### Redis use case
- Employees are seldom created and deleted.
- There is a peak demand on getting the Employee count
- While getting count of employees does not necessary means a full scan, please assume that it is very time-consuming and cannot be optimized at the database level in this case.

### Authentication
- We don't want OAuth to complicate the project, specifically, Spring OAuth dependency.

### Database
- Simple JPA CRUD is preferred over Spring Repositories.

## TODOs
---

- Configurations like database connection string should be passed in as parameters, instead of hard-code.
- Make application wait for MySQL server to start.
- Better testing code coverage.
- Better test execution for running unit/integration tests.
- More comment/doc in the code/swagger.
- Cleanup dependencies.

## Building and Running
---
A MySQL server and a Redis server should be running(Both using their default port) prior to running the Spring Boot application.

- Using docker-compose.

Please note that currently the application will not wait for mysql server to startup.

Therefore, it may throw exception when starting up. It will recover once MySQL server is up and running.

If you see the application responds 500 error code, please wait a few seconds(depends on you computing resource) for mysql server to initialize/start.
```bash
./mvnw package -Dmaven.test.skip=true 
docker-compose up --build
```

## Authentication
---
The application is using JWT for all the endpoints(including Swagger and Actuator endpoints).

1. To authenticate, pass in username and password (currently hard-coded, both are 'demo') to ```POST /login``` using Basic authentication header.

2. The response header should include a JWT token (Bearer token).

3. Include the token in the following requests using Bearer authentication.

## Testing
---
Use the following command to run unit/integration test.
For integration testing, it launches embedded Redis server and MySQL server.
```bash
./mvnw test
```

## Swagger
---
visit http://localhost:8080/swagger-ui.html to see the endpoint definitions.

## Actuator
visit http://localhost:8080/actuator to see actuator endpoints.
