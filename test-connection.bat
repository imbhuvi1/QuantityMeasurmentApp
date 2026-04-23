@echo off
echo ========================================
echo Supabase PostgreSQL Connection Test
echo ========================================

echo.
echo Connection Details:
echo Host: aws-1-ap-southeast-1.pooler.supabase.com
echo Port: 5432
echo Database: postgres
echo Username: postgres.jwmezlerhbqelxdfnxfb
echo.

if not exist ".env" (
    echo ❌ .env file not found!
    echo Please create .env file with your DB_PASSWORD
    echo.
    echo Example:
    echo DB_PASSWORD=your_actual_supabase_password
    echo.
    pause
    exit /b 1
)

echo ✅ Configuration files ready
echo.
echo Testing connection with Docker...

docker run --rm --env-file .env postgres:15 psql "postgresql://postgres.jwmezlerhbqelxdfnxfb:${DB_PASSWORD}@aws-1-ap-southeast-1.pooler.supabase.com:5432/postgres" -c "SELECT version();"

if %ERRORLEVEL% equ 0 (
    echo.
    echo ✅ Database connection successful!
    echo Your QuantityMeasurementApp can connect to Supabase.
) else (
    echo.
    echo ❌ Database connection failed!
    echo Please check:
    echo 1. Your password in .env file
    echo 2. Network connectivity
    echo 3. Supabase database status
)

echo.
pause