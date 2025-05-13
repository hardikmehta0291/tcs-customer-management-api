# 🧾 Customer Management API

A Spring Boot REST API to manage customer records with real-time tier calculation based on annual spend and recent purchase activity.

---

## 🚀 How to Build and Run

### ✅ Prerequisites
- Java 17
- Maven 3.6+
- Git (optional)

### ▶️ Steps to Run

```bash
# Clone the repository (or unzip the project)
cd customer-api

# Build the application
mvn clean install

# Run the application
mvn spring-boot:run

# Created own port
8088

# API Documentation
http://localhost:8088/swagger-ui/index.html?url=/openapi.yaml#/

# H2 Database Console
http://localhost:8088/h2-console/login.jsp?jsessionid=0e40616b0bf25a52537d3fb101c909f0

# Test the API(Sample Requests)

# Create Customer (POST /customers)
{
  "name": "Hardik",
  "email": "mehtahardik0291@gmail.com",
  "annualSpend": 12000,
  "lastPurchaseDate": "2024-12-01T10:00:00"
}

# Get Customer by ID (GET /customers/{id})
{
  "id": "b2d17e02-0d23-44f8-bef7-07d1b534b482"(auto-generated-uuid),
  "name": "Hardik",
  "email": "mehtahardik0291@gmail.com",
  "annualSpend": 12000,
  "lastPurchaseDate": "2024-12-01T10:00:00",
  "tier": "Platinum"
}

# Update Customer (PUT /customers/{id})
{
  "name": "Hardik Updated",
  "email": "mehtahardik0291@gmail.com",
  "annualSpend": 3000,
  "lastPurchaseDate": "2024-07-01T10:00:00"
}

# Delete Customer (DELETE /customers/{id})
{
    "id": "b2d17e02-0d23-44f8-bef7-07d1b534b482"(auto-generated-uuid)
}