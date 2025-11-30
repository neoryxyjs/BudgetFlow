# Script para probar endpoints del backend Spring Boot
# Uso: .\probar_backend.ps1

Write-Host "🚀 Probando Backend Spring Boot en http://localhost:8080" -ForegroundColor Green
Write-Host ""

# Función para probar un endpoint
function Test-Endpoint {
    param(
        [string]$Url,
        [string]$Method = "GET",
        [string]$Body = $null
    )
    
    Write-Host "📡 $Method $Url" -ForegroundColor Cyan
    
    try {
        if ($Method -eq "GET") {
            $response = Invoke-WebRequest -Uri $Url -Method $Method -UseBasicParsing
        } else {
            $response = Invoke-WebRequest -Uri $Url -Method $Method -Body $Body -ContentType "application/json" -UseBasicParsing
        }
        
        Write-Host "✅ Status: $($response.StatusCode)" -ForegroundColor Green
        Write-Host "📄 Response:" -ForegroundColor Yellow
        Write-Host $response.Content
        Write-Host ""
        return $true
    } catch {
        Write-Host "❌ Error: $($_.Exception.Message)" -ForegroundColor Red
        Write-Host ""
        return $false
    }
}

# Verificar que el backend esté corriendo
Write-Host "🔍 Verificando que el backend esté corriendo..." -ForegroundColor Yellow
$isRunning = Test-Endpoint -Url "http://localhost:8080/api/users"

if (-not $isRunning) {
    Write-Host "❌ El backend no está corriendo o no responde." -ForegroundColor Red
    Write-Host "💡 Asegúrate de ejecutar: mvn spring-boot:run" -ForegroundColor Yellow
    exit 1
}

Write-Host "✅ Backend está funcionando!" -ForegroundColor Green
Write-Host ""
Write-Host "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" -ForegroundColor Gray
Write-Host ""

# Probar endpoints
Write-Host "📋 Probando Endpoints:" -ForegroundColor Magenta
Write-Host ""

# 1. Ver todos los usuarios
Test-Endpoint -Url "http://localhost:8080/api/users"

# 2. Ver todos los gastos
Test-Endpoint -Url "http://localhost:8080/api/expenses"

# 3. Crear un usuario de prueba
Write-Host "📝 Creando usuario de prueba..." -ForegroundColor Cyan
$userBody = @{
    id = "test-user-$(Get-Date -Format 'yyyyMMddHHmmss')"
    email = "test@example.com"
    name = "Test User"
} | ConvertTo-Json

Test-Endpoint -Url "http://localhost:8080/api/users" -Method "POST" -Body $userBody

# 4. Ver usuarios nuevamente (para ver el nuevo)
Test-Endpoint -Url "http://localhost:8080/api/users"

Write-Host "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" -ForegroundColor Gray
Write-Host ""
Write-Host "✅ Pruebas completadas!" -ForegroundColor Green
Write-Host ""
Write-Host "💡 Otros endpoints disponibles:" -ForegroundColor Yellow
Write-Host "   - http://localhost:8080/api/users/{id}" -ForegroundColor White
Write-Host "   - http://localhost:8080/api/expenses/user/{userId}" -ForegroundColor White
Write-Host "   - http://localhost:8080/h2-console (Base de datos)" -ForegroundColor White
Write-Host ""

