"use client";
import { useState, useEffect } from "react";

export default function Clock() {
  const [time, setTime] = useState<string | null>(null);
  const [date, setDate] = useState<string | null>(null);

  useEffect(() => {
    const update = () => {
      const now = new Date();
      setTime(now.toLocaleTimeString());
      setDate(now.toLocaleDateString("en-US", {
        weekday: "short", month: "short", day: "numeric", year: "numeric",
      }));
    };
    update();
    const id = setInterval(update, 1000);
    return () => clearInterval(id);
  }, []);

  // До mount — пустой блок той же ширины, нет hydration mismatch
  if (!time) return <div style={{ width: 180 }} />;

  return (
    <div style={{ textAlign: "right" }}>
      <div style={{
        fontSize: 26, fontWeight: 700,
        fontFamily: "'Space Mono', monospace",
        color: "#7070bb", letterSpacing: 3,
      }}>
        {time}
      </div>
      <div style={{ fontSize: 11, color: "#33335a", marginTop: 6, fontFamily: "monospace" }}>
        {date}
      </div>
      <div style={{
        marginTop: 10, display: "inline-flex", alignItems: "center", gap: 6,
        fontSize: 10, padding: "3px 10px", borderRadius: 20,
        background: "rgba(0,255,136,0.08)", border: "1px solid rgba(0,255,136,0.2)",
        color: "#00ff88", fontFamily: "monospace", letterSpacing: 1,
      }}>
        <span className="pulse-dot" style={{
          width: 6, height: 6, borderRadius: "50%",
          background: "#00ff88", display: "block",
        }} />
        LIVE
      </div>
    </div>
  );
}
