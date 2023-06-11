# Travel API Application

This is a Spring Boot application that provides a RESTful API for retrieving information about airports and locations. It uses an embedded H2 database to store the data and implements the provided OpenAPI specification. The application also includes features such as statistics collection, configurable properties, and a client for consuming the API.

## Setup

To run the application, follow these steps:

1. Clone the repository to your local machine.
2. Make sure you have Java JDK 11 or higher installed.
3. Open a terminal or command prompt and navigate to the project's root directory.
4. Run the command `./gradlew bootRun` to start the application.
5. Run the command `./gradlew check` to run the tests.

The application will start, and you can access the API endpoints and Swagger UI using the following URLs:

- API endpoints: [http://localhost:8080](http://localhost:8080/travel/locations, http://localhost:8080/travel/locations/{code}/{type})
- Swagger UI: Run the command `./gradlew runSwaggerUI`[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) 
- H2 Database Console: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

To access the H2 Database Console, use the following settings:
- JDBC URL: `jdbc:h2:mem:travel-api`
- Username: `sa`
- Password: (leave empty)

## Application Structure

The application is structured using the following packages:

- `com.afkl.travel.exercise`: The main package containing the application class and configuration.
- `com.afkl.travel.exercise.config`: The package containing the configuration.
- `com.afkl.travel.exercise.controller`: Contains the API controllers that handle incoming requests and provide responses.
- `com.afkl.travel.exercise.entities`: Contains the domain model classes and JPA entities.
- `com.afkl.travel.exercise.repository`: Contains the data access layer and JPA repositories.
- `com.afkl.travel.exercise.service`: Contains the business logic and services.

## Implementation Details

### JPA Data Access and Data Layer

The data access layer is implemented using JPA (Java Persistence API) and Spring Data JPA. The domain model classes are mapped to JPA entities using annotations such as `@Entity`, `@Table`, `@Column`, and `@ManyToOne`. The JPA repositories are defined using interfaces that extend `JpaRepository` provided by Spring Data JPA. These repositories provide basic CRUD operations and querying capabilities for the entities.

### OpenAPI Specification

The provided OpenAPI specification file `travel-api.yaml` is implemented using SpringFox libraries. The API controllers are implemented according to the specification, with proper request mappings, validations, and responses. The Swagger UI is accessible at [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) and provides an interactive interface for testing the API endpoints.

### Security

The API is secured with basic authentication using the following credentials:

- Username: someuser
- Password: userPass

The `/actuator/metrics` endpoint, which exposes the application statistics, is secured with the following credentials:

- Username: ops
- Password: adminPass

### Statistics Collection

The application collects statistics on the incoming requests, including the total number of requests, the number of requests resulting in an OK response, the number of requests resulting in a 4xx response, the number of requests resulting in a 5xx response, the average response time, and the maximum response time. These statistics are exposed through the `/actuator/metrics` API endpoint as JSON. The statistics are collected using Micrometer, which is a metrics collection library integrated with Spring Boot Actuator.These Metrics can be gathered on the endpoint `/actuator/metrics/http.server.requests`
you can filter metrics like these `/actuator/metrics/http.server.requests?tag=uri:/travel/locations&tag=status:200`

### Configuration

The application can be configured using a configuration file. The configuration properties are defined in the `application.properties` file. 


### Database schema

The data exposed by the API is stored in an embedded H2 database.   
Us JPA mappings to map this data to a domain model.

The structure of the database schema is shown below:

      =================================
      | Location                      |
      |===============================|
      | id        integer (generated) |
      | code      varchar             |
      | type      varchar             |<-------|
      | longitude double              |        |
      | latitude  double              |        | parent:                           
      | parent    integer             |        | relation between locations 
      =================================        | airport -> city, city -> country   
                  |   |                        |
                  |   |                        |
                  |   --------------------------
                  |
                  |
      ==================================
      | Translation                    |
      |================================|
      | id          integer (generated)|
      | location    integer            |
      | language    varchar            |
      | name        varchar            |
      | description varchar            |
      ==================================
      
### API Endpoints

The restful backend should support the following endpoints:

| Method | Path                            | Description                                                                                  |
|--------|---------------------------------|----------------------------------------------------------------------------------------------|
| GET    | /travel/locations               | Returns list of all Locations that match the language selected by user                       |
| GET    | /travel/locations/{code}/{type} | Return a matched location with city/airport/country location type, code and matched language |


## Improvements can be done
1) Docker image should be created
2) Internationilzation should be improved
3) Pagination and HATEOS should be implemented to handle a large amount of data
4)Error Handling with Message Bundle to support multiple language has to be implemented
5)