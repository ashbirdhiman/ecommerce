# Notification Service Setup

This service consumes user registration events from the user-service via RabbitMQ.

## Prerequisites

1. **RabbitMQ must be running** (started from user-service)
2. **User service must be publishing events**

## Configuration

The notification service is configured to:

- **Connect to RabbitMQ** on `localhost:5672`
- **Listen to** `user-events` exchange
- **Consume messages** with routing key `user.registered`
- **Use queue** `notification-service-queue`

## Running

1. **Start RabbitMQ** (from user-service directory):

   ```bash
   cd ../user-service
   docker-compose up -d
   ```

2. **Start notification service**:

   ```bash
   ./mvnw quarkus:dev
   ```

3. **Start user service** (in another terminal):

   ```bash
   cd ../user-service
   ./mvnw quarkus:dev
   ```

4. **Test by registering a user** in user-service - notification service will receive the event!

## What happens when a user registers:

1. **User service** publishes event to RabbitMQ
2. **Notification service** receives the event
3. **Welcome email** is prepared and "sent" (logged)
4. **Success confirmation** is logged
