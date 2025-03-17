# SureCost Drug API - User Guide

A guide on how to set up, run, and test the SureCost Drug API with Swagger documentation.

## Prerequisites

- Java 17 or higher
- Maven
- Git

## Getting Started

### 1. Clone the Repository

git clone <repository-url>
cd drug-api

### 2. Build the Project

build in terminal:
mvn clean install

### 3. Run the Application

mvn spring-boot:run

### 4. Verify the application is running

Open a web browser and navigate to http://localhost:8080/swagger-ui.html
You should see the Swagger UI page with all the API endpoints documented

You can also check the H2 database console at http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:drugdb
Username: sa
Password: password

### 5. Test the API

To run all unit tests in the project:

Open terminal

Enter the following in command: mvn test

You can also test the API directly from the Swagger UI:

Open a web browser and navigate to http://localhost:8080/swagger-ui.html
Expand any endpoint you want to test
Click "Try it out"
Fill in the required parameters
Click "Execute"
View the response

Test inputs:

Create a Drug (POST /api/drugs):

    {
    "manufacturer": "PharmaCorp",
    "name": "Aspirin",
    "quantity": 500,
    "price": 9.99
    }

Create Multiple Drugs (POST /api/drugs/batch):

    [
        {
        "manufacturer": "MediCorp",
        "name": "Ibuprofen",
        "quantity": 300,
        "price": 12.49
        },
        {
        "manufacturer": "HealthGen",
        "name": "Acetaminophen",
        "quantity": 250,
        "price": 8.99
        }
    ]

Get All Drugs (GET /api/drugs)

    No input required

Get Drug by UID (GET /api/drugs/{uid}):

    uid: The UUID of a previously created drug (generated on drug creation in DrugService.java)

Get Drugs by Manufacturer (GET /api/drugs/manufacturer/{manufacturer})

    manufacturer: "PharmaCorp" (or any manufacturer you've used)

Search Drugs by Name (GET /api/drugs/search)

    name: "asp" (should match "Aspirin" because of partial match)

Get Drugs by Maximum Price (GET /api/drugs/price/max)

    price: 10.00

Update a Drug (PUT /api/drugs/{uid})

    path paramter: uid: The UUID of a previously created drug

    sample request body:

    {
        "manufacturer": "PharmaCorp",
        "name": "Aspirin Extra Strength",
        "quantity": 450,
        "price": 11.99
    }

Or use cURL to test from the command line:

Examples:

# Create a drug

curl -X POST 'http://localhost:8080/api/drugs' \
 -H 'Content-Type: application/json' \
 -d '{
"manufacturer": "PharmaCorp",
"name": "Aspirin",
"quantity": 500,
"price": 9.99
}'

# Get all drugs

curl -X GET 'http://localhost:8080/api/drugs'
