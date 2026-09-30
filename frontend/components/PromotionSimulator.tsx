"use client";

import { FormEvent, useState } from "react";
import { simulatePromotion } from "../lib/api";

type Result = {
  product: string;
  currentPrice: number;
  promotionalPrice: number;
  baselineUnits: number;
  projectedUnits: number;
  currentProfit: number;
  projectedProfit: number;
  profitChange: number;
  recommendation: string;
};

export default function PromotionSimulator() {
  const [productId, setProductId] = useState(2);
  const [discount, setDiscount] = useState(15);
  const [lift, setLift] = useState(25);
  const [units, setUnits] = useState(100);
  const [result, setResult] = useState<Result | null>(null);
  const [error, setError] = useState("");

  async function submit(e: FormEvent) {
    e.preventDefault();
    setError("");
    try {
      setResult(await simulatePromotion({
        productId,
        discountRate: discount / 100,
        expectedDemandLift: lift / 100,
        baselineUnits: units
      }));
    } catch (err) {
      setError(err instanceof Error ? err.message : "Simulation failed");
    }
  }

  return (
    <section className="panel simulator">
      <div className="sectionHeading">
        <div>
          <span className="kicker">SCENARIO LAB</span>
          <h2>Promotion simulator</h2>
        </div>
        <span className="muted">Model contribution profit before discounting.</span>
      </div>

      <form className="simForm" onSubmit={submit}>
        <label>Product ID<input type="number" min="1" value={productId} onChange={e => setProductId(Number(e.target.value))}/></label>
        <label>Discount %<input type="number" min="0" max="90" value={discount} onChange={e => setDiscount(Number(e.target.value))}/></label>
        <label>Demand lift %<input type="number" min="0" value={lift} onChange={e => setLift(Number(e.target.value))}/></label>
        <label>Baseline units<input type="number" min="1" value={units} onChange={e => setUnits(Number(e.target.value))}/></label>
        <button type="submit">Run scenario</button>
      </form>

      {error && <p className="error">{error}</p>}
      {result && (
        <div className="scenarioResult">
          <div><span>Product</span><strong>{result.product}</strong></div>
          <div><span>Promo price</span><strong>${Number(result.promotionalPrice).toFixed(2)}</strong></div>
          <div><span>Projected units</span><strong>{result.projectedUnits}</strong></div>
          <div><span>Projected profit</span><strong>${Number(result.projectedProfit).toFixed(2)}</strong></div>
          <div><span>Profit change</span><strong>{(Number(result.profitChange) * 100).toFixed(1)}%</strong></div>
          <p>{result.recommendation}</p>
        </div>
      )}
    </section>
  );
}
