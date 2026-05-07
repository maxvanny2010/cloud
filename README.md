# Cloud Microservices Platform

A production-grade microservices platform built with Spring Boot 3.4, deployed on a Hetzner VPS with full CI/CD
automation via GitHub Actions.

[![Live](https://img.shields.io/badge/LIVE-maxvanny.dev-6496ff?style=for-the-badge&labelColor=00a050)](https://maxvanny.dev)
[![Jaeger](https://img.shields.io/badge/Tracing-Jaeger-ff6600?style=for-the-badge&labelColor=333)](https://maxvanny.dev/jaeger/)
[![Grafana](https://img.shields.io/badge/Metrics-Grafana-F46800?style=for-the-badge&labelColor=333)](https://maxvanny.dev/grafana/)

---

## Live Demo

| Service   | URL                           | Credentials       |
|-----------|-------------------------------|-------------------|
| Dashboard | https://maxvanny.dev          | —                 |
| Grafana   | https://maxvanny.dev/grafana/ | `admin` / `admin` |
| Jaeger UI | https://maxvanny.dev/jaeger/  | —                 |
| Eureka UI | https://maxvanny.dev/eureka/  | —                 |

---

## Quick Start

No build required. All Docker images are public and hosted on GitHub Container Registry. The only requirement is Docker
with Docker Compose installed.

```bash
git clone https://github.com/maxvanny2010/cloud.git
cd cloud/docker
docker compose up
```

Docker will pull all images automatically and start every service in the correct order. The startup takes approximately
60 seconds because services wait for each other to become healthy before starting.

Once everything is up, open [http://localhost:3000](http://localhost:3000) in your browser.

To run in the background:

```bash
docker compose up -d
```

To stop all services:

```bash
docker compose down
```

To stop and delete all data including the database:

```bash
docker compose down -v
```

### What runs locally

| Service          | URL                                      |
|------------------|------------------------------------------|
| Dashboard        | http://localhost:3000                    |
| Gateway          | http://localhost:8072                    |
| Eureka UI        | http://localhost:8070                    |
| Jaeger UI        | http://localhost:16686                   |
| Grafana          | http://localhost:3001/grafana/           |
| Prometheus       | http://localhost:9090                    |
| Config Server    | http://localhost:8071                    |
| License API      | http://localhost:8072/api/licenses/      |
| Organization API | http://localhost:8072/api/organizations/ |

---

## Overview

This project implements a distributed microservices architecture with centralized configuration, service discovery, API
gateway routing, real-time Next.js dashboard, distributed tracing via OpenTelemetry and Jaeger, and metrics monitoring
via Prometheus and Grafana. All services are containerized, built automatically on every push to master, and deployed
to a cloud server without any manual intervention.

---

## Architecture

```
Browser
   |
   v
nginx (HTTPS / maxvanny.dev)
   |
   +-- /           -> cloud-dashboard  :3000  (Next.js 15, SSR)
   +-- /api/       -> gateway          :8072  (Spring Cloud Gateway)
   +-- /jaeger/    -> Jaeger UI        :16686 (Distributed Tracing)
   +-- /eureka/    -> Eureka UI        :8070  (Service Registry)
   +-- /grafana/   -> Grafana          :3001  (Metrics Dashboards)
                          |
                   +-- lb://LICENSE       -> licensing-service    :8080
                   +-- lb://ORGANIZATION  -> organization-service :8080
                   |
                   +-- /api/health/eureka    -> eurekaserver  :8070
                   +-- /api/health/config    -> configserver  :8071

Service Discovery: Eureka
Configuration:     Config Server (native classpath + optional Git)
Database:          PostgreSQL 16
Migrations:        Liquibase
Tracing:           OpenTelemetry + Jaeger
Metrics:           Prometheus + Grafana
```

---

## Services

### configserver

Spring Cloud Config Server. Serves externalized configuration to all services at startup. Supports native classpath
profiles and optional Git backend. Runs on port 8071.

Configuration is profile-aware: each service loads a base properties file and a profile-specific override (
`license.properties` + `license-prod.properties`).

### eurekaserver

Netflix Eureka service registry. All microservices register themselves on startup. The gateway uses Eureka for
load-balanced routing via `lb://` URIs. Runs on port 8070.

### gateway

Spring Cloud Gateway. Single entry point for all API traffic. Routes requests to downstream services by path prefix,
rewrites paths, and exposes health endpoints for each registered service. Runs on port 8072.

Routes:

```
/api/licenses/**         -> LICENSE service
/api/organizations/**    -> ORGANIZATION service
/api/health/license      -> LICENSE /actuator/health
/api/health/organization -> ORGANIZATION /actuator/health
/api/health/eureka       -> Eureka /actuator/health
/api/health/config       -> Config Server /actuator/health
```

### licensing-service

Manages software licenses per organization. Each license belongs to an organization identified by UUID. Exposes a REST
API with full CRUD. Uses Liquibase for schema management against PostgreSQL. Runs on port 8080.

Resilience patterns: CircuitBreaker, RateLimiter, Retry, Bulkhead via Resilience4j.

### organization-service

Manages organizations with contact information. Each organization can have multiple licenses. Exposes a REST API. Uses
Liquibase for schema management. Runs on port 8080 internally, mapped to 8081 externally.

### cloud-dashboard

Next.js 15 frontend with server-side rendering. Displays live health status of all services, lists organizations and
licenses from the database, shows the Eureka service registry, and links to Jaeger for distributed tracing. All data
fetching uses `"use server"` actions with `cache: "no-store"` for real-time updates. Runs on port 3000.

### jaeger

Jaeger all-in-one distributed tracing backend. Receives traces from all services via OpenTelemetry OTLP protocol and
provides a UI for visualizing request flows across microservices. Runs on port 16686 (UI) and 4318 (OTLP HTTP).

### prometheus

Prometheus metrics collection server. Scrapes `/actuator/prometheus` endpoints from all Spring Boot services every 5
seconds. Stores time-series data with a 7-day retention window. Runs on port 9090.

### grafana

Grafana metrics visualization. Connected to Prometheus as a data source. Ships with the Spring Boot 3.x Statistics
dashboard (ID 19004) showing JVM memory, CPU usage, HTTP request rates, HikariCP connection pool, and GC statistics.
Runs on port 3001, accessible at `/grafana/` via nginx subpath routing.

---

## Tech Stack

| Layer              | Technology                                  |
|--------------------|---------------------------------------------|
| Backend            | Java 21, Spring Boot 3.4, Spring Cloud 2024 |
| Service Discovery  | Netflix Eureka                              |
| API Gateway        | Spring Cloud Gateway                        |
| Configuration      | Spring Cloud Config Server                  |
| Database           | PostgreSQL 16                               |
| Migrations         | Liquibase                                   |
| Resilience         | Resilience4j (CircuitBreaker, Retry, etc.)  |
| Tracing            | OpenTelemetry + Jaeger                      |
| Metrics Collection | Prometheus                                  |
| Metrics Dashboards | Grafana                                     |
| Frontend           | Next.js 15, TypeScript, React 19            |
| Containerization   | Docker, Docker Compose                      |
| CI/CD              | GitHub Actions                              |
| Container Registry | GitHub Container Registry (GHCR)            |
| Server             | Hetzner VPS, Ubuntu 24.04                   |
| Reverse Proxy      | nginx                                       |
| SSL                | Let's Encrypt (certbot)                     |

---

## Distributed Tracing

All services are instrumented with OpenTelemetry via Micrometer Tracing. Every request that enters through the gateway
receives a trace ID that is propagated automatically to all downstream services using the W3C `traceparent` header.

Traces are exported via OTLP HTTP protocol to Jaeger where they can be visualized as waterfall diagrams showing the full
request path across services with timing for each step.

Jaeger UI is available at [https://maxvanny.dev/jaeger/](https://maxvanny.dev/jaeger/).

Sampling is set to 100% (`probability: 1.0`) for full visibility. In production with high traffic this should be reduced
to 10% or lower.

---

## Metrics & Monitoring

All services expose metrics via Spring Boot Actuator in Prometheus format at `/actuator/prometheus`. Prometheus scrapes
all services on a 5-second interval. Grafana connects to Prometheus as a data source and provides dashboards for
visualizing JVM memory, HTTP request rates, circuit breaker states, and HikariCP connection pool usage.

Grafana is available at [https://maxvanny.dev/grafana/](https://maxvanny.dev/grafana/).

| Credential | Value   |
|------------|---------|
| Username   | `admin` |
| Password   | `admin` |

Prometheus scrape configuration (`docker/prometheus.yaml`):

```yaml
scrape_configs:
  - job_name: gateway
    metrics_path: /actuator/prometheus
    static_configs:
      - targets: [ 'gateway:8072' ]

  - job_name: license
    metrics_path: /actuator/prometheus
    static_configs:
      - targets: [ 'license:8080' ]

  - job_name: organization
    metrics_path: /actuator/prometheus
    static_configs:
      - targets: [ 'organization:8080' ]
```

Dashboard in use: **Spring Boot 3.x Statistics** (Grafana ID `19004`). To import manually:
Dashboards → New → Import → enter `19004` → select Prometheus datasource → Import.

---

## Testing

Licensing service has automated tests that run as part of the CI/CD pipeline before every build.

### Test structure

| Class                   | Type          | Description                      |
|-------------------------|---------------|----------------------------------|
| `LicenseControllerTest` | `@WebMvcTest` | REST layer tests with MockMvc    |
| `LicenseServiceTest`    | Unit tests    | Service logic tests with Mockito |

### What is tested

`LicenseControllerTest` covers all CRUD endpoints:

- `GET /v1/organization/{orgId}/license/{licId}` — returns 200 with license JSON
- `GET /v1/organization/{orgId}/license/` — returns list of licenses
- `POST /v1/organization/{orgId}/license` — creates license, returns 200
- `PUT /v1/organization/{orgId}/license` — updates license, returns 200
- `DELETE /v1/organization/{orgId}/license/{licId}` — returns 200 with message
- Invalid UUID format — returns 400 via `GlobalExceptionHandler`

`LicenseServiceTest` covers service logic for all client types: feign, rest, discovery, and default.

### Run tests locally

```bash
mvn -f licensing-service/pom.xml test
```

Test reports are saved to `licensing-service/target/surefire-reports/`. In CI, reports are uploaded as GitHub Actions
artifacts and kept for 7 days.

---

## CI/CD Pipeline

Every push to the `master` branch triggers the following automated sequence:

1. GitHub Actions detects which services changed using path filters.
2. Tests run for changed services. Build is blocked if tests fail.
3. Each changed service is built with Maven or npm.
4. A Docker image is built and pushed to `ghcr.io/maxvanny2010/<service>:latest`.
5. After all builds succeed, a deploy job SSHs into the Hetzner VPS.
6. The server runs `docker compose pull` to fetch new images and `docker compose up -d` to restart updated containers.

Pushes to `develop` trigger builds only for changed services, without deployment. Pull requests trigger builds only,
without pushing images.

```
push to master
      |
      v
changes detection (dorny/paths-filter)
      |
      +-- test-licensing (mvn test)
      |         |
      |         v (only if tests pass)
      +-- build-licensing
      +-- build-configserver
      +-- build-eureka
      +-- build-organization
      +-- build-gateway
      +-- build-dashboard
      |
      v
deploy (appleboy/ssh-action)
      |
      v
docker compose pull && docker compose up -d
```

---

## Configuration

Service configuration is managed centrally by the Config Server. Properties files are located in
`configserver/src/main/resources/config/`.

| File                           | Purpose                                                       |
|--------------------------------|---------------------------------------------------------------|
| `license.properties`           | Base config for licensing-service (JPA, Eureka, resilience4j) |
| `license-dev.properties`       | Dev datasource (local PostgreSQL)                             |
| `license-prod.properties`      | Prod datasource (Docker PostgreSQL container)                 |
| `organization.properties`      | Base config for organization-service                          |
| `organization-dev.properties`  | Dev datasource                                                |
| `organization-prod.properties` | Prod datasource                                               |

---

## Environment Variables

Key environment variables set in `docker-compose.yaml`:

| Variable                 | Value                      | Description                                |
|--------------------------|----------------------------|--------------------------------------------|
| `SPRING_PROFILES_ACTIVE` | `prod`                     | Activates prod properties in Config Server |
| `CONFIGSERVER_URI`       | `http://configserver:8071` | Config Server location                     |
| `ENCRYPT_KEY`            | `secretkey`                | Spring Cloud encryption key                |
| `POSTGRES_DB`            | `cloude`                   | PostgreSQL database name                   |
| `GATEWAY_URL`            | `http://gateway:8072`      | Gateway URL for dashboard SSR              |

---

## Health Endpoints

All services expose Spring Boot Actuator health endpoints.

| Service       | URL                                          |
|---------------|----------------------------------------------|
| License       | https://maxvanny.dev/api/health/license      |
| Organization  | https://maxvanny.dev/api/health/organization |
| Eureka        | https://maxvanny.dev/api/health/eureka       |
| Config Server | https://maxvanny.dev/api/health/config       |

---

## GitHub Secrets Required

To set up your own deployment, add the following secrets to a GitHub Environment named `hetzner`:

| Secret        | Description                       |
|---------------|-----------------------------------|
| `VPS_IP`      | IP address of the target server   |
| `VPS_SSH_KEY` | Private SSH key for server access |

---

## Repository Structure

```
cloud/
  configserver/          Spring Cloud Config Server
  eurekaserver/          Netflix Eureka Server
  gateway/               Spring Cloud Gateway
  licensing-service/     License management microservice
  organization-service/  Organization management microservice
  cloud-dashboard/       Next.js real-time dashboard
  docker/                docker-compose.yaml, prometheus.yaml, init.sql, data.sql
  .github/workflows/     CI/CD pipeline (ci.yml)
  pom.xml                Root Maven POM (monorepo)
```

## Security

The server is protected by a firewall (ufw) that allows only the following ports:

| Port | Protocol | Description               |
|------|----------|---------------------------|
| 22   | TCP      | SSH access                |
| 80   | TCP      | HTTP (redirects to HTTPS) |
| 443  | TCP      | HTTPS (Nginx)             |

All other ports are closed. Internal services communicate through the Docker bridge network
and are accessible only via Nginx reverse proxy.

PostgreSQL runs without an exposed port — it is accessible only
within the Docker network by application services.