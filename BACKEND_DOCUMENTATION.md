# Quantity Measurement Application - Backend Documentation

## Table of Contents
1. [Overview](#overview)
2. [Architecture](#architecture)
3. [Technology Stack](#technology-stack)
4. [Project Structure](#project-structure)
5. [Core Concepts](#core-concepts)
6. [Layer-by-Layer Explanation](#layer-by-layer-explanation)
7. [Authentication & Security](#authentication--security)
8. [Database Design](#database-design)
9. [API Endpoints](#api-endpoints)
10. [Request-Response Flow](#request-response-flow)
11. [Key Design Patterns](#key-design-patterns)
12. [Error Handling](#error-handling)

---

## Overview

The Quantity Measurement Application is a Spring Boot REST API that performs measurement operations (compare, convert, add, subtract, divide) across different unit types: Length, Weight, Volume, and Temperature. It includes user authentication, personalized calculation history, and supports both authenticated and anonymous users.

### Key Features
- Multi-unit measurement operations
- User authentication (Email/Password + Google OAuth2)
- JWT-based session management
- User-specific calculation history
- Anonymous calculator for non-logged-in users
- RESTful API design
- MySQL database integration

---

## Architecture

### N-Tier Architecture (Layered Architecture)

```
┌─────────────────────────────────────────┐
│         Presentation Layer              │
│    (Controllers - REST Endpoints)       │
└─────────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────────┐
│          Service Layer                  │
│    (Business Logic & Validation)        │
└─────────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────────┐
│       Data Access Layer                 │
│    (Repositories - JPA/Hibernate)       │
└─────────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────────┐
│          Database Layer                 │
│         (MySQL Database)                │
└─────────────────────────────────────────┘
```

**Benefits:**
- Separation of concerns
- Easy to test and maintain
- Scalable and modular
- Clear responsibility boundaries

---

## Technology Stack

### Core Technologies
- **Java 17** - Programming language
- **Spring Boot 3.2.5** - Application framework
- **Spring Data JPA** - Database abstraction
- **Hibernate** - ORM (Object-Relational Mapping)
- **MySQL** - Relational database
- **Maven** - Build tool

### Security & Authentication
- **Spring Security** - Security framework
- **JWT (JSON Web Tokens)** - Stateless authentication
- **OAuth2** - Google authentication
- **BCrypt** - Password encryption

### Additional Libraries
- **Lombok** - Reduces boilerplate code
- **HikariCP** - Database connection pooling

---

## Project Structure

```
com.apps.quantitymeasurement/
│
├── auth/                          # Authentication & JWT handling
│   ├── AuthController.java        # Login/Register endpoints
│   ├── AuthService.java           # Authentication business logic
│   ├── JwtFilter.java             # JWT validation filter
│   ├── JwtUtil.java               # JWT token generation/validation
│   └── OAuth2SuccessHandler.java # Google OAuth2 success handler
│
├── config/                        # Configuration classes
│   └── SecurityConfig.java        # Spring Security configuration
│
├── controller/                    # REST API Controllers
│   └── QuantityMeasurementController.java
│
├── dto/                           # Data Transfer Objects
│   ├── ApiResponse.java           # Standard API response wrapper
│   ├── AuthResponse.java          # Authentication response
│   ├── LoginRequest.java          # Login request payload
│   ├── QuantityDTO.java           # Quantity data transfer
│   ├── RegisterRequest.java       # Registration request payload
│   └── TwoQuantityRequest.java    # Two quantities request
│
├── entity/                        # Database entities (JPA)
│   └── QuantityMeasurementEntity.java
│
├── exception/                     # Exception handling
│   ├── DatabaseException.java
│   ├── GlobalExceptionHandler.java
│   └── QuantityMeasurementException.java
│
├── model/                         # Domain models
│   └── QuantityModel.java         # Business logic model
│
├── repository/                    # Data access layer
│   └── QuantityMeasurementRepository.java
│
├── service/                       # Business logic layer
│   ├── AnonymousCalculatorService.java
│   ├── IQuantityMeasurementService.java
│   └── QuantityMeasurementServiceImpl.java
│
├── unit/                          # Unit definitions
│   ├── IMeasurableUnit.java       # Unit interface
│   ├── LengthUnit.java            # Length units enum
│   ├── TemperatureUnit.java       # Temperature units enum
│   ├── VolumeUnit.java            # Volume units enum
│   └── WeightUnit.java            # Weight units enum
│
├── user/                          # User management
│   ├── ProfileController.java     # User profile endpoints
│   ├── UserEntity.java            # User database entity
│   ├── UserRepository.java        # User data access
│   └── UserService.java           # User business logic
│
└── QuantityMeasurementApplication.java  # Main application class
```

---

## Core Concepts

### 1. **Spring Boot Application**
Spring Boot is a framework that simplifies Java application development by providing:
- Auto-configuration
- Embedded web server (Tomcat)
- Dependency management
- Production-ready features

### 2. **Dependency Injection (DI)**
Spring manages object creation and dependencies automatically.

**Example:**
```java
@Service
public class QuantityMeasurementServiceImpl {
    private final QuantityMeasurementRepository repository;
    
    // Spring automatically injects repository
    public QuantityMeasurementServiceImpl(QuantityMeasurementRepository repository) {
        this.repository = repository;
    }
}
```

### 3. **Inversion of Control (IoC)**
Spring container manages the lifecycle of objects (beans).

### 4. **RESTful API**
Architectural style for web services using HTTP methods:
- **GET** - Retrieve data
- **POST** - Create/Submit data
- **PUT** - Update data
- **DELETE** - Remove data

### 5. **JPA (Java Persistence API)**
Standard for ORM in Java. Maps Java objects to database tables.

### 6. **Hibernate**
Implementation of JPA that handles database operations.

---

## Layer-by-Layer Explanation

### 1. **Controller Layer** (Presentation Layer)

**Purpose:** Handle HTTP requests and responses

**Key Annotations:**
- `@RestController` - Marks class as REST controller
- `@RequestMapping` - Base URL path
- `@PostMapping`, `@GetMapping`, `@DeleteMapping` - HTTP method mappings
- `@RequestBody` - Maps JSON to Java object
- `@RequestParam` - Extracts query parameters

**Example: QuantityMeasurementController**
```
Responsibilities:
- Receive HTTP requests
- Validate input (basic)
- Call service layer
- Return HTTP responses
- Handle authenticated vs anonymous users
```

**Endpoints:**
- POST /api/quantity/compare
- POST /api/quantity/convert
- POST /api/quantity/add
- POST /api/quantity/subtract
- POST /api/quantity/divide
- GET /api/quantity/getHistory
- DELETE /api/quantity/deleteById
- DELETE /api/quantity/deleteAll

---

### 2. **Service Layer** (Business Logic Layer)

**Purpose:** Contains business logic and rules

**Key Annotations:**
- `@Service` - Marks class as service component
- `@Transactional` - Manages database transactions

**Components:**

#### IQuantityMeasurementService (Interface)
Defines contract for measurement operations.

#### QuantityMeasurementServiceImpl (Implementation)
- Performs calculations
- Validates measurement types
- Saves history to database
- Associates calculations with users

#### AnonymousCalculatorService
- Performs calculations for non-logged-in users
- Does NOT save to database

**Key Methods:**
- `compare()` - Compares two quantities
- `convert()` - Converts between units
- `add()` - Adds two quantities
- `subtract()` - Subtracts quantities
- `divide()` - Divides quantities
- `getHistory()` - Retrieves user's calculation history
- `deleteAllHistory()` - Deletes user's history

---

### 3. **Repository Layer** (Data Access Layer)

**Purpose:** Database operations

**Key Annotations:**
- `@Repository` - Marks as repository component

**QuantityMeasurementRepository:**
```
Extends JpaRepository<QuantityMeasurementEntity, Long>

Provides:
- findAll() - Get all records
- findById() - Get by ID
- save() - Insert/Update
- deleteById() - Delete by ID
- findAllByUserOrderByIdDesc() - Get user-specific history
- deleteAllByUser() - Delete user-specific history
```

**UserRepository:**
```
Provides:
- findByEmail() - Find user by email
```

---

### 4. **Entity Layer** (Database Models)

**Purpose:** Represent database tables

**Key Annotations:**
- `@Entity` - Marks as JPA entity
- `@Table` - Specifies table name
- `@Id` - Primary key
- `@GeneratedValue` - Auto-increment
- `@Column` - Column mapping
- `@ManyToOne` - Many-to-one relationship
- `@JoinColumn` - Foreign key
- `@Index` - Database index

**QuantityMeasurementEntity:**
```
Fields:
- id (Primary Key)
- user (Foreign Key to UserEntity)
- thisValue, thisUnit, thisMeasurementType
- thatValue, thatUnit, thatMeasurementType
- operation (COMPARE, CONVERT, ADD, etc.)
- resultValue, resultUnit, resultMeasurementType
- resultString
- isError, errorMessage
- createdAt, updatedAt
```

**UserEntity:**
```
Fields:
- id (Primary Key)
- email (Unique)
- name
- password (Encrypted)
- phone
- bio
- provider (LOCAL or GOOGLE)
```

---

### 5. **DTO Layer** (Data Transfer Objects)

**Purpose:** Transfer data between layers, hide internal structure

**Key DTOs:**

**QuantityDTO:**
```
Fields:
- value (double)
- unit (String)
- measurementType (String)
```

**TwoQuantityRequest:**
```
Fields:
- q1 (QuantityDTO)
- q2 (QuantityDTO)
- targetUnit (QuantityDTO, optional)
```

**ApiResponse<T>:**
```
Generic wrapper for all API responses
Fields:
- success (boolean)
- message (String)
- data (T - generic type)
```

**LoginRequest:**
```
Fields:
- email
- password
```

**RegisterRequest:**
```
Fields:
- email
- name
- password
```

**AuthResponse:**
```
Fields:
- token (JWT)
- email
- name
```

---

### 6. **Model Layer** (Business Models)

**QuantityModel<T>:**
```
Generic model for quantity calculations
Fields:
- value (double)
- unit (T extends IMeasurableUnit)

Used internally for business logic
```

---

### 7. **Unit Layer** (Measurement Units)

**IMeasurableUnit (Interface):**
```
Methods:
- getUnitName()
- getMeasurementType()
- getConversionFactor()
```

**LengthUnit (Enum):**
```
FEET(1.0)
INCHES(12.0)
YARDS(0.333333)
CENTIMETERS(30.48)
```

**WeightUnit (Enum):**
```
MILLIGRAM(1000000.0)
GRAM(1000.0)
KILOGRAM(1.0)
POUND(0.453592)
TONNE(0.001)
```

**VolumeUnit (Enum):**
```
LITRE(1.0)
MILLILITRE(1000.0)
GALLON(0.264172)
```

**TemperatureUnit (Enum):**
```
CELSIUS
FAHRENHEIT
KELVIN

Special: Has conversion methods (not just factors)
```

---

## Authentication & Security

### 1. **Spring Security Configuration**

**SecurityConfig.java:**
```
Configures:
- CORS (Cross-Origin Resource Sharing)
- CSRF disabled (for REST API)
- Stateless session (JWT-based)
- Public endpoints (/api/auth/**, /api/quantity/**)
- Protected endpoints (require authentication)
- OAuth2 login
- JWT filter
```

### 2. **JWT (JSON Web Token)**

**How it works:**
1. User logs in with email/password
2. Server validates credentials
3. Server generates JWT token
4. Client stores token (localStorage)
5. Client sends token in Authorization header
6. Server validates token on each request

**JwtUtil.java:**
```
Methods:
- generateToken(email) - Creates JWT
- extractEmail(token) - Gets email from token
- validateToken(token, email) - Validates token
- isTokenExpired(token) - Checks expiration
```

**JwtFilter.java:**
```
Intercepts every request:
1. Extracts JWT from Authorization header
2. Validates token
3. Sets authentication in SecurityContext
4. Allows request to proceed
```

### 3. **Password Encryption**

**BCryptPasswordEncoder:**
- One-way hashing
- Salt added automatically
- Cannot be decrypted
- Secure password storage

### 4. **OAuth2 (Google Login)**

**OAuth2SuccessHandler.java:**
```
When user logs in with Google:
1. Google returns user info
2. Handler extracts email and name
3. Creates/finds user in database
4. Generates JWT token
5. Redirects to frontend with token
```

### 5. **User Authentication Flow**

**Registration:**
```
1. User submits email, name, password
2. AuthService validates input
3. Password encrypted with BCrypt
4. User saved to database
5. JWT token generated
6. Token returned to client
```

**Login:**
```
1. User submits email, password
2. AuthService validates credentials
3. Password verified with BCrypt
4. JWT token generated
5. Token returned to client
```

**Authenticated Request:**
```
1. Client sends request with JWT in header
2. JwtFilter validates token
3. User authenticated
4. Request proceeds to controller
5. Service uses authenticated user
```

---

## Database Design

### Tables

**users:**
```
id (PK, AUTO_INCREMENT)
email (UNIQUE, NOT NULL)
name
password
phone
bio
provider (ENUM: LOCAL, GOOGLE)
```

**quantity_measurement_entity:**
```
id (PK, AUTO_INCREMENT)
user_id (FK -> users.id, NOT NULL)
this_value
this_unit
this_measurement_type
that_value
that_unit
that_measurement_type
operation (COMPARE, CONVERT, ADD, SUBTRACT, DIVIDE)
result_value
result_unit
result_measurement_type
result_string
is_error
error_message
created_at
updated_at

Indexes:
- idx_user_id
- idx_operation
- idx_measurement_type
- idx_created_at
```

### Relationships

```
users (1) ----< (Many) quantity_measurement_entity

One user can have many calculations
Each calculation belongs to one user
```

---

## API Endpoints

### Authentication Endpoints

**POST /api/auth/register**
```
Request:
{
  "email": "user@example.com",
  "name": "John Doe",
  "password": "password123"
}

Response:
{
  "success": true,
  "message": "User registered successfully",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIs...",
    "email": "user@example.com",
    "name": "John Doe"
  }
}
```

**POST /api/auth/login**
```
Request:
{
  "email": "user@example.com",
  "password": "password123"
}

Response:
{
  "success": true,
  "message": "Login successful",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIs...",
    "email": "user@example.com",
    "name": "John Doe"
  }
}
```

### Quantity Measurement Endpoints

**POST /api/quantity/compare**
```
Request:
{
  "q1": {
    "value": 12,
    "unit": "INCHES",
    "measurementType": "LENGTH"
  },
  "q2": {
    "value": 1,
    "unit": "FEET",
    "measurementType": "LENGTH"
  }
}

Response:
{
  "success": true,
  "message": "Comparison successful",
  "data": true
}
```

**POST /api/quantity/convert**
```
Request:
{
  "q1": {
    "value": 1,
    "unit": "FEET",
    "measurementType": "LENGTH"
  },
  "q2": {
    "value": 0,
    "unit": "INCHES",
    "measurementType": "LENGTH"
  }
}

Response:
{
  "success": true,
  "message": "Conversion successful",
  "data": {
    "value": 12.0,
    "unit": "INCHES",
    "measurementType": "LENGTH"
  }
}
```

**POST /api/quantity/add**
```
Request:
{
  "q1": {
    "value": 2,
    "unit": "INCHES",
    "measurementType": "LENGTH"
  },
  "q2": {
    "value": 2,
    "unit": "INCHES",
    "measurementType": "LENGTH"
  }
}

Response:
{
  "success": true,
  "message": "Addition successful",
  "data": {
    "value": 4.0,
    "unit": "INCHES",
    "measurementType": "LENGTH"
  }
}
```

**GET /api/quantity/getHistory**
```
Headers:
Authorization: Bearer <JWT_TOKEN>

Response:
{
  "success": true,
  "message": "History retrieved successfully",
  "data": [
    {
      "id": 1,
      "thisValue": 12.0,
      "thisUnit": "INCHES",
      "operation": "COMPARE",
      "resultString": "True",
      "createdAt": "2026-04-07T19:54:00"
    }
  ]
}
```

**DELETE /api/quantity/deleteById?id=1**
```
Headers:
Authorization: Bearer <JWT_TOKEN>

Response:
{
  "success": true,
  "message": "Record deleted successfully",
  "data": "Record deleted successfully"
}
```

**DELETE /api/quantity/deleteAll**
```
Headers:
Authorization: Bearer <JWT_TOKEN>

Response:
{
  "success": true,
  "message": "All history deleted successfully",
  "data": "All history deleted successfully"
}
```

### User Profile Endpoints

**GET /api/profile**
```
Headers:
Authorization: Bearer <JWT_TOKEN>

Response:
{
  "success": true,
  "message": "Profile retrieved successfully",
  "data": {
    "email": "user@example.com",
    "name": "John Doe",
    "phone": "1234567890",
    "bio": "Software Developer"
  }
}
```

**PUT /api/profile**
```
Headers:
Authorization: Bearer <JWT_TOKEN>

Request:
{
  "name": "John Updated",
  "phone": "9876543210",
  "bio": "Senior Developer"
}

Response:
{
  "success": true,
  "message": "Profile updated successfully",
  "data": {
    "email": "user@example.com",
    "name": "John Updated",
    "phone": "9876543210",
    "bio": "Senior Developer"
  }
}
```

---

## Request-Response Flow

### Example: Adding Two Quantities (Authenticated User)

**Step-by-Step Flow:**

1. **Client Request:**
   ```
   POST /api/quantity/add
   Headers: Authorization: Bearer <JWT>
   Body: { q1: {...}, q2: {...} }
   ```

2. **JwtFilter (Security Layer):**
   - Extracts JWT from Authorization header
   - Validates token
   - Extracts email from token
   - Loads user from database
   - Sets authentication in SecurityContext

3. **QuantityMeasurementController:**
   - Receives request
   - Checks if user is authenticated
   - Calls `service.add(q1, q2)`

4. **QuantityMeasurementServiceImpl:**
   - Gets current authenticated user from SecurityContext
   - Converts DTOs to QuantityModel objects
   - Validates measurement types match
   - Performs calculation:
     - Converts both to base units
     - Adds values
     - Converts result back to q1's unit
   - Creates QuantityMeasurementEntity
   - Sets user on entity
   - Saves to database via repository

5. **QuantityMeasurementRepository:**
   - Executes SQL INSERT
   - Returns saved entity

6. **Service returns QuantityDTO**

7. **Controller wraps in ApiResponse**

8. **Spring converts to JSON**

9. **Client receives response**

---

## Key Design Patterns

### 1. **Dependency Injection Pattern**
Spring automatically injects dependencies through constructors.

### 2. **Repository Pattern**
Abstracts data access logic from business logic.

### 3. **Service Layer Pattern**
Separates business logic from controllers.

### 4. **DTO Pattern**
Transfers data between layers without exposing entities.

### 5. **Strategy Pattern**
Different unit types (Length, Weight, Volume, Temperature) implement same interface.

### 6. **Factory Pattern**
Creating QuantityModel objects based on measurement type.

### 7. **Filter Pattern**
JwtFilter intercepts requests for authentication.

### 8. **Singleton Pattern**
Spring beans are singletons by default.

---

## Error Handling

### Global Exception Handler

**GlobalExceptionHandler.java:**
```
Catches exceptions globally and returns consistent error responses.

Handles:
- QuantityMeasurementException
- DatabaseException
- General exceptions
```

**Example Error Response:**
```json
{
  "success": false,
  "message": "Cannot add different measurement types",
  "data": null
}
```

### Custom Exceptions

**QuantityMeasurementException:**
- Thrown for business logic errors
- Example: "Cannot compare different measurement types"

**DatabaseException:**
- Thrown for database errors
- Example: "Failed to save calculation"

---

## Important Concepts to Understand

### 1. **Annotations**
Special markers that provide metadata to Spring.

**Common Annotations:**
- `@SpringBootApplication` - Main application class
- `@RestController` - REST controller
- `@Service` - Service component
- `@Repository` - Repository component
- `@Entity` - JPA entity
- `@Autowired` - Dependency injection
- `@GetMapping`, `@PostMapping` - HTTP mappings
- `@RequestBody` - Maps JSON to object
- `@PathVariable` - Extracts URL path variable
- `@RequestParam` - Extracts query parameter

### 2. **Spring Boot Auto-Configuration**
Spring Boot automatically configures:
- Database connection
- Web server (Tomcat)
- JPA/Hibernate
- Security
- JSON serialization

### 3. **application.properties**
Configuration file for:
- Database connection
- Server port
- JPA settings
- JWT secret
- OAuth2 credentials

### 4. **Lombok**
Reduces boilerplate code:
- `@Data` - Generates getters, setters, toString, equals, hashCode
- `@NoArgsConstructor` - Generates no-argument constructor
- `@AllArgsConstructor` - Generates all-argument constructor

### 5. **JPA Lifecycle Callbacks**
- `@PrePersist` - Before saving new entity
- `@PreUpdate` - Before updating entity
- Used for setting createdAt and updatedAt timestamps

### 6. **Transactional Management**
`@Transactional` ensures database operations are atomic (all or nothing).

---

## Learning Path

### Beginner Level
1. Understand Spring Boot basics
2. Learn REST API concepts
3. Study HTTP methods (GET, POST, PUT, DELETE)
4. Understand JSON format
5. Learn about annotations

### Intermediate Level
1. Study N-Tier architecture
2. Understand dependency injection
3. Learn JPA and Hibernate
4. Study database relationships
5. Understand DTOs vs Entities

### Advanced Level
1. Master Spring Security
2. Understand JWT authentication
3. Learn OAuth2 flow
4. Study design patterns
5. Understand transaction management

---

## Testing the API

### Using Postman or Thunder Client

**1. Register a user:**
```
POST http://localhost:8080/api/auth/register
Body: { "email": "test@test.com", "name": "Test", "password": "test123" }
```

**2. Login:**
```
POST http://localhost:8080/api/auth/login
Body: { "email": "test@test.com", "password": "test123" }
Copy the token from response
```

**3. Perform calculation:**
```
POST http://localhost:8080/api/quantity/add
Headers: Authorization: Bearer <paste_token_here>
Body: { "q1": {...}, "q2": {...} }
```

**4. Get history:**
```
GET http://localhost:8080/api/quantity/getHistory
Headers: Authorization: Bearer <paste_token_here>
```

---

## Common Issues & Solutions

### Issue 1: "Access denied for user 'root'@'localhost'"
**Solution:** Update MySQL password in application.properties

### Issue 2: "Port 8080 already in use"
**Solution:** Change port in application.properties or stop other application

### Issue 3: "Foreign key constraint fails"
**Solution:** Delete old data from quantity_measurement_entity table

### Issue 4: "JWT token expired"
**Solution:** Login again to get new token

### Issue 5: "CORS error"
**Solution:** Check CORS configuration in SecurityConfig

---

## Best Practices Implemented

1. **Separation of Concerns** - Each layer has specific responsibility
2. **DRY (Don't Repeat Yourself)** - Reusable code
3. **SOLID Principles** - Clean code design
4. **RESTful Design** - Standard API conventions
5. **Security First** - JWT + BCrypt + OAuth2
6. **Error Handling** - Global exception handler
7. **Database Indexing** - Optimized queries
8. **User Privacy** - User-specific data isolation

---

## Conclusion

This backend application demonstrates:
- Modern Spring Boot development
- RESTful API design
- Secure authentication
- Clean architecture
- Database integration
- User management
- Business logic implementation

The code is production-ready, scalable, and follows industry best practices.

---

## Further Reading

- Spring Boot Documentation: https://spring.io/projects/spring-boot
- Spring Security: https://spring.io/projects/spring-security
- JWT: https://jwt.io/
- JPA/Hibernate: https://hibernate.org/
- REST API Design: https://restfulapi.net/

---

**Generated for:** Quantity Measurement Application Backend  
**Version:** 1.0  
**Last Updated:** April 2026
