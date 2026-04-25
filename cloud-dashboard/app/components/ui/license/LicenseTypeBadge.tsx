import {LICENSE_TYPE_COLORS} from "@/app/constants";

export default function LicenseTypeBadge({licenseType}: { licenseType: string }) {
    const color = LICENSE_TYPE_COLORS[licenseType] ?? LICENSE_TYPE_COLORS.default;
    return (
        <span style={{
            fontSize: 10, fontWeight: 700, letterSpacing: 1,
            padding: "3px 10px", borderRadius: 6,
            background: color.bg, border: `1px solid ${color.border}`,
            color: color.text, fontFamily: "monospace",
        }}>
      {licenseType?.toUpperCase()}
    </span>
    );
}
