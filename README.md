# eHealth backend

This repository contains a set of Spring microservices built using the Spring Boot framework. The microservices included are the `measurement-service`, `device-service`, and `user-service`. It also integrates Keycloak as the Identity and Access Management (IAM) solution, Eureka as the Discovery Server, and an API Gateway for routing and managing requests.

## Microservices

### Measurement Service

The `measurement-service` microservice is responsible for managing measurements recorded by various devices. It provides RESTful APIs for creating, retrieving, updating, and deleting measurements

### Device Service

The `device-service` microservice handles device management. It allows users to create, retrieve, update, and delete devices. It also provides APIs for associating measurements with devices and retrieving measurements for a specific device.

### User Service

The `user-service` microservice is responsible for managing user information. It allows users to register, authenticate, and retrieve user details. It integrates with Keycloak for user authentication and authorization.

## Keycloak

Keycloak is used as the IAM solution in this microservices architecture. It provides features for user authentication, authorization, and centralized user management. The `user-service` integrates with Keycloak to handle user-related operations securely.

## Eureka

Eureka is employed as the Discovery Server in this setup. It facilitates service registration and discovery, allowing microservices to locate and communicate with each other easily. With Eureka, services can dynamically discover and interact with one another without explicit configuration.

## API Gateway

The API Gateway serves as the entry point for all incoming requests in this microservices architecture. It handles request routing, load balancing, and security enforcement. The API Gateway is responsible for routing requests to the appropriate microservices based on the requested API endpoint.

## Prerequisites

- Java Development Kit (JDK) 17 or higher
- Maven build tool
- Docker (optional for containerization)

## Getting Started

To get started with the microservices, follow these steps:

1. Clone this repository:

```shell
git clone https://github.com/your-username/your-repo.git
```

2. Build the microservices:

```shell
cd your-repo
mvn clean install
```

3. Start the services:

   - Start the Keycloak server by following the instructions provided by Keycloak.
   ```shell
   ./kc.bat start-dev --http-port=8081
   ```
   - Start the API Gateway by running the `api-gateway` module:
   ```shell
   cd api-gateway
   mvn spring-boot:run
   ```
   - Start the Eureka server by running the `discovery-server` module:

   ```shell
   cd discovery-server
   mvn spring-boot:run
   ```

   - Start each microservice by running their respective modules:

   ```shell
   cd measurement-service
   mvn spring-boot:run

   cd device-service
   mvn spring-boot:run

   cd user-service
   mvn spring-boot:run
   ```

4. Access the microservices:

Ports for microservices are set randomly. So if you need access to microservice, you can check port by discovery-server: `8761`

## Configuration

Each microservice and the API Gateway can be configured via their respective  `application.yml` or `application-docker.yml` files. Update the configuration files as per your environment and requirements.

## Docker
   - Run a docker compose:
    ```shell
    docker-compose up -d
    ```
    - Add this line to `/etc/hosts` on Linux or `C:\Windows\System32\drivers\etc\hosts` on Windows:
     ```shell
     127.0.0.1 keycloak
     ```
## Usage

You can use this project as template of microservices architecture in Spring
