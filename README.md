# 💬 ShareThought – Microservices-Based Chat Platform

![Java](https://img.shields.io/badge/Java-17-blue.svg)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.x-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue)
![Keycloak](https://img.shields.io/badge/Auth-Keycloak-purple)

ShareThought is a **secure, scalable, and modular messaging platform** built using Java and Spring Boot Microservices. It supports **personal and group conversations**, backed by **Keycloak authentication**, **Spring Security**, and a PostgreSQL database. Designed for flexibility, this system is ideal for real-time chat applications and extensible for social platforms.

---

## 🛠️ Tech Stack

| Category       | Tools Used                                                                 |
|----------------|----------------------------------------------------------------------------|
| Language       | ![Java](https://img.shields.io/badge/Java-17-blue.svg)                    |
| Frameworks     | Spring Boot, Spring Security (OAuth2), Spring Data JPA                    |
| Auth           | Keycloak (OpenID Connect, OAuth2)                                          |
| Database       | PostgreSQL                                                                 |
| Config & Routing | Spring Cloud Config Server, Spring Cloud Gateway                        |
| Service Discovery | Eureka Server                                                           |

---

## 🧱 Microservices Architecture

### 1️⃣ **UserManager**
- Handles user creation and profile operations.
- Stores **Keycloak user IDs** alongside user data.
- Secured via Spring Security OAuth2.

### 2️⃣ **ThoughtStore**
- Manages and stores all user messages.
- Supports both **One-to-One** and **Group chats**.
- Exposes RESTful handler endpoints for message operations.

### 3️⃣ **ThoughtHub**
- Manages **Group metadata**: name, description, members, and settings.
- Provides group creation, update, and deletion APIs.

### 4️⃣ **ThoughtSync**
- Handles **message dispatching and chat synchronization**.
- Tracks chat history: sender/receiver, timestamps, edits, etc.
- Supports both personal and group messages.

### 5️⃣ **ConfigServer**
- Hosts centralized configuration (`config.yml`) for all microservices.
- Enables externalized, dynamic config loading.

### 6️⃣ **EurekaServer**
- Acts as a **Service Registry** for microservice discovery.
- Enables fault-tolerant and dynamic scaling.

### 7️⃣ **GatewayServer**
- Serves as the entry point to all services.
- Performs **path routing, filtering, and auth validation**.

---

## 📦 Planned Improvements

- 🚢 Containerization with **Docker**
- 🖼️ Build modern frontend (likely with **React** or **Angular**)
- 📎 Add media support: **images, audio, video, and document sharing**
- 🔔 Integrate **asynchronous push notifications** (event-driven messaging)
- 📊 Add better **logging, monitoring, and analytics**
- 🛡️ Implement **rate limiting** and **request throttling** for DDoS protection

---

## 👨‍💻 Author

**Gohul K.**  
Backend Developer | Java & Spring Enthusiast  
📧 [Reach out](mailto:kpgohul@gmail.com) | 🌐 [GitHub](https://github.com/kpgohul)

---

> _“No matter how much knowledge or skill you have, if you can’t communicate well, people won’t recognize your efforts.”_


