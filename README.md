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

## Use Cases & Branches

| UC | Branch | Description |
|----|--------|-------------|
| UC-1 | `feature/UC1-FeetEquality` | Compare two quantities in FEET |
| UC-2 | `feature/UC2-InchEquality` | Compare two quantities in INCHES |
| UC-3 | `feature/UC3-GenericLength` | Generic length comparison |
| UC-4 | `feature/UC4-UnitSupport` | Support for multiple length units |
| UC-5 | `feature/UC5-UnitConversion` | Convert between length units |
| UC-6 | `feature/UC6-UnitAddition` | Add two length quantities |
| UC-7 | `feature/UC7-TargetUnitAddition` | Add with a target unit |
| UC-8 | `feature/UC8-StandAloneUnit` | Standalone unit support |
| UC-9 | `feature/UC9-WeightMeasurement` | Weight measurement support |
| UC-10 | `feature/UC10-GenericQuantityClassWithUnitInterface` | Generic quantity class with unit interface |
| UC-11 | `feature/UC11-VolumeMeasurementEqualityConversionAndAddition` | Volume measurement — equality, conversion, addition |
| UC-12 | `feature/UC12-SubtractionAndDivisionOperationsOnQuantityMeasurements` | Subtraction and division operations |
| UC-13 | `feature/UC13-Centralized-Arithmetic-Logic-to-Enforce-DRY-in-Quantity-Operations` | Centralized arithmetic logic (DRY principle) |
| UC-14 | `feature/UC14-Temperature-Measurement-with-Selective-Arithmetic-Support-and-Measurable-Refactoring` | Temperature measurement with selective arithmetic |
| UC-15 | `feature/UC15-N-Tier` | N-Tier architecture refactoring |
| UC-16 | `feature/UC16-Database-Integration-with-JDBC` | Database integration with JDBC |
| UC-17 | `feature/UC17-SpringBootBackend` | Spring Boot backend with REST APIs |

