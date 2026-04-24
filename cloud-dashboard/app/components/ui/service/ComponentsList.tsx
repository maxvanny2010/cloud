import type { HealthStatus } from "@/app/types";

export default function ComponentsList({
  components,
}: {
  components: NonNullable<HealthStatus["components"]>;
}) {
  return (
    <div style={{
      background: "rgba(0,0,0,0.25)", borderRadius: 10, padding: "10px 14px",
      borderLeft: "2px solid rgba(255,255,255,0.08)",
    }}>
      {Object.entries(components).slice(0, 4).map(([key, val]) => (
        <div key={key} style={{
          display: "flex", justifyContent: "space-between",
          fontSize: 11, marginBottom: 4, fontFamily: "monospace",
        }}>
          <span style={{ color: "#555577" }}>{key}</span>
          <span style={{ color: val.status === "UP" ? "#00ff88" : "#ff4466" }}>{val.status}</span>
        </div>
      ))}
    </div>
  );
}
