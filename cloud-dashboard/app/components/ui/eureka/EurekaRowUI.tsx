import ServiceIcon from "./ServiceIcon";
import ServiceInfo from "./ServiceInfo";
import ServiceBadge from "./ServiceBadge";

interface Props {
    name: string;
    count: number;
    port?: string;
}

export default function EurekaRowUI({name, count, port}: Props) {
    return (
        <div className="glass-card" style={{padding: "18px 24px"}}>
            <div style={{display: "flex", justifyContent: "space-between", alignItems: "center"}}>
                <div style={{display: "flex", alignItems: "center", gap: 14}}>
                    <ServiceIcon name={name}/>
                    <ServiceInfo name={name} count={count}/>
                </div>
                <ServiceBadge port={port}/>
            </div>
        </div>
    );
}
