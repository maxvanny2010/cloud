"use client";
import {useState} from "react";

interface Props {
    href: string;
    label: string;
    title?: string;
    color?: string;
    hoverColor?: string;
    hoverBorder?: string;
    hoverGlow?: string;
}

export default function NavLink({
                                    href,
                                    label,
                                    title,
                                    color = "#2a2a44",
                                    hoverColor = "#a090ff",
                                    hoverBorder = "rgba(100,80,255,0.4)",
                                    hoverGlow = "rgba(100,80,255,0.2)"
                                }: Props) {
    const [hovered, setHovered] = useState(false);

    return (
        <a
            href={href}
            target="_blank"
            rel="noopener noreferrer"
            title={title}
            onMouseEnter={() => setHovered(true)}
            onMouseLeave={() => setHovered(false)}
            style={{
                fontSize: 10,
                fontFamily: "monospace",
                padding: "2px 7px",
                borderRadius: 5,
                textDecoration: "none",
                transition: "color 0.15s, border-color 0.15s, box-shadow 0.15s",
                color: hovered ? hoverColor : color,
                border: `1px solid ${hovered ? hoverBorder : "#1a1a33"}`,
                boxShadow: hovered ? `0 0 8px ${hoverGlow}` : "none",
                cursor: "pointer",
            }}
        >
            {label}
        </a>
    );
}