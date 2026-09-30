import "./globals.css";
export const metadata = { title: "MarginIQ", description: "Retail profitability intelligence" };
export default function RootLayout({children}:{children:React.ReactNode}) {
  return <html lang="en"><body>{children}</body></html>;
}
