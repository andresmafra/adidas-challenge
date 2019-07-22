# Adidas Challenge Code
This solution is based on Eureka as Discovery Service, Spring Cloud, MongoDB as NOSql database and Docker as a PAAS. The architecture is explained on the presentation below:
* [Solution Architecture](https://slides.com/andrevieiramafra/code-challenge)

For this challenge, a couple of microservices was developed:

## Itinerary Manager

This microservice is responsible for managing the itineraries and persisting then on database via CRUD operations.
All the documentation regarding this module can be found on it's [README](https://github.com/andresmafra/adidas-challenge/tree/master/itinerary-manager).

## Itinerary Calculator

This microservice is responsible for calculating the best routes based on a city and destiny city.
All the documentation regarding this module can be found on it's [README](https://github.com/andresmafra/adidas-challenge/tree/master/itinerary-calculator).

## Prerequisites
* Java 1.8
* Docker Engine 18
* Docker Compose 1.23
* Maven 3.3

## Instalation and Execution
The modules are served in a separated docker instance. First of all, you need to build each module (Manager and Calculator) with `mvn` command below.

```sh
$ mvn install dockerfile:build
```

Then, run all the docker containers and instances with: (the folder that has the `docker-compose.yml` file)
```sh
docker-compose up
```

If everything is ok, the Eureka is up and running:
http://localhost:8761

The applications `ItineraryManager` and `ItineraryCalculator` should appear on instances list.

# Executing the APIs
[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/41e60256e5e04115f730)
