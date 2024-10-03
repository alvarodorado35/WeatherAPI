# Weather API Project

## Description
This project is an API that receives latitude and longitude coordinates and returns the current temperature. It utilizes the Open Meteo service and stores data in a MongoDB database as a cache. There's also an endpoint which can delete selected data by latitude and longitude.

## Table of Contents
- [Technologies Used](#technologies-used)
- [Initial Setup](#initial-setup)
- [MongoDB Installation](#mongodb-installation)
- [Project Configuration](#project-configuration)
- [Running the Project](#running-the-project)
- [API Usage](#api-usage)
- [Swagger Documentation](#swagger-documentation)
- [Delete Data](#delete-data)
- [Contributions](#contributions)
- [Contact](#contact)

## Technologies Used
- Spring Boot
- MongoDB
- Spring Data MongoDB
- Swagger

## Initial Setup
1. Go to [Spring Initializr](https://start.spring.io/).
2. Configure the project with the following options:
   - Group: `com.alvaro`
   - Artifact: `techTaskVodafone`
   - Dependencies: `Spring Web`, `Spring Data MongoDB`
   - Maven 
   - Java 17 
   - Jar packaging

## MongoDB Installation
### Windows
1. Download and install MongoDB from [the official page](https://www.mongodb.com/try/download/community).
2. Follow the instructions to start the MongoDB server.

### macOS

brew tap mongodb/brew
brew install mongodb-community
brew services start mongodb/brew/mongodb-community


### Linux
sudo apt-get install -y mongodb
sudo systemctl start mongodb

## Project Configuration 
Configure the MongoDB connection in src/main/resources/application.properties 

spring.data.mongodb.uri=mongodb://localhost:27017/weatherdb

## Running the project 
    - Ensure MOngoDB is running. 
    - From the project directory, execute: 
        mvn spring-boot:run 

## API Usage 
    - GET /weather?latitude={lat}&longitude={long} 
        Returns the current temperature in JSON format. 
        Example response: 
        {
            "latitude": 36.72016,
            "longitude": -4.42034,
            "temperature": 25.2
        } 

## Swagger Documentation 
        Access the interactive Swagger documentation at:
            http://localhost:8080/swagger-ui/index.html

## Delete Data
    - DELETE /weather?latitude={lat}&longitude={long} 
        Deletes the corresponding entry from the database.

## Contributions
If you would like to contribute, please fork the repository and submit a pull request. 

## Contact
For questions or support, contact adorado51@gmail.com 
