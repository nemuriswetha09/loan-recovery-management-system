# Loan Recovery Management System — implementation package

This package is based on the project ZIP supplied in the conversation. The existing Employee, Case and Assignment source files are retained. The following additional MVP modules have been added:

- Visit Management: schedule a visit, list visits, fetch a visit, list visits by case, update visit status.
- Payment Management: record a payment as PENDING, list/fetch payments, list payments by case, update payment status.
- Resolution Management: submit a case resolution, list/fetch resolutions, update resolution status.
- Dashboard: tenant summary counts and successful payment amount.

## Environment

1. Install JDK 21 and Maven 3.9+ (or configure IntelliJ to use an installed Maven distribution).
2. Create the MySQL database:

```sql
CREATE DATABASE loan_management_db;
```

3. Copy `.env.example` to `.env` and set your local MySQL username/password.
4. Open the folder containing `pom.xml` in IntelliJ and reload the Maven project.
5. Run `LoanRecoveryManagementSystemApplication` or execute `mvn spring-boot:run`.
6. Base URL: `http://localhost:8080`.

Hibernate is configured with `ddl-auto=update` for local development. Use versioned database migrations (Flyway/Liquibase) before production deployment.

## New API endpoints

### Visits
- `POST /api/visits`
- `GET /api/visits?tenantId=1`
- `GET /api/visits/{id}?tenantId=1`
- `GET /api/visits/case/{caseId}?tenantId=1`
- `PATCH /api/visits/{id}/status?tenantId=1&status=COMPLETED`

Example POST:
```json
{
  "tenantId": 1,
  "caseId": 1,
  "employeeId": 4,
  "scheduledAt": "2026-11-20T10:30:00",
  "notes": "Customer requested a morning visit",
  "location": "Customer address"
}
```
Allowed visit statuses: `SCHEDULED`, `COMPLETED`, `MISSED`, `CANCELLED`.

### Payments
- `POST /api/payments`
- `GET /api/payments?tenantId=1`
- `GET /api/payments/{id}?tenantId=1`
- `GET /api/payments/case/{caseId}?tenantId=1`
- `PATCH /api/payments/{id}/status?tenantId=1&status=SUCCESS`

Example POST:
```json
{
  "tenantId": 1,
  "caseId": 1,
  "collectedById": 4,
  "amount": 2500.00,
  "mode": "UPI",
  "paymentReference": "PAY-2026-0001",
  "remarks": "Part payment"
}
```
Allowed modes: `CASH`, `UPI`, `NEFT`, `IMPS`, `RTGS`, `CHEQUE`, `DD`, `OTHER`. New payments are created as `PENDING`. Only mark a payment `SUCCESS` after verifying the bank/payment gateway settlement. A successful payment can only be moved to `REVERSED` through the status endpoint.

### Resolutions
- `POST /api/resolutions`
- `GET /api/resolutions?tenantId=1`
- `GET /api/resolutions/{id}?tenantId=1`
- `PATCH /api/resolutions/{id}/status?tenantId=1&status=APPROVED`

Example POST:
```json
{
  "tenantId": 1,
  "caseId": 1,
  "resolvedById": 3,
  "type": "FULL_PAYMENT",
  "description": "Customer paid the full outstanding amount",
  "referenceNumber": "RES-2026-0001"
}
```
Resolution types: `FULL_PAYMENT`, `SETTLEMENT`, `RESTRUCTURE`, `WAIVER`, `LEGAL`, `CUSTOMER_UNTRACEABLE`, `OTHER`. Statuses: `SUBMITTED`, `APPROVED`, `REJECTED`, `COMPLETED`.

### Dashboard
- `GET /api/dashboard/summary?tenantId=1`

## Important implementation boundary / security warning

This is an incremental development/MVP package, **not a production-certified build**. The existing Employee, Case and Assignment endpoints still accept `tenantId` from request parameters/body. The newly added endpoints also accept tenant IDs from requests. This is not a safe tenant-isolation boundary for a deployed multi-tenant system. Before production, implement authentication (Spring Security), short-lived JWT access tokens, rotating/revocable refresh tokens, role/permission authorization, and derive tenant identity from the authenticated principal rather than trusting request-supplied tenant IDs.

Other production hardening still required: DTO-only responses (avoid returning JPA entities directly), transactional service layer for new modules, payment gateway/bank reconciliation and idempotency, audit trail, pagination/filtering, concurrency controls, database migrations, rate limiting, secrets management, integration/security tests, and review of all business rules. The payment endpoint records a payment claim; it does not process or verify a real transfer.

The ZIP deliberately excludes the uploaded `.env`, `.git` history, IntelliJ workspace metadata and compiled `target` output. Use `.env.example` to create your local secrets file.
