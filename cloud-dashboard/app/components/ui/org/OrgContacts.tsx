import type {Organization} from "@/app/types";
import {ORG_CONTACT_FIELDS} from "@/app/constants";
import {IconMail, IconPhone, IconUser} from "../icons";
import React from "react";

const ICON_MAP: Record<string, React.ReactNode> = {
    user: <IconUser size={14} color="#6666aa"/>,
    mail: <IconMail size={14} color="#6666aa"/>,
    phone: <IconPhone size={14} color="#6666aa"/>,
};

export default function OrgContacts({org}: { org: Organization }) {
    return (
        <div style={{
            marginTop: 16, paddingTop: 16,
            borderTop: "1px solid rgba(255,255,255,0.06)",
            display: "grid", gap: 10,
        }}>
            {ORG_CONTACT_FIELDS.map(({label, key, icon}) => (
                <div key={label} style={{display: "flex", alignItems: "center", gap: 10}}>
                    <div style={{
                        width: 26, height: 26, borderRadius: 6,
                        background: "rgba(100,80,255,0.08)",
                        border: "1px solid rgba(100,80,255,0.15)",
                        display: "flex", alignItems: "center", justifyContent: "center",
                        flexShrink: 0,
                    }}>
                        {ICON_MAP[icon]}
                    </div>
                    <div>
                        <div style={{fontSize: 10, color: "#444466", fontFamily: "monospace", marginBottom: 1}}>
                            {label}
                        </div>
                        <div style={{fontSize: 12, color: "#c0c0d0"}}>{org[key]}</div>
                    </div>
                </div>
            ))}
        </div>
    );
}
