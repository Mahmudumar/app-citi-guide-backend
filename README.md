# City Guide API

Spring Boot REST API for the City Guide Flutter application. The backend owns authentication, profile data, cities, places, reviews, favourites and administration. Flutter communicates only with this API; the API connects to PostgreSQL.

## Stack
- Java 21 and Spring Boot 4.1.x
- Spring MVC, Spring Data JPA, PostgreSQL and Flyway
- Stateless JWT access tokens and opaque rotating refresh tokens
- BCrypt password hashing, Jakarta Validation
- springdoc OpenAPI, Actuator, Caffeine and Spring Mail
- JUnit 5, MockMvc and Testcontainers

## Run locally
1. Install JDK 21, Maven and Docker.
2. Copy `.env.example` to `.env` and set a random `JWT_SECRET` of at least 32 bytes.
3. Start PostgreSQL: `docker compose up -d`.
4. Export the environment variables from `.env` (Spring Boot does not load dotenv files automatically).
5. Run: `mvn spring-boot:run -Dspring-boot.run.profiles=local`.

The server binds to `0.0.0.0:8080`. Android emulator uses `http://10.0.2.2:8080`, Chrome uses `http://localhost:8080`, and a physical phone uses the computer's LAN IP. Android 9+ blocks plain HTTP by default unless cleartext is enabled in the development build.

## Database
Flyway owns the schema. Hibernate is set to `validate`; it must never create or update tables. For Supabase production, use the direct PostgreSQL connection or session pooler on port 5432 with `sslmode=require`; use the same owning database role for Flyway and the application because RLS is enabled without policies. Do not use the Supabase client SDK, Supabase Auth or Supabase REST API. If the transaction pooler on port 6543 is unavoidable, add `prepareThreshold=0` to the JDBC URL and run Flyway over a direct connection.

## Configuration
See `.env.example`. Important values: `DB_URL`, `DB_USER`, `DB_PASSWORD`, `JWT_SECRET`, `CORS_ORIGINS`, `ADMIN_EMAIL`, `ADMIN_PASSWORD`, `STORAGE_LOCAL_DIR`, `MAIL_HOST`, `MAIL_PORT`, `MAIL_USERNAME`, `MAIL_PASSWORD`, and `MAIL_FROM`. Never commit secrets. The JWT secret is mandatory and must be at least 32 bytes.

## Seed data
The original `V2__seed.sql` attachment was not available in this task. Any replacement seed file must be treated as illustrative sample data and must not be represented as the approved Flutter dataset. Replace it with the approved seed before production use.

## Storage
The default storage service writes random-named files under `STORAGE_LOCAL_DIR` and serves them under `/uploads/**`. A Supabase Storage implementation can replace the storage interface later by uploading the same validated bytes to a private/public bucket and returning the resulting public object path; database/API contracts need not change.

## Login lockout limitation
Login-attempt tracking uses a local Caffeine cache. It is per application instance and is not shared between replicas. Move this state to Redis or another shared store before running more than one backend instance.

## Mail and demo data
The local profile logs password reset codes through the development mail implementation. Production uses SMTP. Local demo credentials are intended to be created by the local initializer: `demo@cityguide.app` / `Demo@1234`. Do not enable development reset-code responses outside local.

## API docs and health
Local Swagger UI: `/swagger-ui.html`. Production disables OpenAPI docs. Health: `/actuator/health`.

## Tests
Run `mvn test`. Integration tests use PostgreSQL Testcontainers; Docker must be available.
