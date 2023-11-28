# BeerCrudJwtDb

A simple CRUD for different beers with Spring Security JWT-implementation. Built for school project.

## Getting Started
Replace the following in application.properties with your own database credentials:
```
spring.datasource.username=beer
spring.datasource.password=beer
```
Replace the following in application.properties with your own database details:
```
spring.datasource.url=jdbc:mariadb://localhost:3306/beer
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
```

## Usage
Run the application and go to http://localhost:8080/swagger-ui.html to see the endpoints.
Upon running, USER and ADMIN-roles are created. 
A user with ADMIN-role is created with the following credentials:
```
username: admin
password: admin
```
You can changet his in BeerCrudJwtDbApplication.java CommandLineRunner.
