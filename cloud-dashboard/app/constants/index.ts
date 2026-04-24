import type { ServiceConfig } from "../types";

const GATEWAY = process.env.GATEWAY_URL ?? "http://gateway:8072";

export const API = {
  // Серверные запросы (server actions) — напрямую через Docker сеть
  licenses:      `${GATEWAY}/api/licenses`,
  organizations: `${GATEWAY}/api/organizations`,
  health: {
    license:      `${GATEWAY}/api/health/license`,
    organization: `${GATEWAY}/api/health/organization`,
    eureka:       `${GATEWAY}/api/health/eureka`,
    config:       `${GATEWAY}/api/health/config`,
  },
  // Браузерные запросы — через Next.js rewrite proxy
  // /api/* → gateway:8072/api/*
  browser: {
    health: {
      license:      "/api/health/license",
      organization: "/api/health/organization",
      eureka:       "/api/health/eureka",
      config:       "/api/health/config",
    },
  },
} as const;

export const SERVICES: ServiceConfig[] = [
  { name: "License",       url: API.browser.health.license,      port: "8080", icon: "license", description: "Licenses"      },
  { name: "Organization",  url: API.browser.health.organization,  port: "8081", icon: "org",     description: "Organisations" },
  { name: "Eureka",        url: API.browser.health.eureka,        port: "8070", icon: "network",  description: "Discovery"    },
  { name: "Config Server", url: API.browser.health.config,        port: "8071", icon: "config",   description: "Configs"      },
];

export const SERVICE_STATUS = {
  UP:      "up",
  DOWN:    "down",
  LOADING: "loading",
} as const;

export const STATUS_CONFIG = {
  up:      { color: "#00ff88", label: "UP",   glow: "0 0 12px #00ff8866" },
  down:    { color: "#ff4466", label: "DOWN", glow: "0 0 12px #ff446666" },
  loading: { color: "#ffaa00", label: "...",  glow: "0 0 12px #ffaa0066" },
} as const;

export const HEALTH_ACTION = {
  SUCCESS: "SUCCESS",
  ERROR:   "ERROR",
} as const;

export const POLL_INTERVAL = {
  SERVICES: 8_000,
  DEFAULT:  10_000,
} as const;

export const ORG_IDS = [
  "e6a625cc-718b-48c2-ac76-1dfdff9a531e",
  "d898a142-de44-466c-8c88-9ceb2c2429d3",
  "e839ee96-28de-4f67-bb79-870ca89743a0",
] as const;

export const LICENSE_TYPE_COLORS: Record<string, { bg: string; border: string; text: string }> = {
  complete: { bg: "rgba(0,200,100,0.08)",  border: "rgba(0,200,100,0.2)",  text: "#00c864" },
  personal: { bg: "rgba(100,150,255,0.08)",border: "rgba(100,150,255,0.2)",text: "#6496ff" },
  default:  { bg: "rgba(200,150,0,0.08)",  border: "rgba(200,150,0,0.2)",  text: "#c89600" },
};

export const EUREKA_SERVICE_ICONS: Record<string, string> = {
  LICENSE:      "license",
  ORGANIZATION: "org",
  GATEWAY:      "network",
  CONFIG:       "config",
};

export const ORG_CONTACT_FIELDS = [
  { label: "Contact", key: "contactName",  icon: "user"  },
  { label: "Email",   key: "contactEmail", icon: "mail"  },
  { label: "Phone",   key: "contactPhone", icon: "phone" },
] as const;

export const MESSAGES = {
  NO_ORGANIZATIONS:      "No organizations found",
  NO_LICENSES:           "No licenses found",
  NO_EUREKA_SERVICES:    "No services registered in Eureka",
  SERVICE_UNAVAILABLE:   "Service unavailable",
  EUREKA_REGISTRY_TITLE: "Eureka Service Registry",
  SPRING_BOOT_LABEL:     "Spring Boot",
} as const;