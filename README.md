# High-Performance URL Shortener API

A scalable, backend-focused URL shortener built with **Spring Boot**. This application utilizes **MySQL** for persistent relational storage and implements a **Cache-Aside Pattern using Redis** to deliver sub-millisecond redirection times and prevent database bottlenecking under high traffic.

## 🚀 Architecture & Features
* **Algorithmic Key Generation:** Converts standard database IDs into compact alphanumeric strings using a custom Base62 encoding algorithm.
* **In-Memory Caching (Redis):** Intercepts incoming web requests. If a short-link exists in the RAM cache (Cache Hit), it redirects the user instantly without querying the hard drive. 
* **Database Fallback:** On a Cache Miss, the application queries MySQL, redirects the user, and automatically populates the Redis cache for subsequent requests.
* **Spring Data JPA:** Handles all database interactions and object-relational mapping without raw SQL scripts.

## 🛠️ Tech Stack
* **Language:** Java 17+
* **Framework:** Spring Boot (Web, Data JPA, Cache)
* **Primary Database:** MySQL
* **In-Memory Cache:** Redis
* **Build Tool:** Maven

## ⚙️ Local Setup & Installation

### Prerequisites
Make sure you have the following installed on your machine:
* Java Development Kit (JDK)
* MySQL Server (e.g., via XAMPP)
* Redis Server for Windows/Linux
* Maven

### 1. Database Configuration
Ensure your MySQL server is running on port `3306`. Create a new, empty database named `url_shortener_db`. Spring Boot will automatically generate the required tables on startup.

### 2. Redis Configuration
Ensure your local Redis server is running and listening on the default port `6379`.

### 3. Application Properties
Verify your `src/main/resources/application.properties` file contains the correct database credentials and Redis coordinates:
```properties
# MySQL Connection
spring.datasource.url=jdbc:mysql://localhost:3306/url_shortener_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update

# Redis Connection
spring.cache.type=redis
spring.data.redis.host=localhost
spring.data.redis.port=6379
4. Build and Run
Clone the repository and run the application using Maven:

Bash
git clone [https://github.com/nevin-164/url-shortener-backend.git](https://github.com/nevin-164/url-shortener-backend.git)
cd url-shortener-backend
mvn spring-boot:run
###🌐 API Endpoints
##1. Create a Short URL
POST http://localhost:8080/api/urls (or your configured mapping)

Body:

JSON
{
  "longUrl": "[https://en.wikipedia.org/wiki/Java_(programming_language)](https://en.wikipedia.org/wiki/Java_(programming_language))"
}
##2. Redirect to Original URL
GET http://localhost:8080/{shortKey}

Intercepted by Redis for high-speed retrieval. Redirects the browser via HTTP 302.

Author: Nevin Binu
