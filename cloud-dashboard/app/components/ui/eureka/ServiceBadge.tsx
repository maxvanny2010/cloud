import StatusBadge from "../../StatusBadge";

export default function ServiceBadge({ port }: { port?: string }) {
  return (
    <div style={{ display: "flex", flexDirection: "column", alignItems: "flex-end", gap: 6 }}>
      <StatusBadge status="up" />
      {port && (
        <div style={{
          fontSize: 10, color: "#444466", fontFamily: "monospace",
          padding: "2px 6px", border: "1px solid #222244", borderRadius: 4,
        }}>
          :{port}
        </div>
      )}
    </div>
  );
}
