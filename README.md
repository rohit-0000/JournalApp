 # Journal App Backend (Spring Boot)

A backend service for a journal application built with Spring Boot. This application provides RESTful APIs to manage journal entries, user authentication, role-based access control, email notifications, and JWT-based security. Data is stored in MongoDB Atlas, and the project leverages Lombok for boilerplate reduction.


## Features

* **User Authentication & Authorization**: Users can register and log in. Role-based access (e.g., `USER`, `ADMIN`) is enforced.
* **JWT Security**: JSON Web Tokens (JWT) are used for stateless authentication and authorization.
* **Role-Based Access Control**: Endpoints are secured based on user roles.
* **MongoDB Atlas**: Cloud-based MongoDB database for scalable, reliable data storage.
* **Lombok Integration**: Reduces boilerplate code for entities, DTOs, and services.
* **Email Notifications**: JavaMailSender integration to send registration confirmation and password reset emails.
* **CRUD Operations**: Full create, read, update, and delete operations for journal entries.
* **Spring Security**: Configured to secure endpoints, validate JWT tokens, and handle login/logout.

## Technologies Used

* **Java 11+**
* **Spring Boot (2.5+)**
* **Spring Security**
* **Spring Data MongoDB**
* **JavaMailSender (Spring Boot Starter Mail)**
* **JWT (jjwt)**
* **Lombok**
* **MongoDB Atlas**
* **Maven**
* **JUnit & Mockito (for testing)**

## Prerequisites

Before you begin, ensure you have the following installed on your local machine:

* **Java Development Kit (JDK) 11 or higher**
* **Maven 3.6+**
* **MongoDB Atlas Account** (or local MongoDB instance)
* **SMTP Email Account Credentials** (e.g., Gmail SMTP, SendGrid API, etc.)

## Installation

1. **Clone the Repository**

   ```bash
   git clone https://github.com/your-username/journal-app-backend.git
   cd journal-app-backend
   ```

2. **Build the Project**

   ```bash
   mvn clean install
   ```

3. **Configure Environment Variables**
   Set the following environment variables or update `application.properties` / `application.yml` accordingly:

   ```properties
   # MongoDB Atlas connection string
   spring.data.mongodb.uri=${MONGODB_URI}

   # JWT settings
   jwt.secret=${JWT_SECRET}
   jwt.expiration-in-ms=86400000    # e.g., 24 hours in milliseconds

   # Spring Mail settings
   spring.mail.host=${MAIL_HOST}      # e.g., smtp.gmail.com
   spring.mail.port=${MAIL_PORT}      # e.g., 587
   spring.mail.username=${MAIL_USERNAME}
   spring.mail.password=${MAIL_PASSWORD}
   spring.mail.properties.mail.smtp.auth=true
   spring.mail.properties.mail.smtp.starttls.enable=true
   ```

4. **Set Up MongoDB Atlas**

   * Create a new cluster in MongoDB Atlas.
   * Create a database user with read/write access.
   * Whitelist your IP or allow access from anywhere (for development).
   * Copy the connection URI and set it as `MONGODB_URI`.

5. **Set Up SMTP Email Provider**

   * For Gmail, ensure “Less secure app access” is enabled or create an App Password.
   * For production, consider using SendGrid, Mailgun, or a similar transactional email service.

## Configuration

### `application.properties`

```properties
server.port=8080

# Spring Data MongoDB
spring.data.mongodb.database=journal_app_db
spring.data.mongodb.uri=${MONGODB_URI}

# JWT Configuration
jwt.secret=${JWT_SECRET}
jwt.expiration-in-ms=86400000

# Spring Mail
spring.mail.host=${MAIL_HOST}
spring.mail.port=${MAIL_PORT}
spring.mail.username=${MAIL_USERNAME}
spring.mail.password=${MAIL_PASSWORD}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

# Logging
logging.level.org.springframework=INFO
logging.level.com.yourpackage=DEBUG
```

> **Note**: You can also use `application.yml` instead of `application.properties`.

## Running the Application

1. **Start the Application**

   ```bash
   mvn spring-boot:run
   ```

   The application will start on `http://localhost:8080`.

2. **Swagger UI (Optional)**
   If Swagger is configured, access the API documentation at:

   ```
   http://localhost:8080/swagger-ui.html
   ```

3. **Docker (Optional)**
   You can containerize the application using Docker. Example `Dockerfile`:

   ```dockerfile
   FROM openjdk:11-jre-slim
   ARG JAR_FILE=target/journal-app-backend.jar
   COPY ${JAR_FILE} app.jar
   ENTRYPOINT ["java","-jar","/app.jar"]
   ```

   Build and run:

   ```bash
   docker build -t journal-backend:latest .
   docker run -e MONGODB_URI="<your_uri>" \
              -e JWT_SECRET="<your_jwt_secret>" \
              -e MAIL_HOST="smtp.gmail.com" \
              -e MAIL_PORT=587 \
              -e MAIL_USERNAME="your-email@gmail.com" \
              -e MAIL_PASSWORD="your-email-password" \
              -p 8080:8080 \
              journal-backend:latest
   ```

## API Endpoints

Below is a summarized list of key REST endpoints provided by the application. Replace `{base-url}` with `http://localhost:8080` or your deployed host.

### Authentication

