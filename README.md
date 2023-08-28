# ed-cqrs-saga

## Event-Driven (ED) Architecture

Event-Driven Architecture is a design approach where software components communicate and react to events, rather than being tightly coupled through direct method calls. In an ED architecture, components generate events when specific actions occur, and other components subscribe to these events to take appropriate actions. This decoupled nature enhances scalability, flexibility, and responsiveness in complex systems.

## Command Query Responsibility Segregation (CQRS)

CQRS is a design pattern that separates the read and write operations of an application's data model into distinct paths. It recognizes that reading data (queries) and modifying data (commands) have different requirements and usage patterns. By segregating these responsibilities, CQRS enables optimization of each path independently. This can lead to improved performance, scalability, and maintainability, especially in systems with complex data processing needs.

## Saga Pattern

The Saga pattern is a technique for managing long-lived and distributed transactions in microservices architectures. Traditional distributed transactions can become complex and hard to maintain. The Saga pattern breaks down a distributed transaction into a sequence of smaller, localized transactions, or "sagas." Each saga is responsible for coordinating a specific piece of the overall transaction. If an error occurs, sagas can compensate and undo previously completed actions, maintaining data consistency and reliability across the system.

These architectural concepts — **Event-Driven Architecture, CQRS, and the Saga pattern** — play a crucial role in building robust, scalable, and maintainable microservices-based systems. In the context of the ed-cqrs-saga project, these concepts are employed to handle interactions between various microservices and ensure the overall system's reliability and responsiveness.

### Table of Contents:

1. Introduction
2. Technologies Used
3. Modules
   * Core Module
   * API Gateway Module
   * Discovery Server Module
   * Order Service Module
   * Payment Service Module
   * Product Service Module
   * User Service Module
4. Screenshots

### Introduction

Welcome to the ed-cqrs-saga project! This project showcases an implementation of the CQRS (Command Query Responsibility Segregation) and Saga pattern using Spring Boot, Java 17, and the Axon Framework. The architecture is designed to handle complex interactions between various microservices.

![img_4.png](screenshots%2Fimg_4.png)

![img_7.png](screenshots%2Fimg_7.png)

![img_23.png](screenshots%2Fimg_23.png)

![img_25.png](screenshots%2Fimg_25.png)

Here's the Docker command to run Axon Server
`docker run -d --name my-axon-server -p 8024:8024 -p 8124:8124 axoniq/axonserver`

![img_2.png](screenshots%2Fimg_2.png)

![img_2.1.png](screenshots%2Fimg_2.1.png)

![img_3.png](screenshots%2Fimg_3.png)



### Technologies Used
* Spring Boot 3
  * Java 17
  * Gradle
  * Axon Framework
  * Docker (Axon Server)
  * Spring Cloud (Eureka for service discovery)
  * H2 Database
  * Guava

## Modules

## Core Module

  The **core** module contains the shared classes, commands, events, and queries used across multiple microservices.

* **Commands**: ReserveProductCommand, ProcessPaymentCommand, CancelProductReservationCommand
* **Events**: PaymentProcessedEvent, ProductReservationCancelledEvent, ProductReservedEvent
* **Model**: PaymentDetails, User
* **Queries**: FetchUserPaymentDetailsQuery

## API Gateway Module

The api-gateway module acts as the entry point to the application, utilizing Spring Cloud's Gateway and Eureka for service discovery.

Port: 8765
Registers with Eureka for service discovery

## Discovery Server Module

The discovery-server module is responsible for running the Eureka server for service registration and discovery.

Port: 8761

![img_1.png](discovery-server%2Fsrc%2Fmain%2Fresources%2Fscreenshots%2Fimg_1.png)


## Order Service Module

The order-service module manages order-related functionalities.

Port: Randomly assigned
Database: H2
Registers with Eureka for service discovery
Axon Event Processor: order-group (subscribing mode)

![img_1.png](order-service%2Fsrc%2Fmain%2Fresources%2Fscreenshots%2Fimg_1.png)


## Payment Service Module

The payment-service module handles payment processing.

Port: Randomly assigned
Database: H2
Registers with Eureka for service discovery

## Product Service Module

The product-service module manages product-related operations.

Port: Randomly assigned
Database: H2
Registers with Eureka for service discovery
Axon Event Processor: product-group (tracking mode)
Debugging: Detailed logging for AxonServerEventStore

![img_6.png](product-service%2Fsrc%2Fmain%2Fresources%2Fscreenshots%2Fimg_6.png)



## User Service Module

The user-service module deals with user-related actions.

Port: Randomly assigned
Registers with Eureka for service discovery

## Screenshots

For visual insights into the system's architecture, schemas, and flow, please refer to the provided screenshots.

Feel free to explore the different modules, their configurations, and interaction patterns within the project. If you have any questions or need assistance, don't hesitate to reach out.

Happy coding!