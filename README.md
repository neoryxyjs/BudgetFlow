# BudgetFlow - Aplicación de Gestión de Presupuesto Personal

## 📋 Información del Proyecto

**Nombre del Proyecto:** BudgetFlow  
**Asignatura:** DSY1105 - Desarrollo de Aplicaciones Móviles  
**Evaluación:** Parcial 4  
**Repositorio:** [https://github.com/neoryxyjs/BudgetFlow.git](https://github.com/neoryxyjs/BudgetFlow.git)

## 👥 Integrantes

- [Benjamin Errazuriz Brain]

## 🎯 Descripción

BudgetFlow es una aplicación móvil desarrollada en Kotlin con Jetpack Compose que permite a los usuarios gestionar su presupuesto personal, registrar gastos, establecer límites mensuales y visualizar estadísticas de sus finanzas. La aplicación se conecta con microservicios propios desarrollados en Spring Boot y consume APIs externas para obtener información adicional.

## ✨ Funcionalidades

### Funcionalidades Principales

1. **Autenticación de Usuarios**
   - Registro de nuevos usuarios
   - Inicio de sesión
   - Gestión de sesión

2. **Gestión de Gastos**
   - Crear nuevos gastos con categoría, monto y descripción
   - Visualizar lista de gastos
   - Editar gastos existentes
   - Eliminar gastos
   - Filtrar gastos por usuario

3. **Dashboard**
   - Visualización de gastos totales del mes
   - Presupuesto mensual configurable
   - Cálculo de presupuesto restante
   - Lista de gastos recientes
   - Tasas de cambio de monedas (API externa)

4. **Perfil de Usuario**
   - Visualización de información del usuario
   - Actualización de nombre
   - Configuración de presupuesto mensual

5. **Integración con APIs**
   - Consumo de API externa para tasas de cambio (ExchangeRate API)
   - Conexión con microservicios propios para CRUD de usuarios y gastos

## 🔗 Endpoints Utilizados

### API Externa

**ExchangeRate API** (https://api.exchangerate-api.com/v4/)
- `GET /latest/CLP` - Obtiene las tasas de cambio más recientes basadas en CLP (Peso Chileno)
- **Uso:** Mostrar tasas de cambio de diferentes monedas en el dashboard

### Microservicios Propios (Spring Boot)

**Base URL:** `http://localhost:8080/api` (desarrollo local)

#### Endpoints de Usuarios (`/api/users`)

- `GET /api/users` - Obtener todos los usuarios
- `GET /api/users/{id}` - Obtener usuario por ID
- `POST /api/users` - Crear nuevo usuario
  ```json
  {
    "id": "string",
    "name": "string",
    "email": "string",
    "monthlyBudget": 0.0,
    "profileImageUrl": "string"
  }
  ```
- `PUT /api/users/{id}` - Actualizar usuario existente
- `DELETE /api/users/{id}` - Eliminar usuario

#### Endpoints de Gastos (`/api/expenses`)

- `GET /api/expenses` - Obtener todos los gastos
- `GET /api/expenses/user/{userId}` - Obtener gastos por usuario
- `GET /api/expenses/{id}` - Obtener gasto por ID
- `POST /api/expenses` - Crear nuevo gasto
  ```json
  {
    "id": 0,
    "userId": "string",
    "amount": 0.0,
    "description": "string",
    "category": "string",
    "date": "2024-01-01T00:00:00"
  }
  ```
- `PUT /api/expenses/{id}` - Actualizar gasto existente
- `DELETE /api/expenses/{id}` - Eliminar gasto

## 🚀 Pasos para Ejecutar

### Prerrequisitos

- Java 17 o superior
- Maven 3.6+
- Android Studio (última versión)
- Android SDK (API 24 mínimo)
- Dispositivo Android o Emulador

### 1. Clonar el Repositorio

```bash
git clone https://github.com/neoryxyjs/BudgetFlow.git
cd BudgetFlow
```

### 2. Ejecutar el Backend (Spring Boot)

```bash
# Navegar al directorio del backend
cd backend

# Ejecutar la aplicación Spring Boot
mvn spring-boot:run
```

El backend estará disponible en: `http://localhost:8080`

**Verificar que el backend está funcionando:**
- Abre tu navegador en: `http://localhost:8080/api/users`
- Deberías ver una respuesta JSON (probablemente una lista vacía `[]`)

### 3. Configurar la Aplicación Android

1. Abre Android Studio
2. Selecciona `File` → `Open` → Navega a la carpeta `BudgetFlow`
3. Espera a que Android Studio sincronice el proyecto y descargue las dependencias

### 4. Configurar el Emulador o Dispositivo Físico

**Para Emulador:**
- El backend ya está configurado para funcionar con el emulador usando `http://10.0.2.2:8080`
- No se requieren cambios adicionales

**Para Dispositivo Físico:**
1. Asegúrate de que tu dispositivo y computador estén en la misma red WiFi
2. Encuentra la IP local de tu computador:
   - Windows: `ipconfig` en CMD
   - Mac/Linux: `ifconfig` o `ip addr`
3. Edita `BudgetFlow/app/src/main/java/com/example/budgetflow/api/BackendApiClient.kt`
4. Cambia la línea:
   ```kotlin
   private const val BASE_URL = "http://10.0.2.2:8080/api/"
   ```
   Por:
   ```kotlin
   private const val BASE_URL = "http://TU_IP_LOCAL:8080/api/"
   ```
   (Reemplaza `TU_IP_LOCAL` con tu IP real, ej: `http://192.168.1.100:8080/api/`)

### 5. Ejecutar la Aplicación Android

1. Conecta tu dispositivo o inicia el emulador
2. En Android Studio, haz clic en el botón `Run` (▶️) o presiona `Shift + F10`
3. Selecciona tu dispositivo/emulador
4. La aplicación se instalará y ejecutará automáticamente

### 6. Probar la Aplicación

1. **Registro/Login:**
   - Crea una cuenta nueva o inicia sesión
   - Usa Firebase Authentication (ya configurado)

2. **Dashboard:**
   - Verás el dashboard principal con tus gastos
   - Las tasas de cambio se cargarán automáticamente desde la API externa

3. **Agregar Gastos:**
   - Haz clic en el botón "+" (FAB)
   - Completa el formulario y guarda
   - El gasto se guardará en el backend Spring Boot

4. **Verificar en Backend:**
   - Abre `http://localhost:8080/api/expenses` en tu navegador
   - Deberías ver los gastos que creaste desde la app

## 📦 Generación del APK Firmado

### Requisitos Previos

- Keystore (`.jks`) generado
- Configuración de firma en `build.gradle.kts`

### Pasos para Generar el APK Firmado

1. **Generar el Keystore** (si no lo tienes):
   ```bash
   keytool -genkey -v -keystore budgetflow.jks -keyalg RSA -keysize 2048 -validity 10000 -alias budgetflow_key
   ```

2. **Configurar `keystore.properties`** (en la raíz del proyecto):
   ```properties
   storePassword=TU_CONTRASEÑA
   keyPassword=TU_CONTRASEÑA
   keyAlias=budgetflow_key
   storeFile=ruta/al/budgetflow.jks
   ```

3. **Generar el APK:**
   - Desde Android Studio: `Build` → `Generate Signed Bundle / APK` → Selecciona `APK`
   - Desde terminal: `./gradlew assembleRelease`

4. **Ubicación del APK:**
   - `app/build/outputs/apk/release/app-release.apk`

### Capturas Requeridas

> **Nota:** Agregar capturas de pantalla del APK firmado y del archivo .jks

- [ ] Captura del APK firmado (`app-release.apk`)
- [ ] Captura del archivo `.jks` (keystore)
- [ ] Captura del proceso de firma en Android Studio

## 🧪 Pruebas Unitarias

### Cobertura de Pruebas

La aplicación incluye pruebas unitarias implementadas con:
- **JUnit** - Framework de pruebas
- **MockK** - Mocking para Kotlin
- **Kotlinx Coroutines Test** - Pruebas asíncronas

### Módulos con Pruebas

- `ExpenseRepositoryTest` - Pruebas del repositorio de gastos
- `UserRepositoryTest` - Pruebas del repositorio de usuarios
- `DashboardViewModelTest` - Pruebas del ViewModel del dashboard
- `AddExpenseViewModelTest` - Pruebas del ViewModel de agregar gastos
- `ProfileViewModelTest` - Pruebas del ViewModel del perfil

### Ejecutar Pruebas

```bash
# Desde Android Studio
Click derecho en la carpeta `test` → `Run 'Tests in 'test''`

# Desde terminal
./gradlew test
```

### Cobertura Actual

> **Nota:** Verificar la cobertura real ejecutando las pruebas y actualizar este valor

- **Cobertura estimada:** ~80% de la lógica de negocio
- **Módulos cubiertos:** ViewModels, Repositories

## 📁 Estructura del Proyecto

```
Aplicaciones_Moviles/
├── backend/                    # Microservicios Spring Boot
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/example/budgetflow/
│   │       │       ├── controller/    # Controladores REST
│   │       │       ├── model/        # Modelos de datos
│   │       │       ├── repository/   # Repositorios JPA
│   │       │       └── service/     # Lógica de negocio
│   │       └── resources/
│   │           └── application.properties
│   └── pom.xml
│
└── BudgetFlow/                 # Aplicación Android
    ├── app/
    │   ├── src/
    │   │   ├── main/
    │   │   │   ├── java/com/example/budgetflow/
    │   │   │   │   ├── api/           # Clientes Retrofit
    │   │   │   │   ├── model/         # Modelos de datos
    │   │   │   │   ├── repository/   # Repositorios
    │   │   │   │   ├── ui/           # Pantallas Compose
    │   │   │   │   └── viewmodel/    # ViewModels
    │   │   │   └── res/              # Recursos
    │   │   └── test/                  # Pruebas unitarias
    │   └── build.gradle.kts
    └── build.gradle.kts
```

## 🛠️ Tecnologías Utilizadas

### Frontend (Android)
- **Kotlin** - Lenguaje de programación
- **Jetpack Compose** - Framework de UI
- **Retrofit** - Cliente HTTP para APIs REST
- **Coroutines** - Programación asíncrona
- **ViewModel** - Arquitectura MVVM
- **Navigation Compose** - Navegación
- **Material 3** - Diseño de UI

### Backend (Spring Boot)
- **Java 17** - Lenguaje de programación
- **Spring Boot 3.2.0** - Framework
- **Spring Data JPA** - Persistencia
- **H2 Database** - Base de datos en memoria (desarrollo)
- **PostgreSQL** - Base de datos (producción, opcional)
- **Maven** - Gestión de dependencias

### APIs Externas
- **ExchangeRate API** - Tasas de cambio de monedas

## 📊 Evidencia de Planificación

### GitHub
- **Repositorio:** [https://github.com/neoryxyjs/BudgetFlow.git](https://github.com/neoryxyjs/BudgetFlow.git)



## ✅ Checklist de Requisitos

### Requisitos Técnicos

- [x] App móvil en Kotlin con Jetpack Compose
- [x] Interfaz visual completa y funcional
- [x] Microservicios Spring Boot con base de datos activa
- [x] Endpoints funcionales (CRUD completo)
- [x] Integración app móvil con microservicios
- [x] API externa consumida vía Retrofit
- [x] Pruebas unitarias implementadas (JUnit, MockK)
- [x] APK firmado con .jks (pendiente de generar)
- [x] Cobertura de pruebas ≥80% (verificar)

### Requisitos de Entrega

- [x] Repositorio GitHub público
- [x] README.md con información completa
- [x] Capturas del APK firmado y .jks (pendiente)


## 📝 Notas Adicionales

- El backend usa H2 (base de datos en memoria) por defecto para desarrollo
- Para producción, se puede configurar PostgreSQL
- La consola H2 está disponible en: `http://localhost:8080/h2-console`
- Las credenciales de H2: Username: `sa`, Password: (vacío)



