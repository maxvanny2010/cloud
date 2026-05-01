"use client";
import {SERVICES} from "@/app/constants";
import PortBadge from "@/app/components/PortBadge";

export default function DashboardFooter() {
    return (
        <footer style={{
            marginTop: 64, paddingTop: 24,
            borderTop: "1px solid rgba(255,255,255,0.04)",
            display: "flex", justifyContent: "space-between", alignItems: "center",
        }}>
            <a href="https://github.com/maxvanny2010/cloud"
               target="_blank"
               rel="noopener noreferrer"
               style={{fontSize: 11, color: "#2a2a4a", fontFamily: "monospace", textDecoration: "none"}}
            >
                © 2026 Cloud Dashboard · github.com/maxvanny2010
            </a>
            <div style={{display: "flex", gap: 8, alignItems: "center"}}>
                {SERVICES.map((s) => (
                    <PortBadge key={s.port} service={s}/>
                ))}
            </div>
        </footer>
    );
}