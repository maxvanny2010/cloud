import {MESSAGES} from "@/app/constants";

export default function EurekaRegistryInfo({count}: { count: number }) {
    return (
        <div>
            <div style={{fontSize: 13, fontWeight: 600, color: "#c0b8ff"}}>
                {MESSAGES.EUREKA_REGISTRY_TITLE}
            </div>
            <div style={{fontSize: 11, color: "#666688", marginTop: 2, fontFamily: "monospace"}}>
                {count} service(s) registered
            </div>
        </div>
    );
}
