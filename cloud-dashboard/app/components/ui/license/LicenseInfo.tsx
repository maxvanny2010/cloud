import type {License} from "@/app/types";
import LicenseTypeBadge from "./LicenseTypeBadge";

export default function LicenseInfo({license}: { license: License }) {
    return (
        <div style={{flex: 1}}>
            <div style={{display: "flex", alignItems: "center", gap: 10, marginBottom: 8}}>
                <LicenseTypeBadge licenseType={license.licenseType}/>
                <span style={{fontSize: 15, fontWeight: 600, color: "#e8e8f0"}}>
          {license.productName}
        </span>
            </div>
            <div style={{fontSize: 11, color: "#555577", fontFamily: "monospace"}}>
                ID: {license.licenseId?.substring(0, 18)}...
            </div>
            {license.description && (
                <div style={{fontSize: 12, color: "#888", marginTop: 4}}>{license.description}</div>
            )}
        </div>
    );
}
