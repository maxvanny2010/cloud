"use client";
import {STATUS_CONFIG} from "../constants";
import type {ServiceStatus} from "../types";

export default function StatusBadge({status}: { status: ServiceStatus }) {
    const cfg = STATUS_CONFIG[status];
    return (
        <span style={{
            display: "inline-flex", alignItems: "center", gap: 6,
            padding: "3px 10px", borderRadius: 20,
            background: `${cfg.color}18`,
            border: `1px solid ${cfg.color}44`,
            color: cfg.color, fontSize: 11, fontWeight: 700, letterSpacing: 1,
            boxShadow: cfg.glow,
        }}>
      <span className={status === "up" ? "pulse-dot" : ""} style={{
          width: 7, height: 7, borderRadius: "50%",
          background: cfg.color, boxShadow: cfg.glow, display: "block",
      }}/>
            {cfg.label}
    </span>
    );
}
