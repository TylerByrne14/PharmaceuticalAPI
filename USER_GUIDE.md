# SureCost Drug API - User Guide

A guide on how to set up, run, and test the SureCost Drug API with Swagger documentation.

## Prerequisites

- Java 17 or higher
- Maven
- Git

## Getting Started

### 1. Clone the Repository

git clone git@github.com:TylerByrne14/PharmaceuticalAPI.git
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

### NOTES ON IMPROVEMENTS

Use a postgres database for larger data volumes. I used an H2 database here since it's suitable for testing

Create DTOs so I can add fields to the entity without neccessarily exposing that to the client. One example is if I wanted to add an update_date to the entity that tracks when the drug was last updated but don't want that in the response, I could create a DrugResponseDTO

Assuming this API would be handling large datasets, pagination would help with scalabilty and response times making a better user experience.

Include more robust error handling. This would be beneficial for clients to know what went wrong and make it easier for developers to debug

Additonal Endpoints:

get drugs with low stock to check what needs to be restocked by using a specified threshold. Assuming what is considered "low stock" is different for each drug, I would also consider including a restock threshold in the drug model and check if quanitity is lower than threshold instead.

Update drug quanitity that increments or decrements quanity by specified amount. This would be good for bulk orders and sales.
