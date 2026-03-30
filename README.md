# Quantity Measurement App

A Spring Boot REST API application that performs measurement operations like compare, convert, add, subtract, and divide across different unit types — Length, Weight, Volume, and Temperature.

---

## Technologies Used

- Java 17
- Spring Boot 3.2.5
- Spring Data JPA
- MySQL
- Lombok
- Maven

---

## Project Structure

```
QuantityMeasurmentApp/
│
├── controller/
│   └── QuantityMeasurementController.java
│
├── dto/
│   ├── QuantityDTO.java
│   └── TwoQuantityRequest.java
│
├── entity/
│   └── QuantityMeasurementEntity.java
│
├── exception/
│   ├── QuantityMeasurementException.java
│   └── DatabaseException.java
│
├── model/
│   └── QuantityModel.java
│
├── repository/
│   └── QuantityMeasurementRepository.java
│
├── service/
│   ├── IQuantityMeasurementService.java
│   └── QuantityMeasurementServiceImpl.java
│
├── unit/
│   ├── IMeasurableUnit.java
│   ├── LengthUnit.java
│   ├── WeightUnit.java
│   ├── VolumeUnit.java
│   └── TemperatureUnit.java
│
└── QuantityMeasurementApplication.java
```

---

## Supported Units

| Measurement Type | Units |
|-----------------|-------|
| LENGTH | `FEET`, `INCHES`, `YARDS`, `CENTIMETERS` |
| WEIGHT | `MILLIGRAM`, `GRAM`, `KILOGRAM`, `POUND`, `TONNE` |
| VOLUME | `LITRE`, `MILLILITRE`, `GALLON` |
| TEMPERATURE | `CELSIUS`, `FAHRENHEIT`, `KELVIN` |

---

## Setup & Run

### Prerequisites
- Java 17
- MySQL running on `localhost:3306`
- Maven

### Steps

1. Create the database in MySQL:
```sql
CREATE DATABASE quantitymeasurementdb;
```

2. Update `src/main/resources/application.properties` with your MySQL credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/quantitymeasurementdb
spring.datasource.username=root
spring.datasource.password=your_password
```

3. Run the application:
```
mvn spring-boot:run
```

App starts at: `http://localhost:8080`

---

## API Reference

Base URL: `http://localhost:8080/api/quantity`

All POST request bodies use `Content-Type: application/json`.

---

### UC-1: Compare Two Quantities

**POST** `/api/quantity/compare`

Compares two quantities of the same measurement type and returns `true` if they are equal.

**Request:**
```json
{
  "q1": { "value": 1, "unit": "FEET", "measurementType": "LENGTH" },
  "q2": { "value": 12, "unit": "INCHES", "measurementType": "LENGTH" }
}
```

**Response:**
```json
true
```

---

### UC-2: Convert a Quantity

**POST** `/api/quantity/convert`

Converts a quantity from one unit to another of the same measurement type.

**Request:**
```json
{
  "q1": { "value": 1, "unit": "FEET", "measurementType": "LENGTH" },
  "q2": { "value": 0, "unit": "INCHES", "measurementType": "LENGTH" }
}
```

**Response:**
```json
{
  "value": 12.0,
  "unit": "INCHES",
  "measurementType": "LENGTH"
}
```

---

### UC-3: Add Two Quantities

**POST** `/api/quantity/add`

Adds two quantities. Result is in the unit of `q1` by default, or in `targetUnit` if provided.

**Request (without target unit):**
```json
{
  "q1": { "value": 1, "unit": "FEET", "measurementType": "LENGTH" },
  "q2": { "value": 12, "unit": "INCHES", "measurementType": "LENGTH" }
}
```

**Request (with target unit):**
```json
{
  "q1": { "value": 1, "unit": "FEET", "measurementType": "LENGTH" },
  "q2": { "value": 12, "unit": "INCHES", "measurementType": "LENGTH" },
  "targetUnit": { "value": 0, "unit": "INCHES", "measurementType": "LENGTH" }
}
```

**Response:**
```json
{
  "value": 24.0,
  "unit": "INCHES",
  "measurementType": "LENGTH"
}
```

---

### UC-4: Subtract Two Quantities

**POST** `/api/quantity/subtract`

Subtracts `q2` from `q1`. Result is in the unit of `q1` by default, or in `targetUnit` if provided.

**Request:**
```json
{
  "q1": { "value": 2, "unit": "FEET", "measurementType": "LENGTH" },
  "q2": { "value": 6, "unit": "INCHES", "measurementType": "LENGTH" }
}
```

**Response:**
```json
{
  "value": 1.5,
  "unit": "FEET",
  "measurementType": "LENGTH"
}
```

---

### UC-5: Divide Two Quantities

**POST** `/api/quantity/divide`

Divides `q1` by `q2` (both converted to base unit before dividing). Returns a ratio.

**Request:**
```json
{
  "q1": { "value": 2, "unit": "FEET", "measurementType": "LENGTH" },
  "q2": { "value": 1, "unit": "FEET", "measurementType": "LENGTH" }
}
```

**Response:**
```json
2.0
```

---

### UC-6: Get All History

**GET** `/api/quantity/getHistory`

Returns all recorded operations from the database.

---

### UC-7: Find by Operation

**GET** `/api/quantity/operation?operation=ADD`

Returns all records for a specific operation.

Possible values: `COMPARE`, `CONVERT`, `ADD`, `SUBTRACT`, `DIVIDE`

---

### UC-8: Find by Measurement Type

**GET** `/api/quantity/measurementType?measurementType=LENGTH`

Returns all records where the input measurement type matches.

Possible values: `LENGTH`, `WEIGHT`, `VOLUME`, `TEMPERATURE`

---

### UC-9: Find Successful Records by Operation

**GET** `/api/quantity/findByOperation?operation=ADD`

Returns all records for a specific operation where no error occurred (`isError = false`).

---

### UC-10: Count Successful Records by Operation

**GET** `/api/quantity/countByOperation?operation=ADD`

Returns the count of successful records for a specific operation.

**Response:**
```json
5
```

---

### UC-11: Find All Error Records

**GET** `/api/quantity/errorTrue`

Returns all records where an error occurred (`isError = true`).

---

### UC-12: Delete Record by ID

**DELETE** `/api/quantity/deleteById?id=1`

Deletes a specific record by its ID.

**Response:**
```
User deleted successfully
```

---

## Database Schema

Table: `quantity_measurement_entity`

| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT | Primary key |
| this_value | DOUBLE | Input quantity 1 value |
| this_unit | VARCHAR | Input quantity 1 unit |
| this_measurement_type | VARCHAR | Input quantity 1 type |
| that_value | DOUBLE | Input quantity 2 value |
| that_unit | VARCHAR | Input quantity 2 unit |
| that_measurement_type | VARCHAR | Input quantity 2 type |
| operation | VARCHAR | Operation performed |
| result_value | DOUBLE | Result value |
| result_unit | VARCHAR | Result unit |
| result_measurement_type | VARCHAR | Result measurement type |
| result_string | VARCHAR | Result as string (for compare/divide) |
| is_error | BOOLEAN | Whether an error occurred |
| error_message | VARCHAR | Error message if any |
| created_at | DATETIME | Record creation time |
| updated_at | DATETIME | Record last update time |
