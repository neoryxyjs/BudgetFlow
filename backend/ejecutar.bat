@echo off
echo ========================================
echo   BudgetFlow Backend - Spring Boot
echo ========================================
echo.

REM Verificar si Maven está instalado
where mvn >nul 2>&1
if %ERRORLEVEL% EQU 0 (
    echo [OK] Maven encontrado
    echo.
    echo Ejecutando Spring Boot...
    mvn spring-boot:run
    goto :end
)

REM Si no está Maven, intentar con wrapper
if exist "mvnw.cmd" (
    echo [INFO] Maven no encontrado, usando Maven Wrapper...
    echo.
    echo Ejecutando Spring Boot con wrapper...
    call mvnw.cmd spring-boot:run
    goto :end
)

echo [ERROR] Maven no está instalado y no se encontró Maven Wrapper
echo.
echo Por favor:
echo 1. Instala Maven, O
echo 2. Abre este proyecto en Android Studio/IntelliJ IDEA
echo.
echo Ver INSTALAR_MAVEN.md para más información
pause

:end

