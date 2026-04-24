import Title from "./Title";
import Clock from "@/app/components/layout/Clock";

export default function DashboardHeader() {
  return (
    <header className="animate-fade-up" style={{
      display: "flex", justifyContent: "space-between",
      alignItems: "flex-start", marginBottom: 52,
    }}>
      <Title />
      <Clock />
    </header>
  );
}
