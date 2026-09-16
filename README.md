# CABZI — Ride Booking & Sharing Platform

CABZI is a full-stack ride booking and sharing application that I built to understand how a real-world cab platform works from frontend to backend.

The idea was to create one platform where users can book rides, drivers can manage rides, and admins/staff can manage the overall system.

I built the project using **React, Java, Spring Boot, MySQL, REST APIs, JWT, and WebSockets**.

---

## ✨ Features

### 👤 User

* Register and login
* JWT authentication
* Book a ride
* Add pickup and destination
* View ride information
* Track rides
* Make payments
* Rate completed rides
* Create and join shared rides
* Book parcels
* Receive notifications
* Chat with staff
* Manage profile

### 🚗 Driver

* Driver registration
* Driver profile management
* KYC verification
* Vehicle information
* Manage ride requests
* Update ride status
* Handle assigned rides
* Participate in shared rides

### 👨‍💼 Admin & Staff

* Manage users
* Manage drivers
* Manage rides
* Driver verification
* Staff management
* Dashboard
* Support users
* View ride-related information

---

## 🛠️ Tech Stack

### Frontend

* React 19
* JavaScript
* React Router
* Axios
* Tailwind CSS
* React Leaflet
* Leaflet
* Lucide React
* SockJS
* STOMP.js
* Vite

### Backend

* Java 17
* Spring Boot
* Spring Web
* Spring Data JPA
* Spring Security
* JWT
* WebSocket
* STOMP
* Lombok
* SpringDoc OpenAPI

### Database

* MySQL
* Hibernate / JPA

### Other

* OpenStreetMap
* Firebase Cloud Messaging
* Cloudinary

---

## 🏗️ How the Project Works

The basic flow of a normal ride is:

```text
User
  ↓
Login / Register
  ↓
Enter Pickup & Destination
  ↓
Book Ride
  ↓
Spring Boot Backend
  ↓
MySQL
  ↓
Driver Handles Ride
  ↓
Ride Tracking
  ↓
Payment
  ↓
Rating
```

The React frontend communicates with the Spring Boot backend through REST APIs.

---

## 🤝 Shared Ride

I also added a shared ride feature where users can create a ride and allow other users to join it.

A shared ride contains:

* Pickup location
* Drop location
* Schedule
* Number of seats
* Fare

The basic flow is:

```text
Create Shared Ride
       ↓
Ride becomes OPEN
       ↓
Other users search
       ↓
User joins
       ↓
Available seats updated
```

---

## 📦 Parcel Booking

CABZI also supports parcel booking.

Users can create and manage parcel requests through the same platform instead of having a separate application for the service.

---

## 🔐 Authentication & Security

For authentication, I used **Spring Security and JWT**.

```text
Login
  ↓
Authentication
  ↓
JWT Token
  ↓
Frontend
  ↓
API Request
  ↓
JWT Filter
  ↓
Protected API
```

I also implemented role-based access so that users, drivers, staff, and admins can access their respective features.

---

## 📡 Real-Time Communication

For real-time communication, I used:

* Spring WebSocket
* STOMP
* SockJS

This is used for features such as chat and real-time ride-related communication.

```text
React
  ↓
WebSocket / STOMP
  ↓
Spring Boot
  ↓
Message Broker
```

---

## 🗺️ Maps

For location and map functionality, I used **Leaflet and React Leaflet** with OpenStreetMap.

Users can select and work with pickup and destination locations directly through the map interface.

---

## ⚙️ Backend Architecture

I followed a layered Spring Boot architecture:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
MySQL
```

### Controller

Handles API requests.

Some controllers include:

```text
AuthController
RideController
DriverController
PaymentController
ShareRideController
ParcelController
ChatController
NotificationController
AdminController
UserController
DashboardController
```

### Service

Contains the main business logic.

### Repository

Uses Spring Data JPA to communicate with MySQL.

This structure helped me keep the project modular and easier to maintain.

---

## 📁 Project Structure

```text
CABZI/
│
├── cabzi-frontend/
│   ├── src/
│   │   ├── components/
│   │   ├── context/
│   │   ├── pages/
│   │   ├── services/
│   │   └── utils/
│   │
│   └── package.json
│
└── ridesystem/
    ├── src/
    │   └── main/
    │       ├── java/com/sumit/ridesystem/
    │       │   ├── config/
    │       │   ├── controller/
    │       │   ├── dto/
    │       │   ├── model/
    │       │   ├── repository/
    │       │   ├── security/
    │       │   └── service/
    │       │
    │       └── resources/
    │
    ├── pom.xml
    ├── Dockerfile
    └── docker-compose.yml
```

---

# 🚀 Getting Started

## Requirements

Before running CABZI, make sure you have:

* Java 17
* Maven
* Node.js
* npm
* MySQL

## 1. Clone

```bash
git clone https://github.com/InnocentSG/Cabzi.git
cd Cabzi
```

## 2. Backend

```bash
cd ridesystem
mvn spring-boot:run
```

Backend:

```text
http://localhost:8080
```

## 3. Frontend

Open another terminal:

```bash
cd cabzi-frontend
npm install
npm run dev
```

Vite will provide the frontend URL in the terminal.

---

## 🗄️ Database

Create the MySQL database:

```sql
CREATE DATABASE cabzi;
```

Configure your database credentials in the backend environment/configuration.

For example:

```text
DB_URL=jdbc:mysql://localhost:3306/cabzi
DB_USERNAME=root
DB_PASSWORD=your_password
```

> Don't upload real passwords, JWT secrets, Firebase keys, or other private credentials to GitHub.

---

## 📖 API Documentation

The backend includes SpringDoc/OpenAPI support.

After starting the backend, Swagger can be accessed from the configured Swagger endpoint.

---

## 🐳 Docker

The backend also includes:

```text
Dockerfile
docker-compose.yml
```

so the project can be prepared for containerized development and deployment.

---

## 💡 What I Learned

While building CABZI, I got practical experience with:

* React frontend development
* Spring Boot backend development
* REST API development
* JWT authentication
* Spring Security
* Role-based authorization
* MySQL database design
* JPA/Hibernate
* React API integration
* WebSockets
* Map integration
* Payment modules
* Notifications
* Driver workflows
* Admin functionality
* Building a multi-module application

The main goal was not just to make a CRUD project. I wanted to understand how different parts of a real application connect and work together.

---

## 🔮 Future Improvements

I would like to improve CABZI further by adding:

* Better real-time GPS tracking
* Improved driver matching
* Production payment gateway
* Better fare calculation
* More automated tests
* CI/CD
* Production deployment
* Better monitoring and logging
* Improved mobile responsiveness

---

## 👨‍💻 About Me

**Sumit Kumar**

Java Full-Stack Developer

I enjoy building full-stack applications and working with:

```text
Java
Spring Boot
React
MySQL
REST APIs
JavaScript
```

If you find the project interesting, feel free to explore the code and give the repository a ⭐.
