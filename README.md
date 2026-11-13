# City Guide API

Spring Boot REST API for the City Guide Flutter application. The backend owns authentication, profile data, cities, places, reviews, favourites and administration. Flutter communicates only with this API; the API connects to PostgreSQL.

## Stack
- Java 21 and Spring Boot 4.1.2
- Spring MVC, Spring Data JPA, PostgreSQL and Flyway
- Stateless JWT access tokens and opaque rotating refresh tokens
- BCrypt password hashing, Jakarta Validation
- springdoc OpenAPI, Actuator, Caffeine and Spring Mail
- JUnit 5, MockMvc and Testcontainers (PostgreSQL 16)

## Run locally
1. Install JDK 21 and Docker.
2. Copy `.env.example` to `.env` and set a random `JWT_SECRET` of at least 32 bytes.
3. Start PostgreSQL: `docker compose up -d`.
4. Export the environment variables from `.env` (Spring Boot does not load dotenv files automatically).
5. Run: `./mvnw spring-boot:run -Dspring-boot.run.profiles=local` (Windows: `mvnw.cmd`).

The server binds to `0.0.0.0:8080`. Android emulator uses `http://10.0.2.2:8080`, Chrome uses `http://localhost:8080`, and a physical phone uses the computer's LAN IP. Android 9+ blocks plain HTTP by default unless cleartext is enabled in the development build.

## Deploy to Render
The repository includes a Render Blueprint (`render.yaml`) and a Dockerfile. In Render, create a Blueprint Instance from this repository and deploy the `feature/city-guide-backend` branch (or deploy the branch after merging it into your chosen deployment branch).

The Blueprint creates a Docker web service, enables the production Spring profile, checks `/actuator/health`, and mounts a persistent disk at `/var/data` for uploaded images. The Starter web service and persistent disk are paid Render resources; the disk is needed because uploaded files must survive restarts and deployments. A single persistent disk also means the service should run as one instance.

### Required Render environment values
- `DB_URL`: JDBC PostgreSQL URL. For Supabase, use its direct connection or session pooler with SSL, for example `jdbc:postgresql://HOST:5432/postgres?sslmode=require`. Use the host, port, database and username shown in Supabase. Avoid the transaction pooler for Flyway migrations.
- `DB_USER` and `DB_PASSWORD`: database credentials.
- `JWT_SECRET`: generate a long random secret (at least 32 bytes). Render can generate this value through the Blueprint.
- `CORS_ORIGINS`: comma-separated exact origins allowed to call the API from browsers. For a Flutter mobile app this may be empty unless Flutter Web is also used; never use a wildcard in production.
- `MAIL_HOST`, `MAIL_USERNAME`, `MAIL_PASSWORD`, `MAIL_FROM`: SMTP details if password-reset email is to be delivered. Set these before relying on password reset in production.

Render provides `PORT` automatically and the application uses it. Do not set `SERVER_PORT` unless you have a specific reason. Keep `STORAGE_LOCAL_DIR=/var/data/uploads`; the Blueprint sets this for the persistent disk.

After deployment, confirm `https://YOUR-SERVICE.onrender.com/actuator/health` reports `UP`. Use that base URL in the Flutter production build. Do not put database credentials, SMTP passwords, or the JWT secret in Flutter or in Git.

### Database and content readiness
Flyway owns the schema. Hibernate is set to `validate`; it must never create or update tables. For Supabase production, use the direct PostgreSQL connection or session pooler with `sslmode=require`; use a database role that can run migrations. The API connects directly to PostgreSQL and does not use the Supabase client SDK, Supabase Auth or Supabase REST API.

The committed V2 migration contains illustrative sample rows matching the requested four-city / 35-place counts. It is not an approved City Guide dataset. Replace or review this content before presenting the app as a real city guide. Do not use demo accounts or sample reviews as real user content.

## Environment variables
All variables are documented in `.env.example`.

| Variable | Purpose |
| --- | --- |
| `SERVER_PORT` / `PORT` | HTTP server port |
| `DB_URL`, `DB_USER`, `DB_PASSWORD` | PostgreSQL connection |
| `JWT_SECRET`, `JWT_ACCESS_TTL`, `JWT_REFRESH_TTL` | JWT signing and token lifetimes |
| `LOGIN_MAX_ATTEMPTS`, `LOGIN_LOCK_SECONDS` | Login lockout |
| `RESET_CODE_TTL_MINUTES`, `RESET_MAX_ATTEMPTS`, `DEV_RETURN_RESET_CODE` | Password reset |
| `CORS_ORIGINS` | Allowed browser origins |
| `STORAGE_LOCAL_DIR` | Upload directory; use persistent storage in production |
| `ADMIN_EMAIL`, `ADMIN_PASSWORD` | Optional first-admin bootstrap |
| `MAIL_HOST`, `MAIL_PORT`, `MAIL_USERNAME`, `MAIL_PASSWORD`, `MAIL_FROM` | SMTP configuration |

Never commit secrets. The JWT secret is mandatory and must be at least 32 bytes.

## Seed data
The original approved seed attachment was not available when the sample data was created. The committed seed is illustrative and must be replaced with the approved Flutter dataset before production use.

## Storage
The default storage service writes random-named files under `STORAGE_LOCAL_DIR` and serves them under `/uploads/**`. The Render Blueprint mounts a persistent disk and points this directory to it. This is suitable for a small single-instance academic project deployment. For multiple backend instances or larger production use, move images to object storage such as Supabase Storage.

## Login lockout limitation
Login-attempt tracking uses a local Caffeine cache. It is per application instance and is not shared between replicas. Keep one backend instance for this project deployment; move this state to Redis or another shared store before scaling horizontally.

## Mail and demo data
The local profile logs password reset codes through the development mail implementation. Production uses SMTP. Local demo credentials are `demo@cityguide.app` / `Demo@1234`. Do not enable development reset-code responses outside local. Configure SMTP in Render so users can receive password reset messages.

## API docs and health
Local Swagger UI: `/swagger-ui.html`. Production disables OpenAPI docs. Health: `/actuator/health`.

## Tests
Run `./mvnw verify`. Tests use PostgreSQL Testcontainers; Docker must be available. Tests cover application startup, seed counts, protected-route error shape, registration/login, and opening-hours edge cases.

## Project scope notes
- Maps and turn-by-turn directions are provided by the Flutter map/directions integration. The API supplies place coordinates and details.
- The API keeps the app's last-viewed city on the Flutter device.
- The current login lockout cache is local to one backend instance.
