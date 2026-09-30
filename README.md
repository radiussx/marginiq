# MarginIQ

**Retail Profitability Intelligence Platform**

MarginIQ is a full-stack business analytics application that combines sales, product cost, inventory, supplier and waste data to surface margin opportunities and operational risk.

## Business problem
Revenue alone does not tell a retailer whether a product is economically healthy. MarginIQ models the economics behind each SKU and helps answer:
- Which products generate healthy contribution margin?
- Where is excess inventory tying up capital?
- Which products combine weak margins with overstock risk?
- Could a proposed promotion improve or reduce contribution profit?

## Stack
**Backend:** Java 21, Spring Boot, Spring Data JPA, PostgreSQL, Redis, OpenAPI  
**Frontend:** Next.js, React, TypeScript  
**Testing:** JUnit, Mockito  
**DevOps:** Docker Compose, GitHub Actions

## Key features
- Product, supplier, inventory and transaction data model
- 30/60/90-day profitability calculations
- Revenue, discounts, COGS, waste and margin analytics
- Product opportunity/risk classification engine
- Promotion scenario simulator with explicit demand-lift assumptions
- Seeded reproducible retail transaction dataset
- Swagger API documentation
- CI pipeline for backend tests and frontend builds

## Run locally
```bash
docker compose up --build
```

- Dashboard: http://localhost:3000
- API: http://localhost:8080
- Swagger: http://localhost:8080/swagger-ui.html

## Example API
```bash
curl "http://localhost:8080/api/analytics/summary?days=30"
```

```bash
curl -X POST "http://localhost:8080/api/pricing/simulate" \
  -H "Content-Type: application/json" \
  -d '{"productId":2,"discountRate":0.15,"expectedDemandLift":0.25,"baselineUnits":100}'
```

## Architecture
See [`docs/architecture.md`](docs/architecture.md) and [`docs/api.md`](docs/api.md).

## Roadmap
- Live dashboard integration with backend APIs
- JWT authentication and role-based authorization
- Inventory movement ledger
- Category and SKU trend analytics
- Testcontainers integration tests
- Deployment
