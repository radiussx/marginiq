const metrics = [
  ["Revenue", "$184,240", "+12.4%"],
  ["Estimated Profit", "$58,910", "+8.7%"],
  ["Gross Margin", "32.0%", "+1.8%"],
  ["Risk Signals", "14", "Needs review"]
];

export default function Home() {
  return <main className="shell">
    <header><div><span className="eyebrow">RETAIL INTELLIGENCE</span><h1>MarginIQ</h1><p>See where your retail business makes money — and where margin leaks.</p></div><button>Run Scenario</button></header>
    <section className="grid">{metrics.map(([label,value,change])=><article className="card" key={label}><span>{label}</span><strong>{value}</strong><small>{change}</small></article>)}</section>
    <section className="content">
      <article className="panel"><h2>Profitability overview</h2><div className="bars">{[62,78,48,86,70,92,55].map((v,i)=><div className="barWrap" key={i}><div className="bar" style={{height:`${v}%`}}/><span>W{i+1}</span></div>)}</div></article>
      <article className="panel"><h2>Business signals</h2><div className="signal"><b>Growth Opportunity</b><span>Arabica Coffee Beans</span></div><div className="signal"><b>Margin Problem</b><span>Protein Box</span></div><div className="signal"><b>Overstock Risk</b><span>Cold Brew</span></div></article>
    </section>
  </main>;
}
