@echo off
echo ========================================
echo Maven Structure Verification Script
echo ========================================

echo.
echo 1. Checking Maven directory structure...
if exist "src\main\java" (
    echo ✓ src/main/java exists
) else (
    echo ✗ src/main/java missing
    exit /b 1
)

if exist "src\main\resources" (
    echo ✓ src/main/resources exists
) else (
    echo ✗ src/main/resources missing
    exit /b 1
)

if exist "src\test\java" (
    echo ✓ src/test/java exists
) else (
    echo ✗ src/test/java missing
    exit /b 1
)

if exist "src\test\resources" (
    echo ✓ src/test/resources exists
) else (
    echo ✗ src/test/resources missing
    exit /b 1
)

echo.
echo 2. Checking main application files...
if exist "src\main\java\com\apps\quantitymeasurement\QuantityMeasurementApplication.java" (
    echo ✓ Main application class exists
) else (
    echo ✗ Main application class missing
    exit /b 1
)

if exist "src\main\resources\application.properties" (
    echo ✓ Application properties exists
) else (
    echo ✗ Application properties missing
    exit /b 1
)

echo.
echo 3. Checking test files...
if exist "src\test\java\com\apps\quantitymeasurement\QuantityMeasurementApplicationTests.java" (
    echo ✓ Main test class exists
) else (
    echo ✗ Main test class missing
    exit /b 1
)

if exist "src\test\resources\application-test.properties" (
    echo ✓ Test properties exists
) else (
    echo ✗ Test properties missing
    exit /b 1
)

echo.
echo 4. Validating Maven build...
call mvn validate
if %ERRORLEVEL% neq 0 (
    echo ✗ Maven validation failed
    exit /b 1
) else (
    echo ✓ Maven validation successful
)

echo.
echo 5. Compiling sources...
call mvn compile
if %ERRORLEVEL% neq 0 (
    echo ✗ Maven compile failed
    exit /b 1
) else (
    echo ✓ Maven compile successful
)

echo.
echo 6. Compiling tests...
call mvn test-compile
if %ERRORLEVEL% neq 0 (
    echo ✗ Maven test-compile failed
    exit /b 1
) else (
    echo ✓ Maven test-compile successful
)

echo.
echo ========================================
echo ✓ All Maven structure checks passed!
echo ========================================
echo.
echo Your project structure is correct:
echo - src/main/java (source code)
echo - src/main/resources (application properties)
echo - src/test/java (test code)
echo - src/test/resources (test properties)
echo.
echo Ready for Docker build and deployment!