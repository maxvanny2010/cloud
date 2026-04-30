"use client";
import NavLink from "./NavLink";

export default function EurekaLink() {
    const isDev = window.location.hostname === "localhost";
    const url = isDev ? "http://localhost:8070" : `${window.location.origin}/eureka/`;

    return (
        <NavLink
            href={url}
            label="eureka"
            title="Eureka Service Registry"
            hoverColor="#6496ff"
            hoverBorder="rgba(100,150,255,0.4)"
            hoverGlow="rgba(100,150,255,0.2)"
        />
    );
}