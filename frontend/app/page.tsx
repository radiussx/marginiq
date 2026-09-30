import { getCategories, getInsights, getSummary } from "../lib/api";
import PromotionSimulator from "../components/PromotionSimulator";

const money = (value: number) =>
  new Intl.NumberFormat("en-CA", { style: "currency", currency: "CAD", maximumFractionDigits: 0 }).format(value);

const pct = (value: number) => `${(Number(value) * 100).toFixed(1)}%`;

const prettyClass = (value: string) =>
  value.toLowerCase().split("_").map(word => word[0].toUpperCase() + word.slice(1)).join(" ");

export default async function Home() {
  let summary, insights, categories;
  try {
    [summary, insights, categories] = await Promise.all([
      getSummary(30),
      getInsights(),
      getCategories(30)
    ]);
  } catch {
    return (
      <main className="shell">
        <div className="offline">
          <span className="kicker">MARGINIQ</span>
          <h1>Backend unavailable</h1>
          <p>Start PostgreSQL and the Spring Boot API, then refresh this page.</p>
          <code>docker compose up --build</code>
        </div>
      </main>
    );
  }

  const maxProfit = Math.max(...categories.map(c => Number(c.profit)), 1);
  const riskCount = insights.filter(i => i.classification !== "HEALTHY" && i.classification !== "GROWTH_OPPORTUNITY").length;

  return (
    <main className="shell">
      <header className="hero">
        <div>
          <span className="kicker">RETAIL PROFITABILITY INTELLIGENCE</span>
          <h1>MarginIQ</h1>
          <p>Turn sales, cost and inventory data into decisions that protect margin.</p>
        </div>
        <div className="liveBadge"><span/> LIVE DATA · 30 DAYS</div>
      </header>

      <section className="metricGrid">
        <article className="metric"><span>Net revenue</span><strong>{money(Number(summary.netRevenue))}</strong><small>{summary.transactions} transactions</small></article>
        <article className="metric"><span>Estimated profit</span><strong>{money(Number(summary.estimatedProfit))}</strong><small>after COGS + waste</small></article>
        <article className="metric"><span>Profit margin</span><strong>{pct(Number(summary.profitMargin))}</strong><small>{money(Number(summary.discounts))} discounts</small></article>
        <article className="metric"><span>Risk signals</span><strong>{riskCount}</strong><small>products requiring review</small></article>
      </section>

      <section className="twoCol">
        <article className="panel">
          <div className="sectionHeading"><div><span className="kicker">CATEGORY ECONOMICS</span><h2>Profit contribution</h2></div><span className="muted">Last 30 days</span></div>
          <div className="categoryList">
            {categories.map(c => (
              <div className="categoryRow" key={c.category}>
                <div className="categoryMeta"><strong>{c.category}</strong><span>{money(Number(c.profit))} · {pct(Number(c.margin))} margin</span></div>
                <div className="track"><div className="fill" style={{width: `${Math.max(6, Number(c.profit) / maxProfit * 100)}%`}}/></div>
              </div>
            ))}
          </div>
        </article>

        <article className="panel">
          <div className="sectionHeading"><div><span className="kicker">BUSINESS SIGNALS</span><h2>What needs attention</h2></div></div>
          <div className="signals">
            {insights.slice().sort((a,b) => a.classification === "HEALTHY" ? 1 : b.classification === "HEALTHY" ? -1 : 0).slice(0,5).map(i => (
              <div className="signal" key={i.productId}>
                <div><strong>{i.product}</strong><span>{i.category} · {i.stock} units</span></div>
                <div className="signalRight"><b>{prettyClass(i.classification)}</b><span>{pct(Number(i.margin))}</span></div>
                <p>{i.recommendation}</p>
              </div>
            ))}
          </div>
        </article>
      </section>

      <PromotionSimulator />
    </main>
  );
}
