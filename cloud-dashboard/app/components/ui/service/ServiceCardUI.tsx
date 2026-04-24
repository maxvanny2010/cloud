import StatusBadge from "../../StatusBadge";
import ServiceHeader from "./ServiceHeader";
import ComponentsList from "./ComponentsList";
import DownBanner from "./DownBanner";
import type { HealthStatus, ServiceConfig, ServiceStatus } from "@/app/types";

interface Props {
  service: ServiceConfig;
  status: ServiceStatus;
  data: HealthStatus | null;
}

export default function ServiceCardUI({ service, status, data }: Props) {
  return (
    <div className="glass-card" style={{ padding: 24 }}>
      <div style={{
        display: "flex", justifyContent: "space-between",
        alignItems: "flex-start", marginBottom: 18,
      }}>
        <ServiceHeader service={service} />
        <StatusBadge status={status} />
      </div>
      {data?.components && <ComponentsList components={data.components} />}
      {status === "down" && <DownBanner />}
    </div>
  );
}
