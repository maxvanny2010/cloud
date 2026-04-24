@echo off
echo ========================================
echo   Cloud Microservices - Build and Run
echo ========================================

echo.
echo [1/9] Building JAR files with Maven...
call mvn clean package -DskipTests
if %errorlevel% neq 0 (
    echo ERROR: Maven build failed!
    pause & exit /b 1
)

echo.
echo [2/9] Stopping old containers...
cd docker
docker-compose down -v

echo.
echo [3/9] Building configserver image...
docker build -t configserver:latest ../configserver

echo.
echo [4/9] Building eureka image...
docker build -t eureka:latest ../eurekaserver

echo.
echo [5/9] Building license image...
docker build -t license:latest ../licensing-service

echo.
echo [6/9] Building organization image...
docker build -t organization:latest ../organization-service

echo.
echo [7/9] Building gateway image...
docker build -t gateway:latest ../gateway

echo.
echo [8/9] Building dashboard image...
docker build -t dashboard:latest ../cloud-dashboard

echo.
echo [9/9] Starting all services...
docker-compose up -d --build

echo.
echo Waiting for services to start...
timeout /t 20 >nul

echo.
echo ========================================
echo   Health check
echo ========================================

curl http://localhost:8072/api/health/license
echo.
curl http://localhost:8072/api/health/organization
echo.

echo.
echo ========================================
echo   All services started!
echo ========================================
echo.
echo   Gateway:              http://localhost:8072
echo   Eureka Dashboard:     http://localhost:8070
echo   Dashboard:            http://localhost:3000
echo.
echo   API routes via Gateway:
echo   Licenses:             http://localhost:8072/api/licenses/
echo   Organizations:        http://localhost:8072/api/organizations/
echo.
pause