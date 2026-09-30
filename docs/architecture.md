# MarginIQ Architecture

MarginIQ uses a layered Spring Boot architecture:

`REST Controller -> Service / Business Logic -> JPA Repository -> PostgreSQL`

## Core domains
- **Supplier**: supplier economics and lead time
- **Product**: SKU, price, cost, inventory, reorder point, estimated waste rate
- **Sale / SaleItem**: immutable transaction snapshots of selling price and cost
- **ProfitabilityService**: revenue, discounts, COGS, waste, profit and margin
- **AnalyticsService**: deterministic business-risk classifications
- **PromotionService**: scenario modelling for discount and demand-lift assumptions

The promotion simulator is deliberately a scenario model rather than an ML forecast. Demand lift is an explicit user assumption so the API does not present an unvalidated prediction as fact.
