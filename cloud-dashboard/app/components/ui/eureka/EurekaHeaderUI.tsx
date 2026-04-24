import { IconGlobe } from "../icons";
import EurekaRegistryInfo from "./EurekaRegistryInfo";

export default function EurekaHeaderUI({ count }: { count: number }) {
  return (
    <div className="glass-card" style={{
      padding: "16px 22px",
      background: "rgba(100,80,255,0.06)",
      borderColor: "rgba(100,80,255,0.15)",
    }}>
      <div style={{ display: "flex", alignItems: "center", gap: 12 }}>
        <div style={{
          width: 36, height: 36, borderRadius: 10,
          background: "rgba(100,80,255,0.15)",
          border: "1px solid rgba(100,80,255,0.25)",
          display: "flex", alignItems: "center", justifyContent: "center",
        }}>
          <IconGlobe size={18} color="#c0b8ff" />
        </div>
        <EurekaRegistryInfo count={count} />
      </div>
    </div>
  );
}
