import {getEurekaApps} from "../actions/eureka";
import {MESSAGES, SERVICES} from "../constants";
import EurekaHeaderUI from "./ui/eureka/EurekaHeaderUI";
import EurekaRowUI from "./ui/eureka/EurekaRowUI";
import EmptyStateUI from "./ui/EmptyStateUI";

const PORT_MAP: Record<string, string> = Object.fromEntries(
    SERVICES.map((s) => [s.name.toUpperCase(), s.port])
);

export default async function EurekaSection() {
    const apps = await getEurekaApps();
    const entries = Object.entries(apps);

    if (entries.length === 0) {
        return <EmptyStateUI message={MESSAGES.NO_EUREKA_SERVICES}/>;
    }

    return (
        <div style={{display: "grid", gap: 14}}>
            <EurekaHeaderUI count={entries.length}/>
            {entries.map(([name, count]) => (
                <EurekaRowUI key={name} name={name} count={count} port={PORT_MAP[name]}/>
            ))}
        </div>
    );
}
