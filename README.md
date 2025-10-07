# Ecommerce Microservices

A simple microservices platform with user, order, and notification services connected via RabbitMQ.

## Quick Start

**Prerequisites:** Docker, Docker Compose

```bash
# Start all services
docker-compose up -d

# Check logs
docker-compose logs -f
```

## Services

- **User Service**: http://localhost:8080 - User registration
- **Order Service**: http://localhost:8082 - Order processing
- **Notification Service**: http://localhost:8081 - Email notifications
- **RabbitMQ UI**: http://localhost:15672 (guest/guest)

## Testing

**Register a user:**

```bash
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","email":"john@example.com","password":"password123"}'
```

**Create an order:**

```bash
curl -X POST http://localhost:8080/users/orders \
  -H "Content-Type: application/json" \
  -d '{"customerId":"john@example.com","items":[{"name":"Pizza","price":15.99,"quantity":1}]}'
```

## Development

```bash
# Run individual service
cd user-service
./mvnw quarkus:dev

# Stop all services
docker-compose down
```
