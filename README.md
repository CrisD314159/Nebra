# Nebra - Business Directory Platform

<p align="center">
  <img alt="Nebra" src="https://res.cloudinary.com/dw43hgf5p/image/upload/v1752680406/Nebra_qemlaz.png" width="300">
</p>

Nebra is a portfolio backend business directory application built with Java, Spring Boot, and PostgreSQL. It provides a robust backend for business listings, user management, authentication (including Google OAuth2), comments, reporting, and email notifications.

## 🌐 Public Fronted Repo

- **Frontend:** [https://github.com/CrisD314159/nebra-front](https://github.com/CrisD314159/nebra-front)
- **Deployment Preview:** [https://nebra.vercel.app](https://nebra.vercel.app)

## Features

- **User Registration & Verification**
    - Sign up with email and password.
    - Email verification with code.
    - Google OAuth2 login and registration.

- **Authentication & Security**
    - JWT-based authentication and refresh tokens.
    - Secure password hashing (BCrypt).
    - Stateless session management.
    - Custom authentication handlers for OAuth2.

- **User Profile Management**
    - Update profile information (name, location, profile picture).
    - Upload profile pictures.
    - Delete (soft-delete) user accounts.
    - Search users by name.

- **Business Management**
    - Create, update, archive, and republish businesses.
    - Upload business photos.
    - Filter businesses by category or proximity.
    - Add/remove businesses to/from favorites.
    - View user's businesses and favorites.

- **Comments & Reviews**
    - Post comments and ratings on businesses.
    - Business owners can answer comments.
    - View comments by business or user.

- **Reporting System**
    - Report businesses for review.
    - Moderators can accept or reject reports.
    - Email notifications for report outcomes.

- **Password Recovery**
    - Request password reset via email.
    - Secure password change with verification code.

- **Email Notifications**
    - Templated emails for account verification, password recovery, and business/report events.
    - Configurable SMTP settings.

## Main Services

- **UserService**: Handles user registration, profile updates, verification, and search.
- **AccountService**: Manages authentication, password changes, and recovery.
- **BusinessService**: Manages business CRUD, archiving, republishing, favorites, and filtering.
- **CommentService**: Handles business comments, answers, and ratings.
- **ReportService**: Manages business reports and moderation.
- **EmailService**: Sends templated emails for verification, recovery, and notifications.
- **ThirdPartyUserService**: Handles Google OAuth2 login and user creation.

## Technologies Used

- Java 17+
- Spring Boot
- Spring Security (JWT, OAuth2)
- Spring Data JPA (Hibernate)
- PostgreSQL
- Flyway (database migrations)
- Cloudinary (image storage)
- JavaMailSender (email)
- MapStruct (DTO mapping)
- Lombok

## Getting Started

1. **Clone the repository**
2. **Configure environment variables** in `application.properties` or `.env` (see placeholders for DB, JWT, mail, OAuth, Cloudinary, etc).
3. **Run database migrations** (Flyway runs automatically on startup).
4. **Start the application**
   ```bash
   ./mvnw spring-boot:run
   ```
5. **API Endpoints**: See the `controladores` package for available endpoints.

## Folder Structure

- `src/main/java/com/crisdevApps/Nebra/`
    - `controladores/` - REST API controllers
    - `services/` - Business logic and service interfaces/implementations
    - `model/` - JPA entities
    - `repositories/` - Spring Data repositories
    - `dto/` - Data transfer objects (input/output)
    - `security/` - Security configuration and JWT utilities
    - `exceptions/` - Custom exceptions and global error handling
    - `mappers/` - MapStruct mappers for DTO/entity conversion

- `src/main/resources/templates/` - Email HTML templates
- `src/main/resources/db/migration/` - Flyway SQL migrations

## License

This project is for portfolio and demonstration purposes.

---
Made with ❤️ by Cristian David Vargas Loaiza

---

## 👤 About the Creator

Created by **Cristian David Vargas Loaiza**  
[LinkedIn](https://www.linkedin.com/in/cristian-david-vargas-loaiza-982314271) | [GitHub](https://github.com/CrisD314159) | [Portfolio](https://crisdev-pi.vercel.app)