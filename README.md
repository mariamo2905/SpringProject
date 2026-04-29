# HR Management API

A Spring Boot REST API for managing HR data, built as part of the IBSU Java GEO Class 2026.

## Tech Stack

- **Java 17+**
- **Spring Boot 4.0.1**
- **Spring Data JPA** — ORM & repository layer
- **Spring Security + JWT** — authentication & authorization
- **H2 Database** — in-memory database for development/testing
- **MySQL** — production database
- **Maven** — build & dependency management

## Project Structure

```
src/main/java/ge/ibsu/demo/
├── DemoApplication.java          # Entry point
├── controllers/                  # REST API endpoints
│   ├── BaseController.java       # Global exception handling
│   ├── DepartmentController.java # Department CRUD & search
│   ├── EmployeeController.java   # Employee CRUD & search
│   └── TestController.java       # Health check
├── dto/                          # Data Transfer Objects
│   ├── DepartmentResponse.java   # Department search response
│   ├── SearchDepartment.java     # Department search filters
│   ├── SearchEmployee.java       # Employee search filters
│   ├── RequestData.java          # Generic request wrapper
│   ├── Paging.java               # Pagination parameters
│   ├── LoginData.java            # Login request
│   ├── RegistrationRequest.java  # Registration request
│   └── AuthenticationResponse.java # JWT token response
├── entities/                     # JPA entities
│   ├── Region.java               # HR region
│   ├── Country.java              # Country (linked to Region)
│   ├── Location.java             # Office location (linked to Country)
│   ├── Department.java           # Department (linked to Location & Manager)
│   ├── Employee.java             # Employee (linked to Department)
│   └── User.java                 # Application user (for auth)
├── repositories/                 # Spring Data JPA repositories
│   ├── DepartmentRepository.java
│   ├── EmployeeRepository.java
│   └── UserRepository.java
├── services/                     # Business logic
│   ├── DepartmentService.java
│   └── EmployeeService.java
├── security/                     # JWT authentication
│   ├── config/
│   │   ├── ApplicationConfig.java
│   │   ├── JwtAuthenticationFilter.java
│   │   └── SecurityConfiguration.java
│   ├── controller/
│   │   └── UserController.java
│   └── services/
│       ├── JwtService.java
│       └── UserService.java
└── utils/
    └── GeneralUtil.java          # Reflection utilities
```

## Entity Relationships

```
Region (1) ──── (N) Country (1) ──── (N) Location (1) ──── (N) Department
                                                                    │
                                                          Manager ──┘ (Employee)
```

- **Region** → has many Countries
- **Country** → belongs to a Region, has many Locations
- **Location** → belongs to a Country, has many Departments
- **Department** → belongs to a Location, has one Manager (Employee)
- **Employee** → belongs to a Department

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven (or use the included Maven wrapper `./mvnw`)

### Run the Application

```bash
./mvnw spring-boot:run
```

The server starts on **http://localhost:8000**.

Sample HR data (regions, countries, locations, departments, employees) is automatically loaded on startup via `src/main/resources/data.sql`.

## API Endpoints

### Authentication (No token required)

#### Register a new user
```bash
curl -X POST http://localhost:8000/api/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Name",
    "lastName": "LastName",
    "email": "[EMAIL_ADDRESS]",
    "password": "password123",
    "role": "ROLE_NAME"
  }'
```

**Response:**
```json
{
  "token": "your_token..."
}
```

#### Login
```bash
curl -X POST http://localhost:8000/api/users/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "[EMAIL_ADDRESS]",
    "password": "password123"
  }'
```

### Departments (Token required)

> Add the header `-H "Authorization: Bearer <YOUR_TOKEN>"` to all requests below.

#### Get all departments
```
GET /api/departments/all
```

#### Get department by ID
```
GET /api/departments/{id}
```

#### Get employees in a department
```
GET /api/departments/{id}/employees
```

#### Search departments (with filtering & pagination)
```bash
curl -X POST http://localhost:8000/api/departments/search \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <YOUR_TOKEN>" \
  -d '{
    "data": {
      "country": "United States of America",
      "city": "Seattle"
    },
    "paging": {
      "page": 1,
      "size": 10
    }
  }'
```

**Filters** (leave empty string `""` to skip a filter):
- `country` — filter by country name (e.g., "United States of America", "Georgia")
- `city` — filter by city name (e.g., "Seattle", "Tbilisi")

**Response:**
```json
{
  "content": [
    {
      "departmentName": "Administration",
      "managerFullName": "Jennifer Whalen",
      "country": "United States of America",
      "city": "Seattle",
      "streetAddress": "1297 Via Cola di Rie"
    }
  ],
  "totalElements": 1,
  "totalPages": 1,
  "number": 0,
  "size": 10
}
```

### Employees (Token required)

#### Search employees
```
POST /api/employees/search
```

#### Get employee by ID
```
GET /api/employees/{id}
```

## Sample Data

The application loads the following test data on startup:

| Department | Manager | City | Country |
|---|---|---|---|
| Administration | Jennifer Whalen | Seattle | United States of America |
| Marketing | Michael Hartstein | London | United Kingdom |
| IT | Alexander Hunold | Tbilisi | Georgia |
| Sales | John Russell | San Francisco | United States of America |
| Engineering | Karen Partners | Tokyo | Japan |

## Configuration

Configuration is in `src/main/resources/application.properties`:

```properties
server.port=8000

# H2 (development)
spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
```

### Switching to MySQL (production)

Replace the H2 settings with:

```properties
spring.datasource.url=jdbc:mysql://YOUR_HOST:3306/hr?useSSL=false&serverTimezone=UTC
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
spring.jpa.hibernate.ddl-auto=update
```

## Roles

| Role | Permissions |
|---|---|
| `EMPLOYEE_ADMIN` | Read & add employees |
| `EMPLOYEE_READ` | Read employees only |
