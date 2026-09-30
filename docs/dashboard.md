# MarginIQ Dashboard

The dashboard is connected to the Spring Boot API rather than hard-coded KPI values.

## Live data
- `GET /api/analytics/summary?days=30` powers revenue, profit, margin and transaction KPIs.
- `GET /api/analytics/categories?days=30` powers category profit contribution.
- `GET /api/analytics/insights` powers operational risk/opportunity signals.
- `POST /api/pricing/simulate` powers the interactive promotion scenario lab.

## Promotion modelling
The simulator does not claim to forecast demand. `expectedDemandLift` is an explicit scenario assumption entered by the user. MarginIQ then calculates the contribution-profit impact of the assumed lift and proposed discount.
