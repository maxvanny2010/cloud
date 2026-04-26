"use client";
import {useState} from "react";
import {PORT_URLS, SERVICES} from "@/app/constants";

export default function DashboardFooter() {
    return (
        <footer style={{
            marginTop: 64, paddingTop: 24,
            borderTop: "1px solid rgba(255,255,255,0.04)",
            display: "flex", justifyContent: "space-between", alignItems: "center",
        }}>
      <span style={{fontSize: 11, color: "#2a2a4a", fontFamily: "monospace"}}>
        © 2026 Cloud Dashboard https://github.com/maxvanny2010
      </span>
            <div style={{display: "flex", gap: 8}}>
                {SERVICES.map((s) => (
                    <PortBadge key={s.port} service={s}/>
                ))}
            </div>
        </footer>
    );
}

function PortBadge({service}: { service: typeof SERVICES[number] }) {
    const [hovered, setHovered] = useState(false);
    const url = PORT_URLS[service.port] ?? `http://localhost:${service.port}`;

    return (
        <a
            href={url}
            target="_blank"
            rel="noopener noreferrer"
            title={`${service.name} · ${service.description}`}
            onMouseEnter={() => setHovered(true)}
            onMouseLeave={() => setHovered(false)}
            style={{
                fontSize: 10,
                fontFamily: "monospace",
                padding: "2px 7px",
                borderRadius: 5,
                textDecoration: "none",
                transition: "color 0.15s, border-color 0.15s, box-shadow 0.15s",
                color: hovered ? "#a090ff" : "#2a2a44",
                border: `1px solid ${hovered ? "rgba(100,80,255,0.4)" : "#1a1a33"}`,
                boxShadow: hovered ? "0 0 8px rgba(100,80,255,0.2)" : "none",
                cursor: "pointer",
            }}
        >
            :{service.port}
        </a>
    );
}