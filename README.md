# Loan Recovery Management System

Java 21 / Spring Boot / Spring Data JPA / MySQL REST backend.

## Included

- Existing Employee, Loan Case and Assignment modules retained from the supplied source.
- DTO request/response classes and mapper classes for the existing modules.
- User account provisioning, BCrypt password hashing, login, short-lived JWT access tokens, rotating opaque refresh tokens, and logout/revocation.
- Visit, Payment and Resolution CRUD MVP modules with request DTOs, response DTOs, mappers, repositories and services.
- Dashboard summary endpoint.
- Global API exception handling and Actuator health/info.

## Requirements

- JDK 21
- Maven 3.9+ (or a working Maven Wrapper)
- MySQL 8+

## Setup

1. Create database: `CREATE DATABASE loan_management_db;`
2. Copy `.env.example` to `.env` and set DB credentials, a random JWT secret (at least 32 bytes), and bootstrap admin credentials.
3. Open the folder containing `pom.xml` in IntelliJ as a Maven project.
4. Reload Maven and run `LoanRecoveryManagementSystemApplication`.
5. The bootstrap admin is created once when `BOOTSTRAP_TENANT_ID`, `BOOTSTRAP_USERNAME`, and `BOOTSTRAP_PASSWORD` are set and no matching account exists.

If your IDE does not load `.env` automatically, set the same values as environment variables in the Run Configuration.

## Authentication

`POST /api/auth/login`
```json
{"tenantId":1,"username":"superadmin","password":"ChangeThisAdminPassword123!"}
```
Use `accessToken` as `Authorization: Bearer <token>`.

`POST /api/auth/refresh`
```json
{"refreshToken":"<refreshToken from login>"}
```
A refresh token is one-time rotated; store the new token and discard the old one.

`POST /api/auth/logout`
```json
{"refreshToken":"<current refresh token>"}
```
Logout revokes the supplied refresh token. Already-issued access JWTs remain valid until expiry (15 minutes by default).

`POST /api/users` (SUPER_ADMIN or ADMIN only) provisions another user:
```json
{"tenantId":1,"username":"agent01","password":"StrongPassword123!","role":"AGENT","employeeId":4}
```
Supported roles: SUPER_ADMIN, ADMIN, MANAGER, TEAMLEAD, AGENT, TELECALLER.

## API endpoints

Existing:
- `/api/employees`
- `/api/cases`
- `/api/assignments`

Added:
- `/api/visits` (POST, GET list, GET by id, DELETE)
- `/api/payments` (POST, GET list, GET by id, DELETE)
- `/api/resolutions` (POST, GET list, GET by id, DELETE)
- `/api/dashboard/summary?tenantId=1`
- `/actuator/health`

New module request examples:

Visit:
```json
{"tenantId":1,"caseId":1,"employeeId":4,"visitDate":"2026-11-19T10:30:00","outcome":"CUSTOMER_NOT_AVAILABLE","remarks":"Follow-up required","status":"OPEN"}
```
Payment:
```json
{"tenantId":1,"caseId":1,"amount":2500.00,"paymentMode":"UPI","transactionReference":"TXN-10001","paidAt":"2026-11-19T12:00:00","status":"PAID"}
```
Resolution:
```json
{"tenantId":1,"caseId":1,"resolutionType":"SETTLEMENT","description":"Customer agreed to settlement plan","resolvedAt":"2026-11-19T12:30:00","status":"PROPOSED"}
```

## Important security and business-rule notes

This is an implementation baseline, not a certified production deployment. The existing Employee/Case/Assignment controllers accept tenant IDs from request parameters/body. The JWT contains tenant and role claims, but tenant ownership must be enforced consistently at every service boundary before production. The newly added CRUD modules also accept tenantId in their request/query. Do not expose this service publicly until tenant authorization, CORS allowlists, rate limiting, audit trails, password reset/MFA policy, refresh-token family reuse detection, and security tests have been completed.

The visit/payment/resolution modules are intentionally generic MVP workflows. Confirm your organization's exact business rules (cash reconciliation, payment allocation/refunds, visit geolocation/proof, approval matrix, settlement rules) before treating them as final operational workflows.

## Build and test

Run:
```bash
mvn clean test
mvn spring-boot:run
```
or on Windows with Maven Wrapper:
```powershell
.\mvnw.cmd clean test
.\mvnw.cmd spring-boot:run
```

This delivery was assembled from the supplied source and has not been compiled in this environment. Run the commands above locally and resolve any environment-specific dependency/build errors before using it.
