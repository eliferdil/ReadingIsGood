# ReadingIsGood

This is a Java, Spring Boot application with H2 in-memory database.

## How to Run

This application is packaged as a war which has Tomcat embedded. You can run it using the java -jar command.

* Clone this repository
* Make sure you are using JDK 11 and Maven 3.x
* You can build the project by running `mvn clean package`
* Once successfully built, you can run the service by following command:
```
java -jar target/demo-0.0.1-SNAPSHOT.war
```
        
## Swagger Doc
http://localhost:8080/swagger-ui.html

## Authentication

Bearer Token based Authentication

## Authorization

[ADMIN, CUSTOMER]

## Public Endpoints

Register user as Admin: /public/users/admin
Register user as Customer: /public/users/customer
Login: /public/authentication/login

## Secured Endpoints

Register Book: /rest/books (role: ADMIN)
Update Book Stock: /rest/books/update-stock (role: ADMIN)

Create Order: /rest/orders (role: ADMIN, CUSTOMER)
List Orders of Customer: /rest/customers/{userId}/orders/{page} (role: ADMIN, CUSTOMER)
List Order by ID: /rest/orders/{orderId} (role: ADMIN, CUSTOMER)
List Orders by Date Interval: /rest/orders?dateFrom={fromDate}&dateTo={toDate}&page={page} (role: ADMIN, CUSTOMER)
Get Customer's Monthly Statistics: /rest/statistics/{userId} (role: ADMIN, CUSTOMER)


