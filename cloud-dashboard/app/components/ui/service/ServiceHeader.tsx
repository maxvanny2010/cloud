import { IconServer, IconOrg, IconNetwork, IconConfig, IconLicense } from "../icons";
import type { ServiceConfig } from "@/app/types";
import React from "react";

const ICON_MAP: Record<string, React.ReactNode> = {
  license: <IconLicense size={26} color="#a0a0cc" />,
  org:     <IconOrg     size={26} color="#a0a0cc" />,
  network: <IconNetwork size={26} color="#a0a0cc" />,
  config:  <IconConfig  size={26} color="#a0a0cc" />,
  server:  <IconServer  size={26} color="#a0a0cc" />,
};

export default function ServiceHeader({ service }: { service: ServiceConfig }) {
  return (
    <div>
      <div style={{
        width: 44, height: 44, borderRadius: 12, marginBottom: 12,
        background: "rgba(100,80,255,0.1)", border: "1px solid rgba(100,80,255,0.2)",
        display: "flex", alignItems: "center", justifyContent: "center",
      }}>
        {ICON_MAP[service.icon] ?? <IconServer size={26} color="#a0a0cc" />}
      </div>
      <div style={{ fontSize: 14, fontWeight: 600, color: "#e8e8f0" }}>{service.name}</div>
      <div style={{ fontSize: 11, color: "#555577", marginTop: 3, fontFamily: "monospace" }}>
        :{service.port}:
      </div>
    </div>
  );
}
