# Cloud Microservices Platform

A production-grade microservices platform built with Spring Boot 3.4, deployed on a Hetzner VPS with full CI/CD automation via GitHub Actions.

[![Live](https://img.shields.io/badge/LIVE-www.maxvanny.dev-6496ff?style=for-the-badge&labelColor=00a050)](https://maxvanny.dev)

---

## Quick Start

No build required. All Docker images are public and hosted on GitHub Container Registry. The only requirement is Docker with Docker Compose installed.

```bash
git clone https://github.com/maxvanny2010/cloud.git
cd cloud/docker
docker compose up
```

Docker will pull all images automatically and start every service in the correct order. The startup takes approximately 60 seconds because services wait for each other to become healthy before starting.

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
| Config Server    | http://localhost:8071                    |
| License API      | http://localhost:8072/api/licenses/      |
| Organization API | http://localhost:8072/api/organizations/ |

---

## Overview

This project implements a distributed microservices architecture with centralized configuration, service discovery, API gateway routing, and a real-time Next.js dashboard. All services are containerized, built automatically on every push to master, and deployed to a cloud server without any manual intervention.

---

## Architecture

```
Browser
   |
   v
nginx (HTTPS / maxvanny.dev)
   |
   +-- :3000  cloud-dashboard  (Next.js 15, SSR)
   |
   +-- :8072  gateway          (Spring Cloud Gateway)
                  |
                  +-- lb://LICENSE       -> licensing-service  :8080
                  +-- lb://ORGANIZATION  -> organization-service :8080
                  |
                  +-- /api/health/eureka    -> eurekaserver  :8070
                  +-- /api/health/config    -> configserver  :8071

Service Discovery: Eureka
Configuration:     Config Server (native classpath + optional Git)
Database:          PostgreSQL 16
Migrations:        Liquibase
```

---

## Services

### configserver

Spring Cloud Config Server. Serves externalized configuration to all services at startup. Supports native classpath profiles and optional Git backend. Runs on port 8071.

Configuration is profile-aware: each service loads a base properties file and a profile-specific override (`license.properties` + `license-prod.properties`).

### eurekaserver

Netflix Eureka service registry. All microservices register themselves on startup. The gateway uses Eureka for load-balanced routing via `lb://` URIs. Runs on port 8070.

### gateway

Spring Cloud Gateway. Single entry point for all API traffic. Routes requests to downstream services by path prefix, rewrites paths, and exposes health endpoints for each registered service. Runs on port 8072.

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

Manages software licenses per organization. Each license belongs to an organization identified by UUID. Exposes a REST API with full CRUD. Uses Liquibase for schema management against PostgreSQL. Runs on port 8080.

### organization-service

Manages organizations with contact information. Each organization can have multiple licenses. Exposes a REST API. Uses Liquibase for schema management. Runs on port 8080 internally, mapped to 8081 externally.

### cloud-dashboard

Next.js 15 frontend with server-side rendering. Displays live health status of all services, lists organizations and licenses from the database, and shows the Eureka service registry. All data fetching uses `"use server"` actions with `cache: "no-store"` for real-time updates. Runs on port 3000.

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
| Frontend           | Next.js 15, TypeScript, React 19            |
| Containerization   | Docker, Docker Compose                      |
| CI/CD              | GitHub Actions                              |
| Container Registry | GitHub Container Registry (GHCR)            |
| Server             | Hetzner VPS, Ubuntu 24.04                   |
| Reverse Proxy      | nginx                                       |
| SSL                | Let's Encrypt (certbot)                     |

---

## CI/CD Pipeline

Every push to the `master` branch triggers the following automated sequence:

1. GitHub Actions detects which services changed using path filters.
2. Each changed service is built with Maven or npm.
3. A Docker image is built and pushed to `ghcr.io/maxvanny2010/<service>:latest`.
4. After all builds succeed, a deploy job SSHs into the Hetzner VPS.
5. The server runs `docker compose pull` to fetch new images and `docker compose up -d` to restart updated containers.

Pushes to `develop` trigger builds only, without deployment. Pull requests trigger builds only, without pushing images.

```
push to master
      |
      v
changes detection (dorny/paths-filter)
      |
      +-- build-configserver
      +-- build-eureka
      +-- build-licensing
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

Service configuration is managed centrally by the Config Server. Properties files are located in `configserver/src/main/resources/config/`.

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
  docker/                docker-compose.yaml, init.sql, data.sql
  .github/workflows/     CI/CD pipeline (ci.yml)
  pom.xml                Root Maven POM (monorepo)
```