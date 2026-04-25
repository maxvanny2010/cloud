import type {License} from "@/app/types";

export default function LicenseMeta({license}: { license: License }) {
    return (
        <div style={{textAlign: "right", marginLeft: 20}}>
            {license.organizationName && (
                <div style={{
                    fontSize: 12, color: "#aaa", background: "rgba(255,255,255,0.04)",
                    padding: "4px 10px", borderRadius: 8,
                    border: "1px solid rgba(255,255,255,0.06)", marginBottom: 6,
                }}>
                    {license.organizationName}
                </div>
            )}
            {license.comment && (
                <div style={{fontSize: 11, color: "#555577", fontFamily: "monospace"}}>
                    {license.comment}
                </div>
            )}
        </div>
    );
}
