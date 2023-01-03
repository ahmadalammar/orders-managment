# Orders Managment Microservices

## Intro
In this example i design a simple event-driven distrbuted system that simulate order managment system using spring-cloud, spring-native, apache-camel and saga design pattern.
This is just a show case on how to use apache camel with saga in microservices.

## System design
![order-managment-system-design](https://user-images.githubusercontent.com/17546520/209901887-cd51af6f-5429-4e4b-b8bc-50cddfdde708.png)

## System Install
* Start kafka server by running:
  `cd kafka-server`
  `docker-compose up -d`

* Start LRA coordinator:
  `cd narayana-lra-coordinator`
  `java -Dquarkus.http.port=50000 -jar target/quarkus-app/quarkus-run.jar`

* Run `discovery-server`, `customer-service`, `gateway-service`, `order-service`

* Run simple API test by calling `http://localhost:9999/api/order`

## System flow
![system-flow](https://user-images.githubusercontent.com/17546520/210304592-0ef89761-0b04-4146-ae5e-b976e1b4f7de.png)
