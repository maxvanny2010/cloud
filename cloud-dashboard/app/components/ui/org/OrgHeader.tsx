import type { Organization } from "../../../types";
import { IconOrg } from "../icons";

export default function OrgHeader({ org }: { org: Organization }) {
  return (
    <div style={{ display: "flex", justifyContent: "space-between", alignItems: "flex-start" }}>
      <div>
        <div style={{ fontSize: 16, fontWeight: 700, color: "#e8e8f0", marginBottom: 4 }}>
          {org.name}
        </div>
        <div style={{
          display: "inline-flex", fontSize: 10, fontFamily: "monospace", color: "#555577",
          background: "rgba(255,255,255,0.04)", padding: "2px 8px",
          borderRadius: 6, border: "1px solid rgba(255,255,255,0.06)",
        }}>
          {org.id.substring(0, 8)}...
        </div>
      </div>
      <div style={{
        width: 40, height: 40, borderRadius: 12,
        background: "rgba(100,80,255,0.12)", border: "1px solid rgba(100,80,255,0.2)",
        display: "flex", alignItems: "center", justifyContent: "center",
      }}>
        <IconOrg size={20} color="#a090ff" />
      </div>
    </div>
  );
}
