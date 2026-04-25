export function TooltipRow({
                               label, value, mono = false,
                           }: {
    label: string;
    value?: string;
    mono?: boolean;
}) {
    if (!value) return null;
    return (
        <div style={{
            display: "flex", gap: 8, marginBottom: 5,
            fontSize: 11, alignItems: "flex-start",
        }}>
      <span style={{
          color: "#555577", fontFamily: "monospace",
          minWidth: 58, flexShrink: 0,
      }}>
        {label}:
      </span>
            <span style={{
                color: "#c8c8e0",
                fontFamily: mono ? "monospace" : "inherit",
                wordBreak: "break-all",
            }}>
        {value}
      </span>
        </div>
    );
}