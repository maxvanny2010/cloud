@echo off
echo ========================================
echo   PROD MODE - Cloud Microservices
echo ========================================

echo.
echo [1/8] Building JAR files...
call mvn clean package -DskipTests
if %errorlevel% neq 0 (
    echo ERROR: Maven build failed!
    pause & exit /b 1
)

echo.
echo [2/8] Stopping old containers...
cd docker
docker-compose down -v

echo.
echo [3/8] Building images...
docker build -t configserver:latest ../configserver
docker build -t eureka:latest ../eurekaserver
docker build -t license:latest ../licensing-service
docker build -t organization:latest ../organization-service
docker build -t gateway:latest ../gateway
docker build -t dashboard:latest ../cloud-dashboard

echo.
echo [4/8] Starting services...
docker-compose up -d --build

echo.
echo [5/8] Waiting for Gateway...

:waitloop
curl -s http://localhost:8072/actuator/health >nul 2>&1
if %errorlevel% neq 0 (
    echo   Gateway not ready yet...
    timeout /t 3 >nul
    goto waitloop
)

echo   Gateway is UP

echo.
echo [6/8] Checking services...

echo License:
curl http://localhost:8072/api/health/license

echo.
echo Organization:
curl http://localhost:8072/api/health/organization

echo.
echo [7/8] Checking Eureka...
curl http://localhost:8070 | find "Instances currently registered" >nul
if %errorlevel%==0 (
    echo   Eureka OK
) else (
    echo   Eureka problem
)

echo.
echo [8/8] Summary
echo ========================================
echo Gateway:          http://localhost:8072
echo Eureka:           http://localhost:8070
echo Dashboard:        http://localhost:3000
echo.
echo API:
echo   Licenses:       http://localhost:8072/api/licenses/
echo   Organizations:  http://localhost:8072/api/organizations/
echo ========================================

pause