* **Register User**
  `POST {base-url}/api/auth/register`
  **Request Body**:

  ```json
  {
    "username": "johndoe",
    "email": "john@example.com",
    "password": "StrongPassword123"
  }
  ```

  **Response**: Registration confirmation and activation email sent.

* **Login**
  `POST {base-url}/api/auth/login`
  **Request Body**:

  ```json
  {
    "usernameOrEmail": "johndoe",
    "password": "StrongPassword123"
  }
  ```

  **Response**:

  ```json
  {
    "accessToken": "<jwt_token>",
    "tokenType": "Bearer"
  }
  ```

* **Refresh Token** (if implemented)
  `POST {base-url}/api/auth/refresh`
  **Request Body**:

  ```json
  {
    "refreshToken": "<refresh_token>"
  }
  ```

  **Response**: New JWT access token.

### User Management

* **Get All Users**
  `GET {base-url}/api/users`
  **Roles**: `ADMIN` only
  **Response**: List of all registered users.

* **Get User by ID**
  `GET {base-url}/api/users/{userId}`
  **Roles**: `ADMIN` or the user themselves
  **Response**: User profile data.

* **Update User Role**
  `PUT {base-url}/api/users/{userId}/role`
  **Roles**: `ADMIN` only
  **Request Body**:

  ```json
  {
    "role": "ADMIN"
  }
  ```

  **Response**: Updated user data.

* **Delete User**
  `DELETE {base-url}/api/users/{userId}`
  **Roles**: `ADMIN` only
  **Response**: Confirmation message.

### Journal Entries

* **Create Entry**
  `POST {base-url}/api/journal`
  **Roles**: `USER`, `ADMIN`
  **Request Body**:

  ```json
  {
    "title": "My First Entry",
    "content": "Today I started building my journal app...",
    "tags": ["spring", "mongodb"]
  }
  ```

  **Response**: Created journal entry with metadata.

* **Get All Entries (Paged)**
  `GET {base-url}/api/journal?page=0&size=10`
  **Roles**: `USER`, `ADMIN`
  **Response**: Paged list of entries for the authenticated user.

* **Get Entry by ID**
  `GET {base-url}/api/journal/{entryId}`
  **Roles**: `USER`, `ADMIN`
  **Response**: Detailed entry data.

* **Update Entry**
  `PUT {base-url}/api/journal/{entryId}`
  **Roles**: `USER`, `ADMIN`
  **Request Body**:

  ```json
  {
    "title": "Updated Entry Title",
    "content": "Updated content...",
    "tags": ["updated", "journal"]
  }
  ```

  **Response**: Updated entry data.

* **Delete Entry**
  `DELETE {base-url}/api/journal/{entryId}`
  **Roles**: `USER`, `ADMIN`
  **Response**: Confirmation message.

* **Search Entries by Tag**
  `GET {base-url}/api/journal/search?tag=spring`
  **Roles**: `USER`, `ADMIN`
  **Response**: List of entries matching the tag.

## Security

* **Password Storage**: BCrypt password encoder is used to hash passwords before storing in MongoDB.
* **JWT**: Each authenticated request requires a valid JWT in the `Authorization` header:

  ```http
  Authorization: Bearer <jwt_token>
  ```
* **Role-Based Access**: Custom annotations (`@PreAuthorize`, `@Secured`) are used on controller methods to restrict access based on roles.
* **Exception Handling**: Global exception handlers provide standardized error responses for authentication failures, access denied, and validation errors.

## Email Notifications

The application uses Spring’s `JavaMailSender` to send emails for:

* **Account Activation**: Upon registration, an activation link is emailed to the user.

  * User clicks the activation link (e.g., `/api/auth/confirm?token=<confirmation_token>`) to verify their email.
* **Password Reset** (if implemented): Generates a reset token and sends a reset link to the user’s email.

### Sample Email Configuration

```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

> **Note**: Make sure to enable “App Passwords” if you use Gmail with 2FA, or configure a transactional email service for production.

## Project Structure

```
journal-app-backend/
├── src/
│   ├── main/
│   │   ├── java/com/example/journalapp/
│   │   │   ├── config/               # Security and JWT configuration
│   │   │   ├── controller/           # REST controllers (AuthController, UserController, JournalController)
│   │   │   ├── dto/                  # Data Transfer Objects for requests/responses
│   │   │   ├── exception/            # Custom exception classes and handlers
│   │   │   ├── model/                # Domain models (User, Role, JournalEntry)
│   │   │   ├── repository/           # Spring Data MongoDB repositories
│   │   │   ├── security/             # JWT utilities, filters, and user details service
│   │   │   ├── service/              # Business logic services (UserService, JournalService, EmailService)
│   │   │   └── JournalAppApplication.java  # Main application class
│   │   └── resources/
│   │       ├── application.properties
│   │       └── templates/            # Email templates (Thymeleaf or plain text)
│   └── test/                         # Unit and integration tests (JUnit, Mockito)
├── .gitignore
├── pom.xml                          # Maven configuration
└── REALME.md                        # This README file
```

## License

This project is licensed under the [MIT License](LICENSE).

## Contact

For questions, issues, or contributions, please contact:

* **Your Name**: \[[your.email@example.com](mailto:your.email@example.com)]
* **GitHub**: \[[https://github.com/your-username](https://github.com/your-username)]

---

*Thank you for using the Journal App Backend!*

