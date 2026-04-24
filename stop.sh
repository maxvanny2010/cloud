#!/bin/bash

echo "========================================"
echo "  Cloud Microservices - Stopping..."
echo "========================================"

cd docker
docker-compose down

echo ""
echo "  All services stopped."
echo "  To remove database data run: docker-compose down -v"
echo "========================================"