#!/bin/bash

set -e

echo "========================================"
echo "  PROD MODE - Cloud Microservices"
echo "========================================"

echo
echo "[1/8] Building JARs..."
mvn clean package -DskipTests

echo
echo "[2/8] Stopping old containers..."
cd docker
docker-compose down -v

echo
echo "[3/8] Building images..."
docker build -t configserver:latest ../configserver
docker build -t eureka:latest ../eurekaserver
docker build -t license:latest ../licensing-service
docker build -t organization:latest ../organization-service
docker build -t gateway:latest ../gateway
docker build -t dashboard:latest ../cloud-dashboard

echo
echo "[4/8] Starting services (detached)..."
docker-compose up -d --build

echo
echo "[5/8] Waiting for Gateway..."
until curl -s http://localhost:8072/actuator/health > /dev/null; do
  echo "  ⏳ Gateway not ready yet..."
  sleep 3
done

echo "  ✅ Gateway is UP"

echo
echo "[6/8] Checking services health..."

echo -n "  License: "
curl -s http://localhost:8072/api/health/license || echo "FAILED"
echo

echo -n "  Organization: "
curl -s http://localhost:8072/api/health/organization || echo "FAILED"
echo

echo
echo "[7/8] Checking Eureka..."
curl -s http://localhost:8070 | grep -q "Instances currently registered" \
  && echo "  ✅ Eureka OK" \
  || echo "  ❌ Eureka problem"

echo
echo "[8/8] Summary"
echo "========================================"
echo "Gateway:          http://localhost:8072"
echo "Eureka:           http://localhost:8070"
echo "Dashboard:        http://localhost:3000"
echo
echo "API:"
echo "  Licenses:       http://localhost:8072/api/licenses/"
echo "  Organizations:  http://localhost:8072/api/organizations/"
echo "========================================"