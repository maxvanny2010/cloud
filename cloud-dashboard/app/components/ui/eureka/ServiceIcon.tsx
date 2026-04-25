import {IconConfig, IconLicense, IconNetwork, IconOrg, IconServer} from "../icons";
import {EUREKA_SERVICE_ICONS} from "@/app/constants";
import React from "react";

const ICON_MAP: Record<string, React.ReactNode> = {
    license: <IconLicense size={22} color="#64c8ff"/>,
    org: <IconOrg size={22} color="#64c8ff"/>,
    network: <IconNetwork size={22} color="#64c8ff"/>,
    config: <IconConfig size={22} color="#64c8ff"/>,
};

export default function ServiceIcon({name}: { name: string }) {
    const iconKey = EUREKA_SERVICE_ICONS[name] ?? "server";
    return (
        <div style={{
            width: 44, height: 44, borderRadius: 12,
            background: "rgba(100,200,255,0.07)",
            border: "1px solid rgba(100,200,255,0.14)",
            display: "flex", alignItems: "center", justifyContent: "center",
        }}>
            {ICON_MAP[iconKey] ?? <IconServer size={22} color="#64c8ff"/>}
        </div>
    );
}
