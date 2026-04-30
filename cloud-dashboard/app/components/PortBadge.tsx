import {PORT_URLS, SERVICES} from "@/app/constants";
import NavLink from "@/app/components/NavLink";

export default function PortBadge({service}: { service: typeof SERVICES[number] }) {
    const url = PORT_URLS[service.port] ?? `http://localhost:${service.port}`;
    return (
        <NavLink
            href={url}
            label={`:${service.port}`}
            title={`${service.name} · ${service.description}`}
        />
    );
}