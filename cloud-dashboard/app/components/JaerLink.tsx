"use client";
import NavLink from "./NavLink";

export default function JaegerLink() {
    const isDev = window.location.hostname === "localhost";
    const url = isDev ? "http://localhost:16686" : `${window.location.origin}/jaeger/`;

    return (
        <NavLink
            href={url}
            label="jaeger"
            title="Jaeger Distributed Tracing"
            hoverColor="#00ff88"
            hoverBorder="rgba(0,255,136,0.4)"
            hoverGlow="rgba(0,255,136,0.2)"
        />
    );
}