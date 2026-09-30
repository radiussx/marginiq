# MarginIQ API

## Analytics
`GET /api/analytics/summary?days=30`

Returns gross revenue, discounts, net revenue, COGS, estimated waste cost, estimated profit, margin and transaction count.

`GET /api/analytics/insights`

Returns product-level margin and inventory classifications with recommended business actions.

## Products
`GET /api/products`

`GET /api/products/top-inventory`

## Promotion simulator
`POST /api/pricing/simulate`

Example:
```json
{
  "productId": 2,
  "discountRate": 0.15,
  "expectedDemandLift": 0.25,
  "baselineUnits": 100
}
```
