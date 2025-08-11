 # Notification Service

A simple Spring Boot microservice for sending and managing notifications (email only for this project).

## Setup
1. Install Java 17, Maven, PostgreSQL.
2. Update `application.properties` with DB and Gmail credentials.
3. Run: `mvn spring-boot:run`
4. Test with Postman: POST `http://localhost:8080/api/notifications/send`

For Gmail: Generate App Password from Google Account settings.