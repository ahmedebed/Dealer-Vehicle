# 🚗 Dealer & Vehicle Inventory Module

## 📌 Overview

This project is a **Multi-Tenant Dealer & Vehicle Inventory Module** built using **Spring Boot (Java 21)** following **Clean Architecture principles**.

The system allows managing dealers and their vehicles while enforcing strict tenant isolation and role-based access control.

---

## 🧱 Architecture

The application follows a modular and clean structure:

* **Controller Layer** → Handles HTTP requests
* **Service Layer** → Business logic
* **Repository Layer** → Database access (Spring Data JPA)
* **Entity Layer** → Domain models
* **Validation Layer** → Request validation
* **Security Layer** → Role-based access + tenant enforcement

---

## 🏢 Multi-Tenancy

* Tenant is passed via header:

  ```
  X-Tenant-Id
  ```
* All operations are scoped to tenant
* Cross-tenant access is **blocked (403)**

---

## 🔐 Security

* Role passed via header:

  ```
  X-Role
  ```

* Supported roles:

  * `GLOBAL_ADMIN`

* Admin endpoints require:

  ```
  ROLE_GLOBAL_ADMIN
  ```

---

## ⚙️ Tech Stack

* Java 21
* Spring Boot 3
* Spring Data JPA
* Spring Security
* MySQL
* Maven

---

## 🚀 Setup Instructions

### 1. Clone the repository

```bash
git clone <your-repo-url>
cd inventory
```

### 2. Configure Database

Update `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/inventory_db
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 3. Run the application

```bash
mvn spring-boot:run
```

---

## 📬 Required Headers

| Header      | Required | Description             |
| ----------- | -------- | ----------------------- |
| X-Tenant-Id | ✅        | Tenant identifier       |
| X-Role      | ❌        | Required for admin APIs |

---

## 📦 Data Models

### Dealer

* id (UUID)
* tenantId
* name
* email
* subscriptionType → BASIC / PREMIUM

### Vehicle

* id (UUID)
* tenantId
* dealerId
* model
* price
* status → AVAILABLE / SOLD

---

## 🔗 API Endpoints

---

### 🏢 Dealers

#### ➤ Create Dealer

```
POST /dealers
```

**Body**

```json
{
  "name": "Dealer One",
  "email": "dealer@email.com",
  "subscriptionType": "PREMIUM"
}
```

---

#### ➤ Get Dealer by ID

```
GET /dealers/{id}
```

---

#### ➤ Get All Dealers (Pagination)

```
GET /dealers?page=0&size=10&sort=name,asc
```

---

#### ➤ Update Dealer

```
PATCH /dealers/{id}
```

---

#### ➤ Delete Dealer

```
DELETE /dealers/{id}
```

---

### 🚗 Vehicles

#### ➤ Create Vehicle

```
POST /vehicles
```

**Body**

```json
{
  "dealerId": "uuid",
  "model": "BMW X5",
  "price": 50000,
  "status": "AVAILABLE"
}
```

---

#### ➤ Get Vehicle by ID

```
GET /vehicles/{id}
```

---

#### ➤ Get Vehicles (Filters + Pagination)

```
GET /vehicles?model=BMW&status=AVAILABLE&priceMin=10000&priceMax=60000&page=0&size=10
```

---

#### ➤ Filter by Dealer Subscription

```
GET /vehicles?subscription=PREMIUM
```

✅ Returns vehicles for dealers with **PREMIUM subscription within same tenant only**

---

#### ➤ Update Vehicle

```
PATCH /vehicles/{id}
```

---

#### ➤ Delete Vehicle

```
DELETE /vehicles/{id}
```

---

### 🛠️ Admin APIs

#### ➤ Count Dealers by Subscription

```
GET /admin/dealers/countBySubscription
```

**Headers**

```
X-Role: GLOBAL_ADMIN
```

**Response**

```json
{
  "BASIC": 5,
  "PREMIUM": 3
}
```

📌 **Note:**
This count is **global across all tenants** (not tenant-scoped).

---

## ✅ Acceptance Criteria Coverage

* ✅ Missing `X-Tenant-Id` → 400
* ✅ Cross-tenant access → 403
* ✅ Subscription filter works within tenant
* ✅ Admin endpoint secured with role
* ✅ Clean architecture applied

---

## 🧪 Testing Tips

Use Postman and always include:

```
X-Tenant-Id: tenant_1
```

For admin:

```
X-Role: GLOBAL_ADMIN
```

---

## 👨‍💻 Author

Ahmed Ebed

---
