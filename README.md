# Information management system about drivers, their cars, spare parts kits with the implementation of an account for managing payments

## Functional requirements:
* Customer registration
* Editing customer information
* adding/editing/removing information about cars, related parts and fees
* Pagination
* Sorting
* Searching
* transfer of payments with the possibility of currency conversion

## Technologies:
* Spring Boot
* Spring Data JPA
* Hibernate
* PostgreSQL
* Maven
* JUnit + Mockito
* Mapstruct
* Docker

## Instructions for building and running Docker containers
1. Run the command to build the application from the root directory: <br> `mvn clean install` <br>
2. From the root directory, run the command to build and run docker containers (databases and applications): <br> `docker compose up` <br>
3. Done. The application is available at: <br> `http://localhost:8080/api/` <br>

### API documentation is available at: 
> http://localhost:8080/swagger-ui.html <br>
System for recording information about drivers, cars and spare parts with the ability to record payments and maintain account functionality
