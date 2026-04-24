import { MESSAGES } from "@/app/constants";

export default function DownBanner() {
  return (
    <div style={{
      background: "rgba(255,68,102,0.08)", borderRadius: 10, padding: "10px 14px",
      border: "1px solid rgba(255,68,102,0.15)",
      fontSize: 11, color: "#ff4466", fontFamily: "monospace",
    }}>
      {MESSAGES.SERVICE_UNAVAILABLE}
    </div>
  );
}
