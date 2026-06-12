# 🚀 High-Performance URL Shortener API

A scalable, backend-focused URL shortener built with **Spring Boot**. This application uses **MySQL** for persistent relational storage and implements a **Cache-Aside Pattern with Redis** to achieve sub-millisecond redirection times while minimizing database load under high traffic.

---

## 📌 Features

* **Algorithmic Key Generation**

  * Converts database-generated IDs into compact alphanumeric short URLs using a custom **Base62 encoding** algorithm.

* **Redis Caching**

  * Uses Redis as an in-memory cache for lightning-fast URL lookups.
  * Frequently accessed URLs are served directly from cache without hitting the database.

* **Cache-Aside Pattern**

  * **Cache Hit:** Redirects instantly from Redis.
  * **Cache Miss:** Retrieves data from MySQL, redirects the user, and stores the result in Redis for future requests.

* **Spring Data JPA**

  * Simplifies database operations through object-relational mapping (ORM).
  * Eliminates the need for raw SQL queries.

* **RESTful API Design**

  * Clean and simple endpoints for URL creation and redirection.

---

## 🏗️ Architecture

```text
Client Request
      │
      ▼
 Spring Boot API
      │
      ▼
    Redis
 (Cache Lookup)
   │       │
Hit       Miss
 │          │
 ▼          ▼
Redirect   MySQL
             │
             ▼
      Store in Redis
             │
             ▼
         Redirect
```

---

## 🛠️ Tech Stack

| Technology      | Purpose                            |
| --------------- | ---------------------------------- |
| Java 17+        | Programming Language               |
| Spring Boot     | Backend Framework                  |
| Spring Web      | REST API Development               |
| Spring Data JPA | ORM & Database Access              |
| MySQL           | Primary Database                   |
| Redis           | In-Memory Cache                    |
| Maven           | Dependency Management & Build Tool |

---

## ⚙️ Local Setup & Installation

### Prerequisites

Make sure the following are installed on your system:

* Java Development Kit (JDK 17 or later)
* Maven
* MySQL Server
* Redis Server

---

### 1️⃣ Database Configuration

Start your MySQL server and create a database named:

```sql
CREATE DATABASE url_shortener_db;
```

Spring Boot will automatically generate the required tables on startup.

---

### 2️⃣ Redis Configuration

Ensure Redis is running on the default port:

```text
6379
```

---

### 3️⃣ Configure Application Properties

Update `src/main/resources/application.properties`:

```properties
# MySQL Connection
spring.datasource.url=jdbc:mysql://localhost:3306/url_shortener_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=

# JPA
spring.jpa.hibernate.ddl-auto=update

# Redis Connection
spring.cache.type=redis
spring.data.redis.host=localhost
spring.data.redis.port=6379
```

---

### 4️⃣ Build & Run

Clone the repository and start the application:

```bash
git clone https://github.com/nevin-164/url-shortener-backend.git

cd url-shortener-backend

mvn spring-boot:run
```

The application will start on:

```text
http://localhost:8080
```

---

## 🌐 API Endpoints

### 1. Create a Short URL

**Request**

```http
POST /api/urls
```

**URL**

```text
http://localhost:8080/api/urls
```

**Request Body**

```json
{
  "longUrl": "https://en.wikipedia.org/wiki/Java_(programming_language)"
}
```

**Response**

```json
{
  "shortUrl": "http://localhost:8080/abc123"
}
```

---

### 2. Redirect to Original URL

**Request**

```http
GET /{shortKey}
```

**Example**

```text
http://localhost:8080/abc123
```

**Behavior**

1. Redis is checked for the URL mapping.
2. If found (**Cache Hit**), the user is redirected immediately.
3. If not found (**Cache Miss**), MySQL is queried.
4. The result is stored in Redis.
5. The user is redirected using an **HTTP 302 Redirect** response.

---

## 📈 Performance Benefits

* Reduced database load through Redis caching.
* Faster response times for frequently accessed URLs.
* Improved scalability under high traffic conditions.
* Efficient memory utilization using the Cache-Aside pattern.

---

## 🔮 Future Enhancements

* URL expiration support
* Click analytics and tracking
* Custom short aliases
* Rate limiting
* User authentication and management
* Docker containerization
* Kubernetes deployment

---

## 👨‍💻 Author

**Nevin Binu**

GitHub: https://github.com/nevin-164

