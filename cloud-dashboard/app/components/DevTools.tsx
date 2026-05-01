"use client";
import NavLink from "@/app/components/NavLink";
import {DEV_TOOLS_LINKS, DEV_TOOLS_STYLES} from "@/app/constants";

const DEV_URLS = {
    jaeger: typeof window !== "undefined" && window.location.hostname === "localhost"
        ? "http://localhost:16686"
        : "/jaeger/",
    grafana: typeof window !== "undefined" && window.location.hostname === "localhost"
        ? "http://localhost:3001"
        : "/grafana/",
};

export default function DevTools() {
    return (
        <div style={DEV_TOOLS_STYLES.container}>
            <span style={DEV_TOOLS_STYLES.label}>dev tools</span>
            {DEV_TOOLS_LINKS.map((link) => (
                <NavLink
                    key={link.key}
                    href={DEV_URLS[link.key as keyof typeof DEV_URLS]}
                    label={link.label}
                    title={link.title}
                    hoverColor={link.hoverColor}
                    hoverBorder={link.hoverBorder}
                    hoverGlow={link.hoverGlow}
                />
            ))}
        </div>
    );
}