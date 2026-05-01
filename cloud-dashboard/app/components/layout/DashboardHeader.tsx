import Title from "./Title";
import Clock from "@/app/components/layout/Clock";
import DevTools from "@/app/components/DevTools";

export default function DashboardHeader() {
    return (
        <header className="animate-fade-up" style={{
            display: "flex", justifyContent: "space-between",
            alignItems: "flex-start", marginBottom: 52,
        }}>
            <Title/>
            <div style={{display: "flex", flexDirection: "column", alignItems: "flex-end", gap: 12}}>
                <Clock/>
                <DevTools/>
            </div>
        </header>
    );
}