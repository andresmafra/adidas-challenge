# Itinerary Manager
Application responsible for the CRUD and query of the itineraries.

## Configuration
You must configure the Eureka endpoints and the MongoDB params if you run on Standalone mode (without docker-compose). For that, open the `application.yml` file and configure:

`spring.data.mongodb.hostname: "localhost"`
`spring.data.mongodb.port: "27017"`

Optional:
`eureka.instance.hostname: "localhost"`
`eureka.instance.port: "9000"`

## Running 

### Unit tests
For package and execute the unit tests, run:
```sh
mvn clean package
```

### Standlone Run
For standalone execution, execute the command below on the project folder:

```sh
java -jar target/itinerary-manager-0.0.1-SNAPSHOT.jar
```

The service will be running on http://localhost:8080

## API Documentation
All the API documentation is served by Swagger. Follow the url below for more information:
http://localhost:8080/swagger-ui.html

## Samples
You can access the itinerary data samples for fast input [here](https://github.com/andresmafra/adidas-challenge/tree/master/itinerary-manager/src/test/resources/samples.json).

### Postman Collection
[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/41e60256e5e04115f730)
