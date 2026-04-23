@echo off
echo ========================================
echo QuantityMeasurementApp - Supabase Setup
echo ========================================

echo.
echo 1. Checking environment setup...

if not exist ".env" (
    echo Creating .env file from template...
    copy .env.example .env
    echo.
    echo ⚠️  IMPORTANT: Edit .env file and add your Supabase password!
    echo    - Open .env file
    echo    - Replace YOUR_SUPABASE_PASSWORD_HERE with your actual password
    echo    - Save the file
    echo.
    pause
)

echo.
echo 2. Checking Docker status...
docker ps >nul 2>&1
if %ERRORLEVEL% neq 0 (
    echo ❌ Docker is not running. Please start Docker Desktop first.
    pause
    exit /b 1
)
echo ✅ Docker is running

echo.
echo 3. Building and starting application...
docker-compose up --build -d

echo.
echo 4. Checking application status...
timeout /t 30 /nobreak >nul
docker-compose ps

echo.
echo 5. Showing application logs...
docker-compose logs app

echo.
echo ========================================
echo 🚀 Application Status
echo ========================================
echo.
echo Backend API: http://localhost:8080
echo Health Check: http://localhost:8080/actuator/health
echo.
echo API Endpoints:
echo - POST /api/auth/register
echo - POST /api/auth/login  
echo - POST /api/quantity/compare
echo - POST /api/quantity/convert
echo - POST /api/quantity/add
echo - POST /api/quantity/subtract
echo - POST /api/quantity/divide
echo - GET  /api/quantity/getHistory
echo.
echo To stop: docker-compose down
echo To view logs: docker-compose logs -f app
echo ========================================