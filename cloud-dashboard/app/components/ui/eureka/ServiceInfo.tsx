import { MESSAGES } from "../../../constants";

export default function ServiceInfo({ name, count }: { name: string; count: number }) {
  return (
    <div>
      <div style={{ fontSize: 15, fontWeight: 600, color: "#e8e8f0" }}>{name}</div>
      <div style={{ fontSize: 11, color: "#666688", marginTop: 3, fontFamily: "monospace" }}>
        {count} instance{count > 1 ? "s" : ""} · {MESSAGES.SPRING_BOOT_LABEL}
      </div>
    </div>
  );
}
