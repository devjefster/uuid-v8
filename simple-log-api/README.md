# Simple Log API

## Overview
This is a simple log service built with **Spring Boot** to demonstrate how to generate and use **UUIDv8** as a primary key. The service allows users to create logs and retrieve logs within a specific time range.

## Features
- Uses **UUIDv8** as a unique identifier for logs
- Provides endpoints to:
    - Create a new log entry
    - Retrieve a log entry by ID
    - Get logs within a specific time range
- Uses **H2 in-memory database** for storage
- Built with **Spring Boot 3.4.2**

## Technologies Used
- **Java 21**
- **Spring Boot 3.4.2**
- **Spring Data JPA**
- **H2 Database**
- **Lombok**

## Installation
### Prerequisites
Ensure you have the following installed:
- Java 21
- Maven

### Build and Run the Application
```sh
mvn spring-boot:run
```

The application will start on **http://localhost:8080**.

## API Endpoints

### 1. Create a Log Entry
**POST** `/logs`

#### Request Parameters:
- `message` (String) – The log message.

#### Example Request:
```sh
curl -X POST "http://localhost:8080/logs?message=HelloUUIDv8"
```
#### Example Response:
```json
{
  "id": "c6a41864-86b2-4fd9-a328-dfb564d99e7e",
  "timestamp": "2024-02-12T10:00:00Z",
  "message": "HelloUUIDv8"
}
```

### 2. Get Log Entry by ID
**GET** `/logs/{id}`

#### Example Request:
```sh
curl -X GET "http://localhost:8080/logs/c6a41864-86b2-4fd9-a328-dfb564d99e7e"
```
#### Example Response:
```json
{
  "id": "c6a41864-86b2-4fd9-a328-dfb564d99e7e",
  "timestamp": "2024-02-12T10:00:00Z",
  "message": "HelloUUIDv8"
}
```

### 3. Get Logs in Time Range
**GET** `/logs?start={start}&end={end}`

#### Request Parameters:
- `start` (ISO 8601 format) – Start time.
- `end` (ISO 8601 format) – End time.

#### Example Request:
```sh
curl -X GET "http://localhost:8080/logs?start=2024-02-12T00:00:00Z&end=2024-02-12T23:59:59Z"
```

#### Example Response:
```json
[
  {
    "id": "c6a41864-86b2-4fd9-a328-dfb564d99e7e",
    "timestamp": "2024-02-12T10:00:00Z",
    "message": "HelloUUIDv8"
  }
]
```

## UUIDv8 Implementation
The UUIDv8 generation is handled in the `UUIDv8Generator` utility class:

```java
public class UUIDv8Generator {
    
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public static UUID generate() {
      long timestamp = Instant.now().toEpochMilli(); // Higher precision timestamp
      long randomBits1 = SECURE_RANDOM.nextLong();
      long randomBits2 = SECURE_RANDOM.nextLong();
  
      ByteBuffer buffer = ByteBuffer.allocate(16);
      buffer.putLong(timestamp);
      buffer.putLong(randomBits1 ^ randomBits2); // More entropy in the least significant bits
  
      byte[] bytes = buffer.array();
  
      // Set version to 8 (UUIDv8)
      bytes[6] = (byte) ((bytes[6] & 0x0F) | 0x80);
      // Set variant to RFC 4122 standard
      bytes[8] = (byte) ((bytes[8] & 0x3F) | 0x80);
  
      ByteBuffer uuidBuffer = ByteBuffer.wrap(bytes);
      return new UUID(uuidBuffer.getLong(), uuidBuffer.getLong());
    }
}
```

## Configuration
The application uses an **H2 in-memory database**, configured in `application.properties`:

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

## License
This project is licensed under the MIT License.
