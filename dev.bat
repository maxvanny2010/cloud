@echo off
echo ========================================
echo   DEV MODE - Cloud Microservices
echo ========================================

echo.
echo [1/6] Building JAR files...
call mvn clean package -DskipTests
if %errorlevel% neq 0 (
    echo ERROR: Maven build failed!
    pause & exit /b 1
)

echo.
echo [2/6] Stopping old containers...
cd docker
docker-compose down -v

echo.
echo [3/6] Building images...
docker build -t configserver:latest ../configserver
docker build -t eureka:latest ../eurekaserver
docker build -t license:latest ../licensing-service
docker build -t organization:latest ../organization-service
docker build -t gateway:latest ../gateway
docker build -t dashboard:latest ../cloud-dashboard

echo.
echo [4/6] Starting services (attached mode)...
docker-compose up --build

pause