const API_URL = process.env.NEXT_PUBLIC_API_URL ?? "http://localhost:8080";

export type Summary = {
  periodDays: number;
  grossRevenue: number;
  discounts: number;
  netRevenue: number;
  cogs: number;
  estimatedWasteCost: number;
  estimatedProfit: number;
  profitMargin: number;
  transactions: number;
};

export type Insight = {
  productId: number;
  sku: string;
  product: string;
  category: string;
  stock: number;
  unitMargin: number;
  margin: number;
  classification: string;
  recommendation: string;
};

export type CategoryMetric = {
  category: string;
  revenue: number;
  profit: number;
  margin: number;
};

export async function getSummary(days = 30): Promise<Summary> {
  const response = await fetch(`${API_URL}/api/analytics/summary?days=${days}`, { cache: "no-store" });
  if (!response.ok) throw new Error("Unable to load profitability summary");
  return response.json();
}

export async function getInsights(): Promise<Insight[]> {
  const response = await fetch(`${API_URL}/api/analytics/insights`, { cache: "no-store" });
  if (!response.ok) throw new Error("Unable to load product insights");
  return response.json();
}

export async function getCategories(days = 30): Promise<CategoryMetric[]> {
  const response = await fetch(`${API_URL}/api/analytics/categories?days=${days}`, { cache: "no-store" });
  if (!response.ok) throw new Error("Unable to load category analytics");
  return response.json();
}

export async function simulatePromotion(input: {
  productId: number;
  discountRate: number;
  expectedDemandLift: number;
  baselineUnits: number;
}) {
  const response = await fetch(`${API_URL}/api/pricing/simulate`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(input)
  });
  const data = await response.json();
  if (!response.ok) throw new Error(data.error ?? "Unable to simulate promotion");
  return data;
}
