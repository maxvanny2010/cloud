"use client";
import {useState} from "react";
import {LICENSE_TYPE_COLORS} from "@/app/constants";
import type {License} from "@/app/types";
import LicenseInfo from "./LicenseInfo";
import LicenseMeta from "./LicenseMeta";

export default function LicenseRowUI({license}: { license: License }) {
    const [hovered, setHovered] = useState(false);
    const typeColor = LICENSE_TYPE_COLORS[license.licenseType] ?? LICENSE_TYPE_COLORS.default;

    return (
        <div
            className="glass-card"
            style={{
                padding: "18px 24px",
                position: "relative",
                cursor: "pointer",
                transition: "border-color 0.2s, box-shadow 0.2s",
                borderColor: hovered ? typeColor.border : undefined,
                boxShadow: hovered ? `0 0 18px ${typeColor.bg}` : undefined,
            }}
            onMouseEnter={() => setHovered(true)}
            onMouseLeave={() => setHovered(false)}
        >
            <div style={{display: "flex", justifyContent: "space-between", alignItems: "flex-start"}}>
                <LicenseInfo license={license}/>
                <LicenseMeta license={license}/>
            </div>

            {hovered && (
                <div style={{
                    position: "absolute",
                    bottom: "calc(100% + 8px)",
                    left: 24,
                    zIndex: 100,
                    background: "rgba(16,14,32,0.97)",
                    border: `1px solid ${typeColor.border}`,
                    borderRadius: 12,
                    padding: "14px 18px",
                    minWidth: 280,
                    maxWidth: 420,
                    boxShadow: `0 8px 32px rgba(0,0,0,0.5), 0 0 12px ${typeColor.bg}`,
                    pointerEvents: "none",
                }}>
                    {/* Заголовок окрашен в цвет типа лицензии */}
                    <div style={{
                        fontSize: 12, fontWeight: 700,
                        color: typeColor.text,
                        marginBottom: 10,
                        borderBottom: `1px solid ${typeColor.border}`,
                        paddingBottom: 8,
                        display: "flex", alignItems: "center", gap: 8,
                    }}>
            <span style={{
                fontSize: 10, padding: "2px 8px", borderRadius: 5,
                background: typeColor.bg,
                border: `1px solid ${typeColor.border}`,
                color: typeColor.text,
                fontFamily: "monospace", letterSpacing: 1,
            }}>
              {license.licenseType?.toUpperCase()}
            </span>
                        License Details
                    </div>

                    <TooltipRow label="ID" value={license.licenseId} mono/>
                    <TooltipRow label="Product" value={license.productName}/>
                    {license.organizationId &&
                        <TooltipRow label="Org ID" value={license.organizationId} mono/>}
                    {license.organizationName &&
                        <TooltipRow label="Org" value={license.organizationName}/>}
                    {license.description &&
                        <TooltipRow label="Desc" value={license.description}/>}
                    {license.comment &&
                        <TooltipRow label="Comment" value={license.comment}/>}
                    {license.contactName &&
                        <TooltipRow label="Contact" value={license.contactName}/>}
                    {license.contactEmail &&
                        <TooltipRow label="Email" value={license.contactEmail}/>}
                    {license.contactPhone &&
                        <TooltipRow label="Phone" value={license.contactPhone}/>}
                </div>
            )}
        </div>
    );
}

function TooltipRow({
                        label, value, mono = false,
                    }: {
    label: string;
    value?: string;
    mono?: boolean;
}) {
    if (!value) return null;
    return (
        <div style={{
            display: "flex", gap: 8, marginBottom: 5,
            fontSize: 11, alignItems: "flex-start",
        }}>
      <span style={{
          color: "#555577", fontFamily: "monospace",
          minWidth: 58, flexShrink: 0,
      }}>
        {label}:
      </span>
            <span style={{
                color: "#c8c8e0",
                fontFamily: mono ? "monospace" : "inherit",
                wordBreak: "break-all",
            }}>
        {value}
      </span>
        </div>
    );
}