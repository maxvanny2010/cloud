export interface Organization {
  id: string;
  name: string;
  contactName: string;
  contactEmail: string;
  contactPhone: string;
}

export interface License {
  licenseId: string;
  organizationId: string;
  description: string;
  productName: string;
  licenseType: string;
  comment: string;
  organizationName?: string;
  contactName?: string;
  contactEmail?: string;
  contactPhone?: string;
}

export interface HealthStatus {
  status: "UP" | "DOWN" | "UNKNOWN";
  components?: Record<string, { status: string; details?: Record<string, unknown> }>;
}

export type ServiceStatus = "up" | "down" | "loading";

export interface ServiceConfig {
  name: string;
  url: string;
  port: string;
  icon: string;
  description: string;
}

export type HealthAction =
  | { type: "SUCCESS"; payload: HealthStatus }
  | { type: "ERROR" };
