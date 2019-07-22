# Itinerary Calculator
Application responsible for dealign with ItineraryManager and calculate the best itineraries between two cities.

## Configuration
You must configure the Eureka endpoints and the Itinerary Manager url if you run on Standalone mode (without docker-compose). For that, open the `application.yml` file and configure:

`feign.itineraryManager.url: "http://serverUrl:serverPort"`

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
java -jar target/itinerary-calculator-0.0.1-SNAPSHOT.jar
```

The service will be running on http://localhost:8081

## API Documentation
All the API documentation is served by Swagger. Follow the url below for more information:
http://localhost:8081/swagger-ui.html

### Postman Collection
[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/41e60256e5e04115f730)
